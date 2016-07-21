package beeriprint.newsletter.camel.beans;

import beeriprint.newsletter.model.DsnRecord;
import com.sun.mail.dsn.DeliveryStatus;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Header;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.mail.dsn.MultipartReport;

/**
 *
 * @author hilel-deb
 */
public class DsnAnalyzer {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final Pattern esmtpPattern = Pattern.compile("[45]\\.[0-9]{1,3}\\.[0-9]{1,3}");

    //IMAPInputStream iMAPInputStream;
    public DsnRecord processMultipartReport(MultipartReport multipartReport) throws Exception {
        //MultipartReport multipartReport = (MultipartReport) message.getContent();
        // initialize results 
        DsnRecord results = new DsnRecord();
        // Extract action, status diagnostic codes
        DeliveryStatus deliveryStatus = (DeliveryStatus) multipartReport.getReport();
        parseDeliveryStatus(deliveryStatus, results);
        // Extract original message id
        MimeMessage returnedMessage = multipartReport.getReturnedMessage();
        if (returnedMessage == null) {
            logger.debug("Returned message not found");
            return results;
        }
        String recordId = returnedMessage.getHeader("X-recordId", null);
        if (recordId == null) {
            logger.debug("Header not found: X-recordId");
            return results;
        }
        results.setMessageCode(recordId);
        return results;
    }

    private void parseDeliveryStatus(DeliveryStatus deliveryStatus, DsnRecord results) throws Exception {

        // get all headers
        Enumeration headers = deliveryStatus.getRecipientDSN(0).getAllHeaders();
        while (headers.hasMoreElements()) {
            Header h = (Header) headers.nextElement();
            // action code
            if (h.getName().equalsIgnoreCase("Action")) {
                if (h.getValue() != null) {
                    String actionCode = h.getValue().trim();
                    if (actionCode.length() > 20) {
                        actionCode = actionCode.substring(0, 20);
                        String[] words = actionCode.split(" ");
                        if (words.length > 1) {
                            actionCode = words[0];
                        }
                    }
                    results.setActionCode(actionCode);
                }
            }
            // status code
            if (h.getName().equalsIgnoreCase("Status")) {
                Matcher matcher = esmtpPattern.matcher(h.getValue());
                if (matcher.find()) {
                    results.setStatusCode(matcher.group());
                }
            }
            // diagnostic code
            if (h.getName().equalsIgnoreCase("Diagnostic-code")) {
                String diagnosticCode = h.getValue();
                diagnosticCode = diagnosticCode.replaceAll("\\n", "").replaceAll("\\r", "");
                diagnosticCode = diagnosticCode.trim();
                if (diagnosticCode.length() > 512) {
                    diagnosticCode = diagnosticCode.substring(0, 512);
                }
                results.setDiagnosticCode(diagnosticCode);
            }
        }
    }
}
