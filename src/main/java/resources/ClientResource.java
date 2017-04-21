package resources;


import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import views.FileView;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Juan on 25/02/2015.
 */
@Path("/client")
@Api(value = "/client", description = "End point for customer related")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource
{

    @GET
    @Path("/uploadFile")
    @ApiOperation(value="Return ApiResponseData", response = Response.class, notes="some day this will do more, it believes in a growth mentality.")
    @ApiResponses(value={
            @ApiResponse(code=400, message="Invalid ID")
    })
    public FileView uploadFile() {
        return new FileView();
    }

}
