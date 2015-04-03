package beeriprint.mail.verifier;

import beeriprint.mail.verifier.util.ClasspathDataSource;
import beeriprint.mail.verifier.util.EmbeddedRuleBase;
import beeriprint.mail.verifier.server.AnalysisResult;
import beeriprint.mail.verifier.server.AnalysisResult.StatusCodes;
import beeriprint.mail.verifier.server.util.BounceComposer;
import beeriprint.mail.verifier.server.RuleEngine;
import beeriprint.mail.verifier.server.RuleBase;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit test for the rule engine.
 */
public class RuleEngineTest extends TestCase {

    private static final Logger logger = LoggerFactory.getLogger(RuleEngineTest.class);
    RuleEngine engine;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RuleEngineTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(RuleEngineTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        RuleBase dataSource = new EmbeddedRuleBase();
        engine = new RuleEngine(dataSource);
    }

    public void testFreeMarkerTemplate() throws Exception {
        logger.info("testing freemarker template");
        String sender = "hilel@beeriprint.co.il";
        Set<String> recipients = new HashSet<>();
        recipients.add("user@ebill.co.il");
        recipients.add("user@printernet.co.il");
        MimeMessage message = createMessage(sender, recipients, "freemarker template test");
        // analyse result
        AnalysisResult result = engine.analyzeMessage(message);
        // check that freemarker generated html
        Email bounceMessage = new BounceComposer().composeMessage(result);
        assertNotNull(bounceMessage);
    }

    public void testInvalidRecipients() throws Exception {
        logger.info("enforces fewer rfc822 syntax rules");
        MimeMessage m = new MimeMessage(Session.getDefaultInstance(System.getProperties()));
        m.setSubject("test rfc822 syntax");
        m.setFrom(new InternetAddress("sender@test.com", "The Sender"));
        m.setHeader("To", "User One <user1@test.com>,User Two <'user2@test.com'>");
        // analyse result
        AnalysisResult result = engine.analyzeMessage(m);
        for (String recipient : result.getAllRecipients()) {
            InternetAddress address = new InternetAddress(recipient, true);
            assertNotNull(address);
        }
    }

    public void test1() throws Exception {
        logger.info("testing user in white list");
        String sender = "user1@beeriprint.co.il";
        Set<String> recipients = new HashSet<>();
        recipients.add("user1@domain");
        recipients.add("user2@domain");
        // compose test message
        MimeMessage message = createMessage(sender, recipients, "user in white list");
        // analyse result
        AnalysisResult result = engine.analyzeMessage(message);
        assertTrue(result.getStatusCode().equals(StatusCodes.OK));
    }

    public void test2() throws Exception {
        logger.info("testing customers mix");
        String sender = "hilel@beeriprint.co.il";
        Set<String> recipients = new HashSet<>();
        recipients.add("user1@domain");
        recipients.add("user2@domain");
        // compose test message
        MimeMessage message = createMessage(sender, recipients, "customers mix");
        // analyse result
        AnalysisResult result = engine.analyzeMessage(message);
        assertTrue(result.getStatusCode().equals(StatusCodes.customersMix));
    }

    public void test3() throws Exception {
        logger.info("testing 2 domains of the same customer");
        String sender = "hilel@beeriprint.co.il";
        Set<String> recipients = new HashSet<>();
        recipients.add("user1@domain");
        recipients.add("user2@domain");
        // compose test message
        MimeMessage message = createMessage(sender, recipients, "2 domains of the same customer");
        // analyse result
        AnalysisResult result = engine.analyzeMessage(message);
        assertTrue(result.getStatusCode().equals(StatusCodes.customersMix));
    }

    public void test4() throws Exception {
        logger.info("testing customers and others mix");
        String sender = "hilel@beeriprint.co.il";
        Set<String> recipients = new HashSet<>();
        recipients.add("user1@domain");
        recipients.add("user@gmail.com");
        // compose test message
        MimeMessage message = createMessage(sender, recipients, "customers and others mix");
        // analyse result
        AnalysisResult result = engine.analyzeMessage(message);
        assertTrue(result.getStatusCode().equals(StatusCodes.othersMix));
    }

