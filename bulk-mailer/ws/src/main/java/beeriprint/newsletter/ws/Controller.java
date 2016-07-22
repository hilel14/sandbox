package beeriprint.newsletter.ws;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

/**
 * Root resource (exposed at "controller" path)
 */
@Path("controller")
public class Controller {

    static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    BufferedImage image;
    Map<Integer, String> targets;
    XmlTranslator xmlTranslator;
    JmsClient jmsClient;

    public Controller() {
        try {
            setup();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void setup() throws IOException, NamingException, ParserConfigurationException, TransformerConfigurationException {
        String configFolder = "/opt/beeri/newsletter/config";
        java.nio.file.Path configPath = Paths.get(configFolder);
        // image
        image = ImageIO.read(configPath.resolve("16x16.png").toFile());
        // links
        targets = new HashMap<>();
        List<String> lines = Files.readAllLines(configPath.resolve("targets.csv"), Charset.forName("utf-8"));
        for (String line : lines) {
            String[] parts = line.split(",");
            targets.put(Integer.parseInt(parts[0]), parts[1]);
        }
        // jaxb
        xmlTranslator = new XmlTranslator();
        // jms
        Context envCtx = (Context) new InitialContext().lookup("java:comp/env");
        ConnectionFactory connectionFactory = (ConnectionFactory) envCtx.lookup("jms/newsletter");
        jmsClient = new JmsClient(connectionFactory);
    }

    @GET
    @Path("/images/{messageCode}")
    @Produces({"image/png", "image/jpg"})
    public Response getImage(@PathParam("messageCode") String messageCode)
            throws URISyntaxException, JMSException, JAXBException, IOException {
        String text = xmlTranslator.createUserActivity(messageCode, "open", 0);
        jmsClient.produce(text, "newsletter.user.activity");
        return Response.ok(image).build();
    }

    @GET
    @Path("/links/{resourceId}/{messageCode}")
    public Response getLink(
            @PathParam("resourceId") int resourceId,
            @PathParam("messageCode") String messageCode)
            throws URISyntaxException, JMSException, JAXBException, IOException {
        String text = xmlTranslator.createUserActivity(messageCode, "click", resourceId);
        jmsClient.produce(text, "newsletter.user.activity");
        String target = targets.get(resourceId);
        URI uri = URI.create(target);
        return Response.seeOther(uri).build();
    }

    @GET
    @Path("/unsubscribe/{messageCode}")
    public Response unSubscribe(
            @PathParam("messageCode") String messageCode)
            throws URISyntaxException, JMSException, JAXBException, IOException {
        String text = xmlTranslator.createUserActivity(messageCode, "unsubscribe", 1);
        jmsClient.produce(text, "newsletter.user.activity");
        String target = targets.get(1);
        URI uri = URI.create(target);
        return Response.seeOther(uri).build();
    }
}
