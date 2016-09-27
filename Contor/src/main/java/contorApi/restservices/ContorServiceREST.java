package contorApi.restservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import contorApi.converter.UserConverter;
import contorApi.domUtils.*;
import contorApi.entities.Contor;
import contorApi.entities.Users;
import contorApi.jsonObjects.UserJson;
import contorApi.security.SessionStore;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by C311939 on 05.09.2016.
 */
@Stateless
public class ContorServiceREST {

    @Inject
    ContorOperations op;

    @Inject
    ContorDAO contorDAO;

    @Inject
    UserDAO userDAO;

    @Inject
    SessionStore sessionStore;

    public String print() {
        List<Contor> values = contorDAO.getContorValues();
        String out = new String();

        for (Contor c : values) {
            out += c.toString();
        }

        return out;
    }

    public String add(String room, int cold, int warm) {

        Users aux = userDAO.getByUsername(sessionStore.getUsername());

        Contor c = new Contor(room, cold, warm, aux);
        JsonMessage message;

        if (contorDAO.add(c) == 1) {
            message = new JsonMessage("Add successful");
            return message.toJson();
        } else if(contorDAO.add(c) == 0){
            message = new JsonMessage("Entry already exists");
            return message.toJson();
        } else {
            message = new JsonMessage("The value entered is less than the value entered last month");
            return message.toJson();
        }
    }

    public String remove(int id) {
        Contor c = new Contor("", 0, 0, new Users());
        c.setId(id);
        JsonMessage message;

        if (contorDAO.remove(c)) {
            message = new JsonMessage("Remove successful");
            return message.toJson();
        } else {
            message = new JsonMessage("Error removing");
            return message.toJson();
        }
    }

    public String printJSON() {
        List<Contor> values = contorDAO.getContorValuesforUser();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        return gson.toJson(values);
    }

    public String printDateJSON(Contor start, Contor end) {
        List<Contor> values = contorDAO.getValuesBetweenDatesForUser(start, end);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(values);
    }

    public String getTimestampValues(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
        Date start = new GregorianCalendar(startYear, startMonth - 1, startDay).getTime();
        Date end = new GregorianCalendar(endYear, endMonth - 1, endDay).getTime();

        Contor c1 = new Contor();
        c1.setTime(start);

        Contor c2 = new Contor();
        c2.setTime(end);

        return printDateJSON(c1, c2);
    }

    public String getMonthlyUsage(int month, int year) {
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

    public String getEstimate() {
        Date date = new Date();

        Contor c = new Contor();
        c.setTime(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int mth = cal.get(Calendar.MONTH) + 1;

        List<SimpleRegression> regressions = op.getPrediction(c);

        List<Double> predictions = new ArrayList<Double>();
        List<ContorTuples> out = new ArrayList<ContorTuples>();
        for(SimpleRegression regress : regressions) {
            predictions.add(regress.predict(mth));
        }
        out.add(new ContorTuples("baie", (int)Math.round(predictions.get(0)), (int)Math.round(predictions.get(1))));
        out.add(new ContorTuples("wc", (int)Math.round(predictions.get(2)), (int)Math.round(predictions.get(3))));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(out);
    }
}
