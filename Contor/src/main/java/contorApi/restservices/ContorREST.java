package contorApi.restservices;

import contorApi.domUtils.ContorOperations;
import contorApi.domUtils.JsonMessage;
import contorApi.entities.Contor;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by C311939 on 02.08.2016.
 */
@Path("contor")
@RequestScoped
@Named
public class ContorREST {

    @Inject
    ContorServiceREST service;

    @GET
    @Path("list")
    @Produces(MediaType.TEXT_PLAIN)
    public Response print() {
        return Response.ok().entity(service.print()).build();
    }

    @GET
    @Path("add/{room}/{cold}/{warm}")
    @Produces(MediaType.APPLICATION_JSON)
    public String add(@PathParam("room") String room, @PathParam("cold") int cold, @PathParam("warm") int warm) {
        return service.add(room, cold, warm);
    }

    @GET
    @Path("remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String remove(@PathParam("id") int id) {
        return service.remove(id);
    }

    @GET
    @Path("printJSON")
    @Produces(MediaType.APPLICATION_JSON)
    public Response printJSON() {
        return Response.ok().entity(service.printJSON()).build();
    }

    @GET
    @Path("intervalChart/{startDay}/{startMonth}/{startYear}/{endDay}/{endMonth}/{endYear}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTimestampValues(@PathParam("startDay") int startDay, @PathParam("startMonth") int startMonth, @PathParam("startYear") int startYear,
                                     @PathParam("endDay") int endDay, @PathParam("endMonth") int endMonth, @PathParam("endYear") int endYear) {
        return service.getTimestampValues(startDay, startMonth, startYear, endDay, endMonth, endYear);
    }

    @GET
    @Path("monthlyChart/{month}/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMonthlyUsage(@PathParam("month") int month, @PathParam("year") int year) {
        return service.getMonthlyUsage(month, year);
    }

    @GET
    @Path("getEstimate")
    @Produces(MediaType.TEXT_PLAIN)
    public String getEstimate(){
        return service.getEstimate();
    }
}
