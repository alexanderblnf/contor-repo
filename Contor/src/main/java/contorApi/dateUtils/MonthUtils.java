package contorApi.dateUtils;

import contorApi.domUtils.ContorTuples;
import contorApi.entities.Contor;

import javax.ejb.Stateless;
import java.util.*;

/**
 * Created by C311939 on 04.08.2016.
 */
@Stateless
public class MonthUtils {

    public MonthUtils() {
    }

    public Date getPreviousMonth(Date currentMonth) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(currentMonth);

        Date previousMonth;
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        if(month == 1){
            previousMonth = new GregorianCalendar(year - 1, 12, 1).getTime();
        }
        else {
            previousMonth = new GregorianCalendar(year, month - 1, 1).getTime();
        }

        return previousMonth;
    }

    public List<ContorTuples> getMonthMaximumUtil(List<Contor> values) {
        if(values.size() == 0) {
            List<ContorTuples> nulls = new ArrayList<ContorTuples>();
            nulls.add(new ContorTuples("baie", 0, 0));
            nulls.add(new ContorTuples("wc", 0, 0));
            return nulls;
        }

        List<ContorTuples> tuples = new ArrayList<ContorTuples>();
        Contor maxBath = new Contor("baie", 0, 0);
        Contor maxWc = new Contor("wc", 0, 0);

        for(Contor c : values) {
            if(c.getRoom().contains("baie")) {
                if(maxBath.getWarm() <= c.getWarm() && maxBath.getCold() <= c.getCold()) {
                    maxBath = c;
                }
            } else {
                if(maxWc.getWarm() <= c.getWarm() && maxWc.getCold() <= c.getCold()) {
                    maxWc = c;
                }
            }
        }

        tuples.add(new ContorTuples(maxBath.getRoom(), maxBath.getCold(), maxBath.getWarm()));
        tuples.add(new ContorTuples(maxWc.getRoom(), maxWc.getCold(), maxWc.getWarm()));

        return tuples;
    }
}
