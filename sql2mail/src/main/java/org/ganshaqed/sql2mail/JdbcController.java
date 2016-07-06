package org.ganshaqed.sql2mail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.ganshaqed.sql2mail.statement.PreparedStatementParamSetter;

/**
 *
 * @author hilel
 */
public class JdbcController {

    static final Logger LOGGER = Logger.getLogger(JdbcController.class.getName());
    private String url;
    private String user;
    private String password;

    public JdbcController() throws IOException {
        loadConnectionProperties();
    }

    private void loadConnectionProperties() throws IOException {
        Properties p = new Properties();
        p.load(JdbcController.class.getResourceAsStream("/jdbc.properties"));
        url = p.getProperty("url");
        user = p.getProperty("user");
        password = p.getProperty("password");
        LOGGER.log(Level.INFO, "Database connection string {0}", url);
    }

    public void exportData(Path outPath, Charset charset, String query, PreparedStatementParamSetter paramSetter, String[] params)
            throws SQLException, IOException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(query)) {
            paramSetter.setParams(statement, params);
            try (ResultSet resultSet = statement.executeQuery();
                    OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outPath.toFile()), charset);
                    CSVPrinter printer = CSVFormat.DEFAULT.withHeader(resultSet).print(out)) {
                printer.printRecords(resultSet);
                LOGGER.log(Level.INFO, "Results saved to file {0}", outPath.toString());
            }
        }
    }
}
