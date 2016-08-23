package domUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dateUtils.MonthUtils;
import domUtils.ContorDB;
import entities.Contor;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
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
@TransactionManagement(TransactionManagementType.BEAN)
public class ContorOperations {

    @ContorDB
    @Inject
    EntityManager entityManager;

    @Inject
    MonthUtils m;

    @Resource
    UserTransaction tx;

    void beginTransaction() {
        try {
            tx.begin();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    void commitTransaction() {
        try {
            tx.commit();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyValidAdd(Contor c) {

        Date date = new Date();

        Date previousMonth = m.getPreviousMonth(date);

        Contor previousContor = new Contor();
        previousContor.setTime(previousMonth);

        List<ContorTuples> previousValues = getMonthMaximum(previousContor);

        for(ContorTuples ct : previousValues) {
            if(ct.getRoom().contains(c.getRoom())) {
                if (c.getCold() >= ct.getCold() && c.getWarm() >= ct.getWarm()) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

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

            List<ContorTuples> max = getMonthMaximum(cAux);

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

    public List<ContorTuples> getGlobalAverage(Contor month) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(month.getTime());

        int year = cal.get(Calendar.YEAR);
        int currMonth = cal.get(Calendar.MONTH);

        List<ContorTuples> avg = new ArrayList<ContorTuples>();
        int count = 0;

        avg.add(new ContorTuples("baie", 0, 0));
        avg.add(new ContorTuples("wc", 0, 0));

        for(int i = 0; i < 12; i++) {
            if(i != currMonth) {
                Date aux = new GregorianCalendar(year, i, 1).getTime();
                Date prevAux =new GregorianCalendar(year, i - 1, 1).getTime();
                Contor cAux = new Contor();
                Contor cPrevAux = new Contor();

                cAux.setTime(aux);
                cPrevAux.setTime(prevAux);

                List<ContorTuples> avgAux = getUsagePerMonth(cAux, cPrevAux);


                if(avgAux.get(0).getCold() != 0 && avgAux.get(0).getWarm() != 0 && avgAux.get(1).getCold() != 0 && avgAux.get(1).getWarm() != 0) {
                    avg.get(0).setCold(avg.get(0).getCold() + avgAux.get(0).getCold());
                    avg.get(0).setWarm(avg.get(0).getWarm() + avgAux.get(0).getWarm());

                    avg.get(1).setCold(avg.get(1).getCold() + avgAux.get(1).getCold());
                    avg.get(1).setWarm(avg.get(1).getWarm() + avgAux.get(1).getWarm());

                    count ++;
                }
            }
        }

        avg.get(0).setCold(avg.get(0).getCold() / count);
        avg.get(0).setWarm(avg.get(0).getWarm() / count);

        avg.get(1).setCold(avg.get(1).getCold() / count);
        avg.get(1).setWarm(avg.get(1).getWarm() / count);

        return avg;

    }

    @Produces
    public int add(Contor c) {
        Contor aux = entityManager.find(Contor.class, c.getId());

        if (aux != null) {
            return 0;
        } else {
            if (verifyValidAdd(c) == false) {
                return 2;
            } else {
                beginTransaction();

                entityManager.persist(c);

                commitTransaction();

                return 1;
            }

        }
    }

    @Produces
    public boolean remove(Contor c) {
        Contor aux = entityManager.find(Contor.class, c.getId());

        if (aux == null) {
            return false;
        } else {

            beginTransaction();

            aux = entityManager.merge(aux);
            entityManager.remove(aux);

            commitTransaction();
        }
        return true;
    }

    @Produces
    public List<Contor> getContorValues() {
        return entityManager.createQuery("SELECT c from Contor c order by c.time").getResultList();
    }

    @Produces
    public String printJSON() {
        List<Contor> values = getContorValues();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(values);
    }

    @Produces
    public List<Contor> getValuesBetweenDates(Contor start, Contor end) {
        return entityManager.createQuery("SELECT c from Contor c WHERE c.time BETWEEN :start AND :end")
                .setParameter("start", start.getTime(), TemporalType.TIMESTAMP)
                .setParameter("end", end.getTime(), TemporalType.TIMESTAMP).getResultList();
    }

    @Produces
    public List<Contor> getMonthlyUsage(Contor month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(month.getTime());

        return entityManager.createQuery("SELECT c from Contor c WHERE YEAR(c.time) = :yearDate and MONTH(c.time) = :monthDate")
                .setParameter("yearDate", cal.get(Calendar.YEAR))
                .setParameter("monthDate", cal.get(Calendar.MONTH) + 1).getResultList();
    }

    @Produces
    public String printDateJSON(Contor start, Contor end) {
        List<Contor> values = getValuesBetweenDates(start, end);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(values);
    }

    @Produces
    public List<ContorTuples> getMonthMaximum(Contor month) {
        List<Contor> values = getMonthlyUsage(month);

        return m.getMonthMaximumUtil(values);
    }

    @Produces
    public List<ContorTuples> getUsagePerMonth(Contor currentMonth, Contor previousMonth) {
        List<ContorTuples> t1 = getMonthMaximum(currentMonth);
        List<ContorTuples> t2 = getMonthMaximum(previousMonth);

        if (t1.get(0).getWarm() == 0 || t1.get(0).getCold() == 0 || t1.get(1).getWarm() == 0 || t1.get(1).getCold() == 0) {
            List<ContorTuples> nulls = new ArrayList<ContorTuples>();
            nulls.add(new ContorTuples("baie", 0, 0));
            nulls.add(new ContorTuples("wc", 0, 0));
            return nulls;
        }

        if (t2.get(0).getWarm() == 0 || t2.get(0).getCold() == 0 || t2.get(1).getWarm() == 0 || t2.get(1).getCold() == 0) {
            List<ContorTuples> avg = getGlobalAverage(currentMonth);

            return avg;
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
