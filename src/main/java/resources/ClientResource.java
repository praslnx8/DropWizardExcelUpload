package resources;


import views.FileView;
import views.ParseView;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Juan on 25/02/2015.
 */
@Path("/client")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource
{

    @GET
    @Path("/uploadFile")
    public FileView uploadFile() {
        return new FileView();
    }

    @GET
    @Path("/parse")
    public ParseView parseData() {
        return new ParseView();
    }
}
