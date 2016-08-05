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
import org.ganshaqed.sql2mail.model.Job;

/**
 *
 * @author hilel
 */
public class EmailSender {

    static final Logger LOGGER = Logger.getLogger(EmailSender.class.getName());
    private final String host;
    private final String user;
    private final String password;
    private final String sender;
    private final boolean debug;
    private final boolean sslOnConnect;
    private final boolean startTLSEnabled;

    public EmailSender() throws IOException {
        Properties p = new Properties();
        p.load(EmailSender.class.getClassLoader().getResourceAsStream("smtp.properties"));
        host = p.getProperty("host");
        user = p.getProperty("user");
        password = p.getProperty("password");
        sender = p.getProperty("sender");
        debug = Boolean.parseBoolean(p.getProperty("debug"));
        sslOnConnect = Boolean.parseBoolean(p.getProperty("sslOnConnect"));
        startTLSEnabled = Boolean.parseBoolean(p.getProperty("startTLSEnabled"));
        LOGGER.log(Level.INFO, "SMTP host {0}", host);
    }

    //public void sendEmail(Path reportFile, String[] recipients, String subject, String body)
    public void sendEmail(Job job)
            throws EmailException, IOException, MessagingException {
        // Create the email message
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(host);
        //email.setSmtpPort(25);
        email.setAuthenticator(new DefaultAuthenticator(user, password));
        email.setSSLOnConnect(sslOnConnect);
        email.setStartTLSEnabled(startTLSEnabled);
        email.setFrom(sender);
        for (String recipient : job.getRecipients()) {
            LOGGER.log(Level.INFO, "Adding recipient {0}", recipient);
            email.addTo(recipient);
        }
        email.setSubject(job.getSubject());
        email.setMsg(job.getBody());

        // add the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(job.getReportFile().toString());
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("sql2mail data");
        attachment.setName(job.getReportFile().getFileName().toString());
        email.attach(attachment);

        // send the email
        if (debug) {
            email.buildMimeMessage();
            Path out = job.getReportFile().getParent().resolve(job.getReportFile().getFileName().toString().concat(".eml"));
            email.getMimeMessage().writeTo(new FileOutputStream(out.toFile()));
            LOGGER.log(Level.INFO, "Email saved in file {0}", out.toString());
        } else {
            email.send();
            LOGGER.log(Level.INFO, "Email sent to {0} recipients", job.getRecipients().length);
        }
    }
}
