package org.ganshaqed.sql2mail;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author hilel
 */
public class EmailSenderTest {

    public EmailSenderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSendEmail() throws Exception {
        Path reportFile = Paths.get("/var/opt/data/reports/testDefaultParamSetter.csv");
        String[] recipients = new String[]{"user1@localhost", "user2@localhost"};
        String subject = "testDefaultParamSetter report";
        String body = "testDefaultParamSetter report attached";
        EmailSender instance = new EmailSender();
        instance.sendEmail(reportFile, recipients, subject, body);
    }

}
