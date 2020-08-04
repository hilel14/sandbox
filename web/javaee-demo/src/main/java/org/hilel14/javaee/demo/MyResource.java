package org.hilel14.javaee.demo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author hilel14
 */
@Path("test")
public class MyResource {

    static final Logger LOGGER = Logger.getLogger(MyResource.class.getName());

    @Inject
    MyEjb ejb;

    @GET
    @Path("1")
    @Produces(MediaType.TEXT_PLAIN)
    public String test1() {
        LOGGER.log(Level.INFO, "method 1");
        return "1";
    }

    @GET
    @Path("2")
    @Produces(MediaType.TEXT_PLAIN)
    public String test2() {
        LOGGER.log(Level.INFO, "calling long task...");
        ejb.doLongTaskInBackground();
        return "2";
    }
}
