package contorApi.restservices;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by C311939 on 05.09.2016.
 */
@Path("/user")
@Stateless
public class UserREST {

    @Inject
    UserServiceREST service;

    @GET
    @Path("/add/{username}/{password}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addUser(@PathParam("username") String username, @PathParam("password") String password) {
        return Response.ok().entity(service.addUser(username, password)).build();
    }

    @GET
    @Path("/remove/{username}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response removeUser(@PathParam("username") String username) {
        return Response.ok().entity(service.remove(username)).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getUsers() {
        return Response.ok().entity(service.getUsers()).build();
    }
}
