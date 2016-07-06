package org.ganshaqed.sql2mail;

import java.io.IOException;
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

    Path inFolder;

    public EmailSenderTest() throws IOException {
        inFolder = Paths.get("/var/opt/data/reports");
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
        Path reportFile = inFolder.resolve("testDefaultParamSetter.csv");
        String[] recipients = new String[]{"user1@localhost", "user2@localhost"};
        String subject = "testDefaultParamSetter report";
        String body = "testDefaultParamSetter report attached";
        EmailSender instance = new EmailSender();
        instance.sendEmail(reportFile, recipients, subject, body);
    }

}
