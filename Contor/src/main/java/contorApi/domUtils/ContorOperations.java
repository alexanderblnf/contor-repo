package contorApi.domUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import contorApi.dateUtils.MonthUtils;
import contorApi.entities.Contor;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.transaction.NotSupportedException;
import javax.transaction.UserTransaction;
import javax.transaction.*;
import java.util.*;

/**
 * Created by C311939 on 02.08.2016.
 */
@Stateless
public class ContorOperations {

    @Inject
    ContorDAO contorDAO;

    public List<SimpleRegression> getPrediction(Contor month) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(month.getTime());

        int year = cal.get(Calendar.YEAR);
        int currMonth = cal.get(Calendar.MONTH) + 1;

        List<SimpleRegression> regressions = new ArrayList<SimpleRegression>();
        SimpleRegression r = new SimpleRegression(), r1 = new SimpleRegression(), r2 = new SimpleRegression(), r3 = new SimpleRegression();

        for(int i = 0; i < currMonth; i++) {
            Date aux = new GregorianCalendar(year, i, 1).getTime();
            Contor cAux = new Contor();
            cAux.setTime(aux);
            Calendar cals = Calendar.getInstance();
            cals.setTime(aux);

            List<ContorTuples> max = contorDAO.getMonthMaximum(cAux);

            if(max.get(0).getCold() != 0 && max.get(0).getWarm() != 0 && max.get(1).getCold() != 0 && max.get(1).getWarm() != 0) {
                r.addData(i, max.get(0).getCold());
                r1.addData(i, max.get(0).getWarm());
                r2.addData(i, max.get(1).getCold());
                r3.addData(i, max.get(1).getWarm());
            }
        }
        regressions.add(r);
        regressions.add(r1);
        regressions.add(r2);
        regressions.add(r3);

        return regressions;
    }

    @Produces
    public List<ContorTuples> getUsagePerMonth(Contor currentMonth, Contor previousMonth) {
        List<ContorTuples> t1 = contorDAO.getMonthMaximum(currentMonth);
        List<ContorTuples> t2 = contorDAO.getMonthMaximum(previousMonth);

        if (t1.get(0).getWarm() == 0 || t1.get(0).getCold() == 0 || t1.get(1).getWarm() == 0 || t1.get(1).getCold() == 0 || t2.get(0).getWarm() == 0 || t2.get(0).getCold() == 0 || t2.get(1).getWarm() == 0 || t2.get(1).getCold() == 0) {
            List<ContorTuples> nulls = new ArrayList<ContorTuples>();
            nulls.add(new ContorTuples("baie", 0, 0));
            nulls.add(new ContorTuples("wc", 0, 0));
            return nulls;
        }

        List<ContorTuples> tuples = new ArrayList<ContorTuples>();
        tuples.add(new ContorTuples(t1.get(0).getRoom(), t1.get(0).getCold() - t2.get(0).getCold(), t1.get(0).getWarm() - t2.get(0).getWarm()));
        tuples.add(new ContorTuples(t1.get(1).getRoom(), t1.get(1).getCold() - t2.get(1).getCold(), t1.get(1).getWarm() - t2.get(1).getWarm()));

        return tuples;
    }

    @Produces
    public String printMonthUsage(Contor currentMonth, Contor previousMonth) {
        List<ContorTuples> tuples = getUsagePerMonth(currentMonth, previousMonth);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(tuples);
    }

}
