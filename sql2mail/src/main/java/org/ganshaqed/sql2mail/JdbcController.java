package org.ganshaqed.sql2mail;

import java.io.FileWriter;
import java.io.IOException;
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
import org.ganshaqed.sql2mail.statement.ParamSetter;

/**
 *
 * @author hilel
 */
public class JdbcController {

    static final Logger LOGGER = Logger.getLogger(JdbcController.class.getName());
    String url;
    String user;
    String password;

    public JdbcController() throws IOException {
        loadConnectionProperties();
    }

    private void loadConnectionProperties() throws IOException {
        Properties p = new Properties();
        p.load(JdbcController.class.getResourceAsStream("/jdbc.properties"));
        url = p.getProperty("url");
        user = p.getProperty("user");
        password = p.getProperty("password");
        LOGGER.log(Level.INFO, "Found database connection string {0}", url);
    }

    public void exportData(Path outPath, String query, ParamSetter paramSetter, String[] params)
            throws SQLException, IOException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(query)) {
            if (paramSetter != null) {
                paramSetter.setParams(statement, params);
            }
            try (ResultSet resultSet = statement.executeQuery();
                    FileWriter out = new FileWriter(outPath.toFile());
                    CSVPrinter printer = CSVFormat.DEFAULT.withHeader(resultSet).print(out)) {
                printer.printRecords(resultSet);
                LOGGER.log(Level.INFO, "Results saved to file {0}", outPath.toString());
            }
        }
    }
}
