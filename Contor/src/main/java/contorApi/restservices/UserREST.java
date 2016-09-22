package contorApi.restservices;

import contorApi.entities.Users;
import contorApi.jsonObjects.UserJson;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
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

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response addUser(UserJson userJson) {
        return Response.ok().entity(service.addUser(userJson)).build();
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


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response login(UserJson userJson) {
        return Response.ok().entity(service.login(userJson)).build();
    }

    @GET
    @Path("/islogged")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response verifyIfLoggedIn() {
        return Response.ok().entity(service.verifyIfLoggedIn()).build();
    }

}
