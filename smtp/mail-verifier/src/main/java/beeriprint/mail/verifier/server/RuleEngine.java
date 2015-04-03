/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.mail.verifier.server;

import beeriprint.mail.verifier.server.util.JdbcRuleBase;
import beeriprint.mail.verifier.server.util.AttachmentParser;
import beeriprint.mail.verifier.server.AnalysisResult.StatusCodes;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import javax.sql.DataSource;

/**
 * Implements core application logic.
 *
 * @author hilel
 */
public class RuleEngine {

    private static final Logger logger = LoggerFactory.getLogger(RuleEngine.class);
    RuleBase ruleBase;
    AttachmentParser parser;

    /**
     * Initialize the rule engine and creates a new instance of JdbcController
     *
     * @param ruleBase
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public RuleEngine(RuleBase ruleBase) throws IOException, ClassNotFoundException, SQLException {
        this.ruleBase = ruleBase;
        parser = new AttachmentParser();
    }

    /**
     * Handles spacial messages sent by some administration tool
     *
     * @param command currently only one command is supported: reload data (used
     * changes to any database table)
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public void execAdminCommand(String command) throws IOException, ClassNotFoundException, SQLException {
        logger.info("Executing special mail verifier command: " + command);
        switch (command) {
            case "reload data":
                ruleBase = new JdbcRuleBase();
                break;
            default:
                throw new IOException("No such command: " + command);
        }
    }

    /**
     * Analyze each message and return accept or reject codes
     *
     * @param message
     * @return OK, if the message passed all the rules. Otherwise, one of:
     * customersMix, nonCustomers, attachmentMismatch
     * @throws javax.mail.MessagingException
     * @throws java.io.IOException
     */
    public AnalysisResult analyzeMessage(MimeMessage message)
            throws MessagingException, IOException {

        AnalysisResult result = new AnalysisResult(message);

        // sender in white list
        if (ruleBase.getSenderWhiteList().contains(result.getFrom())) {
            return result;
        }

        result.sortRecipients(ruleBase);

        // non of the recipients is a customer
        if (result.getCustomerDomains().isEmpty()) {
            return result;
        }

        if (mixOfRecipients(result)) {
            return result;
        }

        result.extractCustomerCodes();

        if (wrongAttachmentFound(result)) {
            return result;
        }

        return result;
    }

    private boolean mixOfRecipients(AnalysisResult result) {
        // mix of customers and non-customers recipients
        if (!result.getCustomerDomains().isEmpty() && !result.getOtherDomains().isEmpty()) {
            result.setStatusCode(StatusCodes.othersMix);
            return true;
        }
        // mix of customers
        CustomerDomain first = result.getCustomerDomains().iterator().next();
        for (CustomerDomain domain : result.getCustomerDomains()) {
            if (!domain.equals(first)) {
                result.setStatusCode(StatusCodes.customersMix);
                return true;
            }
        }
        return false;
    }

    private boolean wrongAttachmentFound(AnalysisResult result) throws IOException, MessagingException {
        Set<String> attachments = parser.parseMessage(result.getMessage());
        if (!attachments.isEmpty()) {
            result.setAttachments(attachments);
            for (String attachment : attachments) {
                if (mismatchFound(result.getCustomeCodes(), attachment)) {
                    result.setStatusCode(StatusCodes.attachmentsMismatch);
                    return true;
                }
            }
            result.setStatusCode(StatusCodes.OK);
            return false;
        }
        return false;
    }

    private boolean mismatchFound(Set<String> customerCodes, String attachmentName) {
        String prefix = attachmentName.substring(0, 3);
        // attachment belongs to customer
        if (customerCodes.contains(prefix)) {
            return false;
        }
        // special attachment name
        for (Pattern pattern : ruleBase.getSpecialAttachmentNames()) {
            if (pattern.matcher(attachmentName).matches()) {
                return false;
            }
        }
        // no match
        return true;
    }

}
