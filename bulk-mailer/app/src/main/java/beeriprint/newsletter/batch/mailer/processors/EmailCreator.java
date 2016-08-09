package beeriprint.newsletter.batch.mailer.processors;

import beeriprint.newsletter.batch.mailer.model.Email;
import com.sun.mail.smtp.SMTPMessage;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 *
 * @author hilel
 *
 * Transforms email-record to mime-message
 */
public class EmailCreator implements ItemProcessor<Email, MimeMessage> {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EmailCreator.class);

    private String appServer;
    private String mailHost;
    private String senderName;
    private String senderAddress;
    private String replyToAddress;
    private String subject;
    private Configuration configuration;

    @Override
    public MimeMessage process(Email item) throws Exception {
        return apacheHtmlEmail(item);
    }

    private MimeMessage apacheHtmlEmail(Email item) throws EmailException, IOException, TemplateException, AddressException, MessagingException {
        HtmlEmail email = new HtmlEmail();
        email.setCharset("UTF-8");
        email.setHostName(mailHost);
        email.setSubject(subject);
        email.setFrom(senderAddress, senderName);
        email.addTo(item.getRecipientAddress());
        // reply to
        List<InternetAddress> replyTo = new ArrayList<>();
        replyTo.add(new InternetAddress(replyToAddress));
        email.setReplyTo(replyTo);
        // custom headers
        Map<String, String> customHeaders = createCustomHeaders(item);
        email.setHeaders(customHeaders);
        // message body
        Properties properties = new Properties();
        properties.put("appServer", appServer);
        properties.put("item", item);
        String bodyFile = item.getCampaign().getTemplateName().toLowerCase() + ".html";
        String bodyData = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(bodyFile), properties);
        email.setHtmlMsg(bodyData);
        // build and return
        email.buildMimeMessage();
        SMTPMessage message = new SMTPMessage(email.getMimeMessage());
        message.setReturnOption(SMTPMessage.RETURN_HDRS);
        message.setNotifyOptions(SMTPMessage.NOTIFY_DELAY | SMTPMessage.NOTIFY_FAILURE | SMTPMessage.NOTIFY_SUCCESS);
        return message;
    }

    private Map createCustomHeaders(Email item) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-recordId", item.getId());
        headers.put("X-campaignDate", item.getCampaign().getCampaignDate().toString());
        headers.put("X-templateName", item.getCampaign().getTemplateName());
        headers.put("X-recipientName", item.getRecipientName());
        return headers;
    }

    /**
     * @param mailHost the mailHost to set
     */
    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
        LOGGER.info("mail host = " + mailHost);
    }

    /**
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @param senderAddress the senderAddress to set
     */
    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    /**
     * @param replyToAddress the replyToAddress to set
     */
    public void setReplyToAddress(String replyToAddress) {
        this.replyToAddress = replyToAddress;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * @param appServer the appServer to set
     */
    public void setAppServer(String appServer) {
        this.appServer = appServer;
        LOGGER.info("application server = " + appServer);
    }
}