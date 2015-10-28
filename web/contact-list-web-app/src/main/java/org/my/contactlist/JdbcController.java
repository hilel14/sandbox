/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.contactlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author c7
 */
public class JdbcController {

    private final InitialContext context;
    private final DataSource dataSource;
    private final String getAllContacts = "SELECT * FROM contacts";
    private final String createContact = "INSERT INTO contacts (first_name, last_name, phone, email) VALUES (?,?,?,?)";
    private final String updateContact = "UPDATE contacts SET first_name = ?, last_name = ?, phone = ?, email = ? WHERE id = ?";
    private final String deleteContact = "DELETE FROM contacts WHERE id = ?";

    public JdbcController() throws NamingException {
        context = new InitialContext();
        dataSource = (DataSource) context.lookup("java:comp/env/jdbc/TestDB");
    }

    public List<Contact> getAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(getAllContacts);
            try (ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    Contact contatc = new Contact();
                    contatc.setId(rs.getInt("id"));
                    contatc.setFirstName(rs.getString("first_name"));
                    contatc.setLastName(rs.getString("last_name"));
                    contatc.setEmail(rs.getString("email"));
                    contatc.setPhone(rs.getString("phone"));
                    contacts.add(contatc);
                }
            }
        }
        return contacts;
    }

    public Integer createContact(Contact contact) throws SQLException {
        try (Connection connection = dataSource.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(createContact, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getPhone());
            statement.setString(4, contact.getEmail());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys();) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public void updateContact(Contact contact) throws SQLException {
        try (Connection connection = dataSource.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(updateContact);
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getPhone());
            statement.setString(4, contact.getEmail());
            statement.setInt(5, contact.getId());
            statement.executeUpdate();
        }
    }

    public void deleteContact(String contactId) throws SQLException {
        try (Connection connection = dataSource.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(deleteContact);
            statement.setString(1, contactId);
            statement.execute();
        }
    }
}
