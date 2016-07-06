package org.ganshaqed.sql2mail;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.ganshaqed.sql2mail.statement.LimitResultsParamSetter;
import org.ganshaqed.sql2mail.statement.ParamSetter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author hilel
 */
public class JdbcControllerTest {

    /*
        Init DB:
        Start H2 database, by running the following command
        (replace /var/opt/h2 with a folder on your machine):
        java -cp  ~/.m2/repository/com/h2database/h2/1.4.192/h2-1.4.192.jar org.h2.tools.Server -tcp -web -baseDir /var/opt/h2
        CREATE TABLE contacts(ID INT PRIMARY KEY, first_name VARCHAR(255), last_name VARCHAR(255));
     */
    public JdbcControllerTest() {
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
    public void testNullParamSetter() throws Exception {
        JdbcController controller = new JdbcController();
        String qry = "SELECT first_name FROM contacts";
        Path out = Paths.get("/tmp/out.csv");
        controller.exportData(out, qry, null, null);
    }

    //@Test
    public void testLimitResultsParamSetter() throws Exception {
        JdbcController controller = new JdbcController();
        // job
        String qry = "SELECT * FROM contacts LIMIT ?";
        ParamSetter paramSetter = new LimitResultsParamSetter();
        String recipients = "user1@example.com, user2@example.net";
        // cli args
        Path out = Paths.get("/tmp/out.csv");
        String[] params = new String[]{"5"};
        controller.exportData(out, qry, paramSetter, params);
    }

}