    public void test5() throws Exception {
        logger.info("testing accept attachment name");
        String sender = "hilel@beeriprint.co.il";
        Set<String> recipients = new HashSet<>();
        recipients.add("user1@domain");
        Set<String> attachments = new HashSet<>();
        attachments.add("123456789.doc");
        attachments.add("abc.tif");
        // compose test message
        MimeMessage message = createMessage(sender, recipients, "accept attachment name", attachments);
        // analyse result
        AnalysisResult result = engine.analyzeMessage(message);
        assertTrue(result.getStatusCode().equals(StatusCodes.attachmentsMismatch));
    }

    public void test6() throws Exception {
        logger.info("testing 2 domains of the same customer");
        String sender = "hilel@beeriprint.co.il";
        Set<String> recipients = new HashSet<>();
        recipients.add("user1@domain");
        recipients.add("user2@domain");
        // compose test message
        MimeMessage message = createMessage(sender, recipients, "2 domains of the same customer");
        // analyse result
        AnalysisResult result = engine.analyzeMessage(message);
        assertTrue(result.getStatusCode().equals(StatusCodes.OK));
    }

    public void test7() throws Exception {
        logger.info("testing accept attachment name");
        String sender = "hilel@beeriprint.co.il";
        Set<String> recipients = new HashSet<>();
        //recipients.add("user@cellcom.co.il");
        recipients.add("user1@domain");
        Set<String> attachments = new HashSet<>();
        attachments.add("abc.mp3");
        attachments.add("000abc.pdf");
        //attachments.add("311GENa01.tif");
        attachments.add("380-איביל - נושא המייל.xlsx‏");
        attachments.add("ATT00001.htm");
        // compose test message
        MimeMessage message = createMessage(sender, recipients, "accept attachment name", attachments);
        // analyse result
        AnalysisResult result = engine.analyzeMessage(message);
        assertTrue(result.getStatusCode().equals(StatusCodes.OK));
    }

    public void test8() throws Exception {
        logger.info("testing address with personal name");
        String sender = "\"Mr. Sender\" <sender1@beeriprint.co.il>";
        Set<String> recipients = new HashSet<>();
        recipients.add("\"The Recipient\" <recipient1@beeriprint.co.il>");
        // compose test message
        MimeMessage message = createMessage(sender, recipients, "address with personal name");
        // analyse result
        AnalysisResult result = engine.analyzeMessage(message);
        assertTrue(result.getFrom().equals("sender1@beeriprint.co.il"));
        assertTrue(result.getAllRecipients().iterator().next().equals("recipient1@beeriprint.co.il"));
    }

    private MimeMessage createMessage(String sender, Set<String> recipients, String subject)
            throws EmailException, MessagingException, MalformedURLException {
        return createMessage(sender, recipients, subject, new HashSet<String>());
    }

    private MimeMessage createMessage(String sender, Set<String> recipients, String subject, Set<String> attachments)
            throws EmailException, MessagingException, MalformedURLException {
        MultiPartEmail email = new MultiPartEmail();
        email.setCharset("UTF-8");
        email.setSubject(subject);
        email.setFrom(sender);
        for (String recipient : recipients) {
            email.addTo(recipient);
        }
        for (String attachment : attachments) {
            addAttachment(email, attachment);
        }
        email.setHostName("localhost");
        email.buildMimeMessage();
        return new MimeMessage(email.getMimeMessage());
    }

    private void addAttachment(MultiPartEmail email, String name) throws EmailException, MalformedURLException {
        EmailAttachment attachment = new EmailAttachment();
        attachment.setName(name);
        DataSource dataSource = new ClasspathDataSource("/par.jpg", "image/jpeg", name);
        email.attach(dataSource, name, "test image");
    }
}
