package beeriprint.newsletter.batch.mailer.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Hilel Y.
 */
public class Email {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String id;
    private Campaign campaign;
    private String recipientAddress;
    private String recipientName;
    private String recipientState = "N/A";
    private String actionCode;
    private String statusCode;
    private String diagnosticCode;
    private Date creationTime;
    private Date lastUpdate;

    public String getUserPart() {
        return getRecipientAddress().split("@")[0];
    }

    public String getDomainPart() {
        return getRecipientAddress().split("@")[1];
    }

    public Object[] valuesAsArray() {
        // id, campaign_id, recipient_name, user_part, domain_part, created
        Object[] array = new Object[6];
        array[0] = getId();
        array[1] = getCampaign().getId();
        array[2] = getRecipientName();
        array[3] = getUserPart();
        array[4] = getDomainPart();
        array[5] = getCreationTime();
        return array;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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

    /**
     * @return the creationTime
     */
    public Date getCreationTime() {
        return creationTime;
    }

    /**
     * @param creationTime the creationTime to set
     */
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return the lastUpdate
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the recipientName
     */
    public String getRecipientName() {
        return recipientName;
    }

    /**
     * @param recipientName the recipientName to set
     */
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    /**
     * @return the recipientAddress
     */
    public String getRecipientAddress() {
        return recipientAddress.toLowerCase().trim();
    }

    /**
     * @param recipientAddress the recipientAddress to set
     */
    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress.toLowerCase().trim();
    }

    /**
     * @return the campaign
     */
    public Campaign getCampaign() {
        return campaign;
    }

    /**
     * @param campaign the campaign to set
     */
    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    /**
     * @return the recipientState
     */
    public String getRecipientState() {
        return recipientState;
    }

    /**
     * @param recipientState the recipientState to set
     */
    public void setRecipientState(String recipientState) {
        this.recipientState = recipientState;
    }
}
