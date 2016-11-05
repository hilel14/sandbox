package org.ganshaqed.jasperstuff;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author hilel
 */
public class Simple {

    static final Logger LOGGER = Logger.getLogger(Simple.class.getName());

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Mandatory arguments: report-name out-file");
            System.exit(1);
        }
        String reportName = args[0];
        String outFile = args[1];
        HashMap<String, Object> parameters = new HashMap<>();
        JREmptyDataSource dataSource = new JREmptyDataSource();
        try (InputStream inStream = Main.class.getResourceAsStream("/reports/" + reportName + ".xml")) {
            JasperReport jasperReport = JasperCompileManager.compileReport(inStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, outFile);
        } catch (JRException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
