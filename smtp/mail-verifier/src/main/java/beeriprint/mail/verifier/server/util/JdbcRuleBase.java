/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.mail.verifier.server.util;

import beeriprint.mail.verifier.server.RuleBase;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads data from the application database.
 *
 * @author hilel
 */
public class JdbcRuleBase implements RuleBase {

    private static final Logger logger = LoggerFactory.getLogger(JdbcRuleBase.class);
    private final String driver;
    private final String url;
    private final String user;
    private final String password;
    private final Map<String, Set<String>> domainToCustomer = new HashMap<>();
    private final Set<String> senderWhiteList = new HashSet<>();
    private final Set<String> domainWhiteList = new HashSet<>();
    private final Set<Pattern> specialAttachmentNames = new HashSet<>();

    public JdbcRuleBase() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        properties.load(JdbcRuleBase.class.getResourceAsStream("/jdbc.properties"));
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        loadData();
    }

    private void loadData() throws IOException, ClassNotFoundException, SQLException {
        Class.forName(driver);
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            findCustomerDomains(connection);
            logger.info(domainToCustomer.size() + " records found in customer-domain table");
            fillSenderWhiteList(connection);
            logger.info(senderWhiteList.size() + " records found in white sender list");
            fillDomainWhiteList(connection);
            logger.info(domainWhiteList.size() + " records found in white domain list");
            fillSpecialAttachmentNames(connection);
            logger.info(specialAttachmentNames.size() + " regular expressions found in special attachment names list");
        }
    }

    private void findCustomerDomains(Connection connection) throws SQLException {
        String query = "SELECT * FROM customer_domain";
        PreparedStatement statement = connection.prepareStatement(query);
        try (ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                String domain = resultSet.getString("customer_domain");
                String code = resultSet.getString("customer_code");
                if (!domainToCustomer.containsKey(domain)) {
                    domainToCustomer.put(domain, new HashSet<String>());
                }
                domainToCustomer.get(domain).add(code);
            }
        }
    }

    private void fillSenderWhiteList(Connection connection) throws SQLException {
        String query = "SELECT * FROM white_sender_list";
        PreparedStatement statement = connection.prepareStatement(query);
        try (ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                senderWhiteList.add(resultSet.getString("email"));
            }
        }
    }

    private void fillDomainWhiteList(Connection connection) throws SQLException {
        String query = "SELECT * FROM white_domain_list";
        PreparedStatement statement = connection.prepareStatement(query);
        try (ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                domainWhiteList.add(resultSet.getString("domain_name"));
            }
        }
    }

    private void fillSpecialAttachmentNames(Connection connection) throws SQLException {
        String query = "SELECT * FROM special_attachment_names";
        PreparedStatement statement = connection.prepareStatement(query);
        try (ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                specialAttachmentNames.add(Pattern.compile(resultSet.getString("attachment_file_name")));
            }
        }
    }

    /**
     * @return the domainToCustomer
     */
    @Override
    public Map<String, Set<String>> getDomainToCustomer() {
        return domainToCustomer;
    }

    /**
     * @return the senderWhiteList
     */
    @Override
    public Set<String> getSenderWhiteList() {
        return senderWhiteList;
    }

    /**
     * @return the domainWhiteList
     */
    @Override
    public Set<String> getDomainWhiteList() {
        return domainWhiteList;
    }

    /**
     * @return the specialAttachmentNames
     */
    @Override
    public Set<Pattern> getSpecialAttachmentNames() {
        return specialAttachmentNames;
    }
}
