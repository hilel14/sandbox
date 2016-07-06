package org.ganshaqed.sql2mail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import org.ganshaqed.sql2mail.statement.DefaultParamSetter;
import org.ganshaqed.sql2mail.statement.LastDaysParamSetter;
import org.ganshaqed.sql2mail.statement.LimitResultsParamSetter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ganshaqed.sql2mail.statement.PreparedStatementParamSetter;

/**
 *
 * @author hilel
 */
public class JdbcControllerTest {

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
    public void testLimitResultsParamSetter() throws Exception {
        JdbcController controller = new JdbcController();
        // job
        String qry = "SELECT * FROM people LIMIT ?";
        PreparedStatementParamSetter paramSetter = new LimitResultsParamSetter();
        // cli args
        Path out = Paths.get("/var/opt/data/reports/testLimitResultsParamSetter.csv");
        String[] params = new String[]{"3"};
        controller.exportData(out, qry, paramSetter, params);
    }

    @Test
    public void testLastDaysParamSetter() throws IOException, SQLException {
        JdbcController controller = new JdbcController();
        String qry = "SELECT * FROM people WHERE birth_date > ? and birth_date < ?";
        PreparedStatementParamSetter paramSetter = new LastDaysParamSetter();
        Path out = Paths.get("/var/opt/data/reports/testLastDaysParamSetter.csv");
        String[] params = new String[]{"365"};
        controller.exportData(out, qry, paramSetter, params);
    }

    @Test
    public void testDefaultParamSetter() throws Exception {
        JdbcController controller = new JdbcController();
        String qry = "SELECT first_name FROM people";
        PreparedStatementParamSetter paramSetter = new DefaultParamSetter();
        Path out = Paths.get("/var/opt/data/reports/testDefaultParamSetter.csv");
        controller.exportData(out, qry, paramSetter, null);
    }
}
