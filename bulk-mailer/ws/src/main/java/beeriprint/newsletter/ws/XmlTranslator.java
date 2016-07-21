package beeriprint.newsletter.ws;

import beeriprint.newsletter.model.UserActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

/**
 *
 * @author hilel-deb
 */
public class XmlTranslator {

    DocumentBuilder documentBuilder;
    Transformer transformer;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public XmlTranslator() throws ParserConfigurationException, TransformerConfigurationException {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        transformer = TransformerFactory.newInstance().newTransformer();
    }

    public String createUserActivity(String messageCode, String activityType, int resourceId)
            throws JAXBException, IOException {

        UserActivity userActivity = new UserActivity();
        userActivity.setMessageCode(messageCode);
        userActivity.setActivityType(activityType);
        userActivity.setRequestTime(Calendar.getInstance());
        userActivity.setResourceId(resourceId);

        JAXBContext jaxbContext = JAXBContext.newInstance(UserActivity.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        try (OutputStream out = new ByteArrayOutputStream()) {
            jaxbMarshaller.marshal(userActivity, out);
            return out.toString();
        }
    }
}
