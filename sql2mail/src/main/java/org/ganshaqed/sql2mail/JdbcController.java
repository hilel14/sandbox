package org.ganshaqed.sql2mail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ganshaqed.sql2mail.model.Job;

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

    public List<List<String>> exportData(Job job, String params) throws SQLException, IOException {
        List<List<String>> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(job.getQuery())) {
            if (params != null) {
                LOGGER.log(Level.CONFIG, "Parsing statement params: {0}", params);
                job.getParamSetter().setParams(statement, params.split(","));
            }
            try (ResultSet rs = statement.executeQuery();) {
                final int columnCount = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    List<String> list = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        list.add(rs.getString(i));
                    }
                    results.add(list);
                }
            }
        }
        return results;
    }
}
