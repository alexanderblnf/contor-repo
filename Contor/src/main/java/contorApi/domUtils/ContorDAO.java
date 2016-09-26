package contorApi.domUtils;

import contorApi.dateUtils.MonthUtils;
import contorApi.entities.Contor;
import contorApi.entities.Users;
import contorApi.security.SessionStore;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by C311939 on 08.09.2016.
 */
@Stateless
public class ContorDAO {

    @ContorDB
    @Inject
    EntityManager entityManager;

    @Inject
    MonthUtils m;

    @Inject
    UserDAO userDAO;

    @Inject
    SessionStore sessionStore;

    private boolean verifyValidAdd(Contor c) {

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

    public List<ContorTuples> getMonthMaximum(Contor month) {
        List<Contor> values = getMonthlyUsageForUser(month);

        return m.getMonthMaximumUtil(values);
    }

    public int add(Contor c) {
        Contor aux = entityManager.find(Contor.class, c.getId());

        if (aux != null) {
            return 0;
        } else {
            if (verifyValidAdd(c) == false) {
                return 2;
            } else {
                entityManager.persist(c);
                return 1;
            }

        }
    }

    public boolean remove(Contor c) {
        Contor aux = entityManager.find(Contor.class, c.getId());

        if(aux != null) {
            entityManager.remove(c);
            return true;
        } else {
            return false;
        }
    }

    public List<Contor> getContorValues() {
        return entityManager.createNamedQuery("Contor.findAllOrderByTime", Contor.class).getResultList();
    }

    public List<Contor> getContorValuesforUser() {
        Users user = userDAO.getByUsername(sessionStore.getUsername());
        return entityManager.createNamedQuery("Contor.findAllforUser")
                .setParameter("user", user).getResultList();
    }

    public List<Contor> getMonthlyUsage(Contor month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(month.getTime());

        return entityManager.createNamedQuery("Contor.getUsage")
                .setParameter("yearDate", cal.get(Calendar.YEAR))
                .setParameter("monthDate", cal.get(Calendar.MONTH) + 1).getResultList();
    }

    public List<Contor> getMonthlyUsageForUser(Contor month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(month.getTime());

        Users user = userDAO.getByUsername(sessionStore.getUsername());

        return entityManager.createNamedQuery("Contor.getUsageForUser")
                .setParameter("yearDate", cal.get(Calendar.YEAR))
                .setParameter("monthDate", cal.get(Calendar.MONTH) + 1)
                .setParameter("user", user).getResultList();
    }

    public List<Contor> getValuesBetweenDates(Contor start, Contor end) {
        return entityManager.createNamedQuery("Contor.getValuesBetweenDates")
                .setParameter("start", start.getTime(), TemporalType.TIMESTAMP)
                .setParameter("end", end.getTime(), TemporalType.TIMESTAMP).getResultList();
    }

    public List<Contor> getValuesBetweenDatesForUser(Contor start, Contor end) {
        Users user = userDAO.getByUsername(sessionStore.getUsername());

        return entityManager.createNamedQuery("Contor.getValuesBetweenDatesForUser")
                .setParameter("start", start.getTime(), TemporalType.TIMESTAMP)
                .setParameter("end", end.getTime(), TemporalType.TIMESTAMP)
                .setParameter("user", user).getResultList();
    }


}
