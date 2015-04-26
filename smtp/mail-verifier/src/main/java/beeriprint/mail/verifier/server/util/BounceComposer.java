/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.mail.verifier.server.util;

import beeriprint.mail.verifier.server.AnalysisResult;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;
import javax.mail.MessagingException;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Composes an HTML bounce message with useful information about the reason the
 * message was rejected by the rule engine.
 *
 * @author hilel
 */
public class BounceComposer {

    private static final Logger logger = LoggerFactory.getLogger(BounceComposer.class);
    Properties subjects = new Properties();
    Template template;

    /**
     * Initialize the FreeMarker template and loads some properties.
     *
     * @throws IOException
     */
    public BounceComposer() throws IOException {
        subjects.load(BounceComposer.class.getResourceAsStream("/reject-codes.properties"));
        // init free-marker template
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        configuration.setClassForTemplateLoading(BounceComposer.class, "/");
        template = configuration.getTemplate("bounce.html", "utf-8");
        logger.info("freemarker template {} created", template.getName());
    }

    /**
     * Compose the bound message, based on the result obtained by the rule
     * engine.
     *
     * @param result - used as the data model for the template.
     * @return
     * @throws EmailException
     * @throws MessagingException
     * @throws TemplateException
     * @throws IOException
     */
    public Email composeMessage(AnalysisResult result)
            throws EmailException, MessagingException, TemplateException, IOException {
        HtmlEmail email = new HtmlEmail();
        email.setCharset("UTF-8");
        email.setSubject(subjects.getProperty(result.getStatusCode().toString()));
        result.setStatusDescription(subjects.getProperty(result.getStatusCode().toString()));
        Writer out = new StringWriter();
        template.process(result, out);
        email.setHtmlMsg(out.toString());
        return email;
    }
}
