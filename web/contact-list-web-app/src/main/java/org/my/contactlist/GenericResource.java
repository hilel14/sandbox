/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.contactlist;

import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author c7
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    private final JdbcController controller;

    public GenericResource() throws NamingException {
        controller = new JdbcController();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Contact createContact(Contact contact) throws SQLException {
        int id = controller.createContact(contact);
        contact.setId(id);
        return contact;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contact> getAllContacts() throws SQLException {
        return controller.getAllContacts();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateContact(Contact contact) throws SQLException {
        controller.updateContact(contact);
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    public void deleteContact(String contactId) throws SQLException {
        controller.deleteContact(contactId);
    }

}
