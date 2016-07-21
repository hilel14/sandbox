package beeriprint.newsletter.model;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;

/**
 *
 * @author hilel
 */
@XmlRootElement(name = "activity")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserActivity {

    @XmlAttribute(name = "message")
    private String messageCode;

    @XmlAttribute(name = "time")
    @XmlSchemaType(name = "date")
    private Calendar requestTime;

    @XmlAttribute(name = "resource")
    private int resourceId;

    @XmlAttribute(name = "type")
    private String activityType;

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
     * @return the requestTime
     */
    public Calendar getRequestTime() {
        return requestTime;
    }

    /**
     * @param requestTime the requestTime to set
     */
    public void setRequestTime(Calendar requestTime) {
        this.requestTime = requestTime;
    }

    /**
     * @return the resourceId
     */
    public int getResourceId() {
        return resourceId;
    }

    /**
     * @param resourceId the resourceId to set
     */
    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * @return the activityType
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * @param activityType the activityType to set
     */
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
