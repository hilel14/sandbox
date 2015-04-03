/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.mail.verifier.server;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.LoggerFactory;

/**
 * Stores all the relevant information about an Email message, before and after
 * analysis.
 *
 * @author hilel
 */
public class AnalysisResult {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AnalysisResult.class);

    private final MimeMessage message;
    private final String from;
    //---
    private Set<String> allRecipients;
    //---
    private Set<String> whiteListDomains;
    private Set<CustomerDomain> customerDomains;
    private Set<String> otherDomains;
    //---
    private Set<String> customeCodes;
    private Set<String> attachments;
    // ---
    private StatusCodes statusCode = StatusCodes.OK;
    private String statusDescription;

    /**
     * All possible results of analyzing a message. Used by RuleEngine and
     * MessageRouter. Each error code also corresponds to a friendly message in
     * reject-codes.properties.
     */
    public enum StatusCodes {

        OK, customersMix, othersMix, attachmentsMismatch;
    }

    public AnalysisResult(MimeMessage message) throws MessagingException {
        this.message = message;
        // extract sender
        Address sender = message.getFrom()[0];
        this.from = new InternetAddress(sender.toString().toLowerCase()).getAddress();

    }

    private void extractRecipients() throws MessagingException {
        allRecipients = new HashSet<>();
        // extract to recipients
        InternetAddress[] to = InternetAddress.parseHeader(message.getHeader("To", ","), false);
        for (InternetAddress address : to) {
            String recipient = normalizeRecipient(address.getAddress());
            allRecipients.add(recipient);
        }
        // extract cc recipients
        String h = message.getHeader("Cc", ",");
        if (h != null) {
            InternetAddress[] cc = InternetAddress.parseHeader(message.getHeader("Cc", ","), false);
            for (InternetAddress address : to) {
                String recipient = normalizeRecipient(address.getAddress());
                allRecipients.add(recipient);
            }
        }
    }

    private String normalizeRecipient(String recipient) {
        String normal = recipient.toLowerCase();
        if (normal.contains("'")) {
            normal = normal.replace("'", "");
        }
        return normal;
    }

    public void sortRecipients(RuleBase ruleBase) throws MessagingException {
        // setup
        extractRecipients();
        whiteListDomains = new HashSet<>();
        customerDomains = new HashSet<>();
        otherDomains = new HashSet<>();
        // sort
        for (String recipient : allRecipients) {
            // extract domain part from recipient address
            String[] parts = recipient.split("@");
            String domain = parts[1];
            // sort recipients by domain type
            if (ruleBase.getDomainWhiteList().contains(domain)) {
                whiteListDomains.add(domain);
            } else if (ruleBase.getDomainToCustomer().containsKey(domain)) {
                customerDomains.add(new CustomerDomain(domain, ruleBase.getDomainToCustomer().get(domain)));
            } else {
                otherDomains.add(domain);
            }
        }
    }

    /**
     * used by the rule-engine before processing attachments names.
     */
    public void extractCustomerCodes() {
        customeCodes = new HashSet<>();
        for (CustomerDomain domain : customerDomains) {
            for (String code : domain.getCodes()) {
                customeCodes.add(code);
            }
        }
    }

    public String getSummary() throws MessagingException {
        StringBuilder b = new StringBuilder();
        b.append("Result: ").append(statusCode).append(", ");
        //b.append("sent: ").append(message.getSentDate()).append(", ");
        b.append("From: ").append(from).append(", ");
        b.append("Recipients: ").append(allRecipients).append(", ");
        //b.append("subject: ").append(message.getSubject()).append(", ");
        b.append("Attachments: ").append(attachments);
        return b.toString();
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @return the allRecipients
     */
    public Set<String> getAllRecipients() {
        return allRecipients;
    }

    /**
     * @param description the status description to set
     */
    public void setStatusDescription(String description) {
        this.statusDescription = description;
    }

    /**
     * @return the message
     */
    public MimeMessage getMessage() {
        return message;
    }

    public Set<String> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments the attachments to set. Used by the rule-engine before
     * processing attachments names.
     */
    public void setAttachments(Set<String> attachments) {
        this.attachments = attachments;
    }

    /**
     * @return the date the original message was sent
     */
    public String getDate() {
        String date = null;
        try {
            date = new SimpleDateFormat("dd בMMMMM yyyy", Locale.forLanguageTag("he")).format(message.getSentDate());
        } catch (MessagingException ex) {
            logger.error("error while parsing message sent date", ex);
        }
        return date;
    }

    /**
     * @return the time the original message was sent
     */
    public String getTime() {
        String time = null;
        try {
            time = new SimpleDateFormat("HH:mm:ss").format(message.getSentDate());
        } catch (MessagingException ex) {
            logger.error("error while parsing message sent time", ex);
        }
        return time;
    }

    public void setStatusCode(StatusCodes statusCode) {
        this.statusCode = statusCode;
    }

    public StatusCodes getStatusCode() {
        return statusCode;
    }

    /**
     * @return a short description of the reason the message was rejected. used
     * as the subject of the bounce message, and also in its body.
     */
    public String getStatusDescription() {
        return statusDescription;
    }

    /**
     * @return a collection of CustomerDomain objects, associated with the
     * message analyzed.
     */
    public Set<CustomerDomain> getCustomerDomains() {
        return customerDomains;
    }

    /**
     * @return a collection of customer codes, collected from all customer
     * recipients.
     */
    public Set<String> getCustomeCodes() {
        return customeCodes;
    }

    /**
     * @return a collection of domain names, extracted from all recipients of
     * this email, that were found in the white list.
     */
    public Set<String> getWhiteListDomains() {
        return whiteListDomains;
    }

    /**
     * @return a collection of domain names, extracted from all recipients of
     * this email, that does no belong to a customer and also not found in the
     * white list.
     */
    public Set<String> getOtherDomains() {
        return otherDomains;
    }
}
