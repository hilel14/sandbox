package org.ganshaqed.sql2mail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import static org.ganshaqed.sql2mail.JdbcController.LOGGER;

/**
 *
 * @author hilel
 */
public class EmailSender {

    static final Logger LOGGER = Logger.getLogger(EmailSender.class.getName());
    String host;
    String user;
    String password;
    String sender;
    boolean debug;

    public EmailSender() throws IOException {
        loadSmtpProperties();
    }

    private void loadSmtpProperties() throws IOException {
        Properties p = new Properties();
        p.load(EmailSender.class.getClassLoader().getResourceAsStream("smtp.properties"));
        host = p.getProperty("host");
        user = p.getProperty("user");
        password = p.getProperty("password");
        sender = p.getProperty("sender");
        debug = Boolean.parseBoolean(p.getProperty("debug"));
        LOGGER.log(Level.INFO, "SMTP host {0}", host);
    }

    public void sendEmail(Path reportFile, String[] recipients, String subject, String body)
            throws EmailException, IOException, MessagingException {
        // Create the email message
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(host);
        //email.setSmtpPort(25);
        email.setAuthenticator(new DefaultAuthenticator(user, password));
        //email.setSSLOnConnect(true);
        for (String recipient : recipients) {
            LOGGER.log(Level.INFO, "Adding recipient {0}", recipient);
            email.addTo(recipient);
        }
        email.setFrom(sender);
        email.setSubject(subject);
        email.setMsg(body);

        // add the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(reportFile.toString());
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("letter-by-click report");
        attachment.setName(reportFile.getFileName().toString());
        email.attach(attachment);

        // send the email
        email.buildMimeMessage();
        if (debug) {
            Path out = reportFile.getParent().resolve(reportFile.getFileName().toString().concat(".eml"));
            email.getMimeMessage().writeTo(new FileOutputStream(out.toFile()));
            LOGGER.log(Level.INFO, "Email saved in file {0}", out.toString());
        } else {
            email.send();
            LOGGER.log(Level.INFO, "Email sent to {0} recipients", recipients.length);
        }
    }
}
