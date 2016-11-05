/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ganshaqed.jasperstuff;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author hilel
 */
public class Main {

    static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Mandatory arguments: report-name out-file");
            System.exit(1);
        }
        String reportName = args[0];
        String outFile = args[1];
        LOGGER.log(Level.INFO, "Processing report {0}", reportName);
        String reportTemplate = "/reports/" + reportName + ".xml";
        HashMap<String, Object> parameters = loadParameters(reportName);
        try (
                InputStream inStream = Main.class.getResourceAsStream(reportTemplate);
                Connection connection = loadConnection();) {
            if (inStream == null) {
                System.out.println("Report template not found: " + reportTemplate);
                System.exit(1);
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(inStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            JasperExportManager.exportReportToPdfFile(jasperPrint, outFile);
            LOGGER.log(Level.INFO, "Report saved to {0}", outFile);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    static HashMap<String, Object> loadParameters(String reportName) {
        HashMap<String, Object> map = new HashMap<>();
        try (InputStream inStream = Main.class.getResourceAsStream("/reports/" + reportName + ".properties")) {
            if (inStream != null) {
                LOGGER.log(Level.INFO, "Loading parameters for {0}", reportName);
                Properties props = new Properties();
                props.load(inStream);
                props.stringPropertyNames().stream().forEach((key) -> {
                    map.put(key, props.getProperty(key));
                });
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return map;
    }

    static Connection loadConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties p = new Properties();
        p.load(Main.class.getResourceAsStream("/jdbc.properties"));
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
