package org.ganshaqed.sql2mail;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
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

    Charset charset = Charset.defaultCharset();
    Path outFolder = Paths.get("/var/opt/data/reports");

    public JdbcControllerTest() throws IOException {

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
        Path out = outFolder.resolve("testLimitResultsParamSetter.csv");
        String qry = "SELECT * FROM people LIMIT ?";
        PreparedStatementParamSetter paramSetter = new LimitResultsParamSetter();
        String[] params = new String[]{"3"};
        controller.exportData(out, charset, qry, paramSetter, params);
    }

    @Test
    public void testLastDaysParamSetter() throws IOException, SQLException {
        JdbcController controller = new JdbcController();
        String qry = "SELECT * FROM people WHERE birth_date > ? and birth_date < ?";
        PreparedStatementParamSetter paramSetter = new LastDaysParamSetter();
        Path out = outFolder.resolve("testLastDaysParamSetter.csv");
        String[] params = new String[]{"365"};
        controller.exportData(out, charset, qry, paramSetter, params);
    }

    @Test
    public void testDefaultParamSetter() throws Exception {
        JdbcController controller = new JdbcController();
        String qry = "SELECT first_name FROM people";
        PreparedStatementParamSetter paramSetter = (PreparedStatementParamSetter) Class.forName("org.ganshaqed.sql2mail.statement.DefaultParamSetter").newInstance();
        Path out = outFolder.resolve("testDefaultParamSetter.csv");
        controller.exportData(out, charset, qry, paramSetter, null);
    }
}
