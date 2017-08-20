package org.hilel14.spa.security.demo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * REST Web Service
 *
 * @author hilel
 */
@Path("generic")
public class GenericResource {

    static final Logger LOGGER = Logger.getLogger(GenericResource.class.getName());

    @Context
    private UriInfo context;

    @Context
    private Configuration config;

    public GenericResource() {

    }

    private String getRole(String user, String password) {
        if (user.startsWith("m")) {
            return "manager";
        } else {
            return "guest";
        }
    }

    @PermitAll
    @PUT
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(
            @FormDataParam("user") String user,
            @FormDataParam("password") String password) {
        LOGGER.log(Level.INFO, "user name {0}", user);
        LOGGER.log(Level.INFO, "password {0}", password);
        Key key = (Key) config.getProperty("jwt.key");
        String role = getRole(user, password);
        String jwt = Jwts.builder()
                .setSubject(role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwt;
    }

    @PermitAll
    @GET
    @Path("public")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "{\"data\":\"no secrets here\"}";
    }

    @RolesAllowed("manager")
    @GET
    @Path("secret")
    @Produces(MediaType.APPLICATION_JSON)
    public Secret getSecret() {
        Secret secret = new Secret();
        return secret;
    }
}
