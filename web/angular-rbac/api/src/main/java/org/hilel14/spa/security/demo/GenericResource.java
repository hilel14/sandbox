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

/**
 * REST Web Service
 *
 * @author hilel
 */
@Path("generic")
public class GenericResource {

    static final Logger LOGGER = Logger.getLogger(GenericResource.class.getName());
    AuthenticationEngine engine;

    @Context
    private UriInfo context;

    @Context
    private Configuration config;

    public GenericResource() {
        engine = new AuthenticationEngine();
    }

    @PermitAll
    @PUT
    @Path("authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Credentials authenticate(Credentials credentials) {
        LOGGER.log(Level.INFO, "user name {0}", credentials.getUsername());
        // Get role and create token for the user
        Key key = (Key) config.getProperty("jwt.key");
        String role = engine.getRole(credentials.getUsername(), credentials.getPassword());
        String jwt = Jwts.builder()
                .setSubject(role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        // Reuse credentials object to return permissions
        credentials.setPassword("***");
        credentials.setRole(role);
        credentials.setToken(jwt);
        return credentials;
    }

    @PermitAll
    @GET
    @Path("public")
    @Produces(MediaType.APPLICATION_JSON)
    public Data getPublicData() {
        return new Data("This data is public");
    }

    @RolesAllowed("manager")
    @GET
    @Path("secret")
    @Produces(MediaType.APPLICATION_JSON)
    public Data getSecret() {
        return new Data("This data is for managers only");
    }
}
