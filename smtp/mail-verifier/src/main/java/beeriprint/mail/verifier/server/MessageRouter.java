package beeriprint.mail.verifier.server;

import beeriprint.mail.verifier.server.util.BounceComposer;
import beeriprint.mail.verifier.server.AnalysisResult.StatusCodes;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;

/**
 * Send each message to the RuleEngine for analysis, then forward or bounce it,
 * based on the result.
 *
 * @author hilel
 */
public class MessageRouter implements MessageHandlerFactory {

    private static final Logger logger = LoggerFactory.getLogger(MessageRouter.class);
    private final String sourceServer;
    private final Properties targetServer = System.getProperties();
    private final RuleEngine engine;
    private final BounceComposer composer;

    public MessageRouter(String source, String target, RuleBase dataSource)
            throws IOException, ClassNotFoundException, SQLException {
        sourceServer = source;
        targetServer.put("mail.smtp.host", target);
        engine = new RuleEngine(dataSource);
        composer = new BounceComposer();
    }

    @Override
    public MessageHandler create(MessageContext ctx) {
        return new Handler(ctx);
    }

    private void forward(MimeMessage message) throws MessagingException {
        Transport.send(message);
    }

    private void bounce(AnalysisResult result)
            throws EmailException, MessagingException, TemplateException, IOException {
        Email email = composer.composeMessage(result);
        email.setHostName(sourceServer);
        email.setFrom("system@mail.filter", "Mail Filter System");
        email.addTo(result.getFrom());
        email.send();
    }

    class Handler implements MessageHandler {

        MessageContext ctx;
        String from;
        List<String> recipients = new ArrayList<>();

        public Handler(MessageContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void from(String from) throws RejectException {
            this.from = from;
        }

        @Override
        public void recipient(String recipient) throws RejectException {
            this.recipients.add(recipient);
        }

        @Override
        public void data(InputStream data) throws IOException {
            try {
                MimeMessage message = new MimeMessage(Session.getInstance(targetServer), data);
                if (message.getSubject().equals("mail verifier command")) {
                    engine.execAdminCommand(message.getContent().toString().trim());
                    return;
                }
                AnalysisResult result = engine.analyzeMessage(message);
                logger.debug(result.getSummary());
                if (result.getStatusCode().equals(StatusCodes.OK)) {
                    forward(message);
                } else {
                    bounce(result);
                }
            } catch (MessagingException | IOException | ClassNotFoundException | SQLException | EmailException | TemplateException ex) {
                logger.warn(" Error during SMTP transaction. From: " + from + " to: " + recipients.toString());
                logger.error(ex.toString(), ex);
                //throw new IOException(ex);
            }
        }

        @Override
        public void done() {
            logger.trace("done");
        }
    }
}
