/**
 * http://www.tutorialspoint.com/jasper_reports/jasper_report_fonts.htm
 */
package beeriprint.jasper.reports.demo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 *
 * @author hilel
 */
public class Reporter {

    static final Logger LOGGER = Logger.getLogger(Reporter.class.getName());

    public void createReport(String reportName, HashMap<String, Object> parameters, Path outFolder)
            throws IOException, SQLException, ClassNotFoundException, JRException {
        LOGGER.log(Level.INFO, "Processing report {0}", reportName);
        try (
                InputStream templateStream = Reporter.class.getResourceAsStream("/templates/" + reportName + ".xml");
                Connection connection = loadConnection();) {
            if (templateStream == null) {
                System.out.println("Report template not found");
                System.exit(1);
            }

            // compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            // Export PDF
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            printTitle(jasperPrint);
            exportPdf(jasperPrint, outFolder, parameters);

            // Export Excell and HTML
            parameters.put("IS_IGNORE_PAGINATION", true);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            exportExcell(jasperPrint, outFolder, parameters);
            exportHtml(jasperPrint, outFolder, parameters);

            // Done
            LOGGER.log(Level.INFO, "Reports saved to {0}", outFolder);
        }
    }

    private void exportPdf(JasperPrint jasperPrint, Path outFolder, HashMap<String, Object> parameters)
            throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(
                        outFolder.resolve(jasperPrint.getName() + ".pdf").toString()));
        exporter.exportReport();
    }

    private void exportExcell(JasperPrint jasperPrint, Path outFolder, HashMap<String, Object> parameters)
            throws JRException {

        // create report configuration
        SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
        reportConfig.setRemoveEmptySpaceBetweenColumns(true);
        reportConfig.setRemoveEmptySpaceBetweenRows(true);
        reportConfig.setWhitePageBackground(false);
        reportConfig.setDetectCellType(true);

        // create exporter configuration
        SimpleXlsxExporterConfiguration exportConfig = new SimpleXlsxExporterConfiguration();

        // configure the exporter
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput(
                        outFolder.resolve(jasperPrint.getName() + ".xlsx").toString()));
        // export
        exporter.exportReport();
    }

    private void exportHtml(JasperPrint jasperPrint, Path outFolder, HashMap<String, Object> parameters)
            throws JRException {
        HtmlExporter exporter = new HtmlExporter();
        /*
        SimpleHtmlExporterConfiguration exporterConfig = new SimpleHtmlExporterConfiguration();
        exporterConfig.setBetweenPagesHtml("");
        exporter.setConfiguration(exporterConfig);

         */
        SimpleHtmlReportConfiguration reportConfig = new SimpleHtmlReportConfiguration();
        reportConfig.setRemoveEmptySpaceBetweenRows(true);
        exporter.setConfiguration(reportConfig);
        // configure the exporter
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(
                new SimpleHtmlExporterOutput(
                        outFolder.resolve(jasperPrint.getName() + ".html").toString()));
        // export
        exporter.exportReport();
    }

    private void printTitle(JasperPrint jasperPrint) {
        // Works only with textFieldExpression
        JRPrintElement firstElement = jasperPrint.getPages().get(0).getElements().get(0);
        switch (firstElement.getClass().getCanonicalName()) {
            case "net.sf.jasperreports.engine.fill.JRTemplatePrintText":
                JRTemplatePrintText title = (JRTemplatePrintText) firstElement;
                LOGGER.log(Level.INFO, "Title: {0}", title.getValue());
                break;
            default:
                LOGGER.log(Level.INFO, "Unknown title class: {0}", firstElement.getClass().getCanonicalName());
        }
    }

    public Connection loadConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties p = new Properties();
        p.load(Reporter.class.getResourceAsStream("/jdbc.properties"));
        String driver = p.getProperty("driver");
        String url = p.getProperty("url");
        String user = p.getProperty("user");
        String password = p.getProperty("password");
        LOGGER.log(Level.INFO, "Connecting to {0}", url);
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}
