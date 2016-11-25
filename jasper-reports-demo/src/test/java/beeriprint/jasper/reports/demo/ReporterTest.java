/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.jasper.reports.demo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Hilel
 */
public class ReporterTest {

    Path outFolder = Paths.get("C:\\data\\reports");

    @BeforeClass
    public static void setUpClass() throws Exception {
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
    public void testReport1() throws Exception {
        String template = "report-1";
        HashMap<String, Object> parameters = new HashMap<>();
        Reporter reporter = new Reporter();
        reporter.createReport(template, parameters, outFolder);
    }

    @Test
    public void testReport2() throws Exception {
        String template = "report-2";
        HashMap<String, Object> parameters = new HashMap<>();
        Reporter reporter = new Reporter();
        reporter.createReport(template, parameters, outFolder);
    }
}
