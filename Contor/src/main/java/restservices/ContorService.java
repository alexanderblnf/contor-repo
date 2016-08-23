package restservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domUtils.ContorOperations;
import domUtils.ContorTuples;
import domUtils.JsonMessage;
import entities.Contor;
import org.apache.commons.math3.stat.regression.RegressionResults;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by C311939 on 02.08.2016.
 */
@Path("contor")
@RequestScoped
@Named
public class ContorService {

    @Inject
    ContorOperations op;

    @GET
    @Path("list")
    @Produces(MediaType.TEXT_PLAIN)
    public String print() {
        List<Contor> values = op.getContorValues();
        String out = new String ();

        for(Contor c : values) {
            out += c.toString();
        }

        return out;
    }

    @GET
    @Path("add/{room}/{cold}/{warm}")
    @Produces(MediaType.APPLICATION_JSON)
    public String add(@PathParam("room") String room, @PathParam("cold") int cold, @PathParam("warm") int warm) {
        Contor c = new Contor(room, cold, warm);
        JsonMessage message;

        if (op.add(c) == 1) {
            message = new JsonMessage("Add successful");
            return message.toJson();
        } else if(op.add(c) == 0){
            message = new JsonMessage("Entry already exists");
            return message.toJson();
        } else {
            message = new JsonMessage("The value entered is less than the value entered last month");
            return message.toJson();
        }

    }

    @GET
    @Path("remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String remove(@PathParam("id") int id) {
        Contor c = new Contor("", 0, 0);
        c.setId(id);
        JsonMessage message;

        if (op.remove(c)) {
            message = new JsonMessage("Remove successful");
            return message.toJson();
        } else {
            message = new JsonMessage("Error removing");
            return message.toJson();
        }
    }

    @GET
    @Path("printJSON")
    @Produces(MediaType.APPLICATION_JSON)
    public String printJSON() {
        return op.printJSON();
    }

    @GET
    @Path("intervalChart/{startDay}/{startMonth}/{startYear}/{endDay}/{endMonth}/{endYear}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTimestampValues(@PathParam("startDay") int startDay, @PathParam("startMonth") int startMonth, @PathParam("startYear") int startYear,
                                     @PathParam("endDay") int endDay, @PathParam("endMonth") int endMonth, @PathParam("endYear") int endYear) {
        Date start = new GregorianCalendar(startYear, startMonth - 1, startDay).getTime();
        Date end = new GregorianCalendar(endYear, endMonth - 1, endDay).getTime();

        Contor c1 = new Contor();
        c1.setTime(start);

        Contor c2 = new Contor();
        c2.setTime(end);

        return op.printDateJSON(c1, c2);
    }

    @GET
    @Path("monthlyChart/{month}/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMonthlyUsage(@PathParam("month") int month, @PathParam("year") int year) {

        Date currentMonth = new GregorianCalendar(year, month - 1, 1).getTime();
        Date previousMonth;

        if(month == 1){
            previousMonth = new GregorianCalendar(year - 1, 11, 1).getTime();
        }
        else {
            previousMonth = new GregorianCalendar(year, month - 2, 1).getTime();
        }

        Contor c = new Contor();
        c.setTime(currentMonth);

        Contor c1 = new Contor();
        c1.setTime(previousMonth);

        return op.printMonthUsage(c, c1);
    }

    @GET
    @Path("getEstimate")
    @Produces(MediaType.TEXT_PLAIN)
    public String getEstimate(){
        Date date = new Date();

        Contor c = new Contor();
        c.setTime(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int mth = cal.get(Calendar.MONTH) + 1;

        List<SimpleRegression> regressions = op.getPrediction(c);

        List<Double> predictions = new ArrayList<Double>();
        String out = new String();
        for(SimpleRegression regress : regressions) {
            out += regress.predict(mth) + " ";
            predictions.add(regress.predict(mth));
        }

        return out;
    }


}
