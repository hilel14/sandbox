package org.hilel14.file.upload.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
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

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of
     * org.hilel14.file.upload.demo.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        return "Hello";
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    public void putText(String content) {
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail)
            throws IOException {
        File out = Paths.get(System.getProperty("java.io.tmpdir")).resolve(fileDetail.getFileName()).toFile();
        byte[] data = IOUtils.toByteArray(uploadedInputStream);
        IOUtils.write(data, new FileOutputStream(out));
        LOGGER.log(Level.INFO, "File {0} uploaded successfully", out.getAbsolutePath());
        //return Response.status(200).entity(out).build();
    }
}
