package beeriprint.newsletter.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hilel
 */
public class DsnRecord {

    private String messageCode;
    private String actionCode;
    private String statusCode;
    private String diagnosticCode;

    /**
     * @return the messageCode
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * @param messageCode the messageCode to set
     */
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    /**
     * @return the actionCode
     */
    public String getActionCode() {
        return actionCode;
    }

    /**
     * @param actionCode the actionCode to set
     */
    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the diagnosticCode
     */
    public String getDiagnosticCode() {
        return diagnosticCode;
    }

    /**
     * @param diagnosticCode the diagnosticCode to set
     */
    public void setDiagnosticCode(String diagnosticCode) {
        this.diagnosticCode = diagnosticCode;
    }

    public Map asMap() {
        Map results = new HashMap();
        results.put("messageCode", messageCode);
        results.put("actionCode", actionCode);
        results.put("statusCode", statusCode);
        results.put("diagnosticCode", diagnosticCode);
        return results;
    }
}
