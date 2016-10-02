package contorApi.domUtils;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by C311939 on 29.07.2016.
 */
public class DatabaseProducer {

    @Produces
    @PersistenceContext(unitName = "contorUnit")
    @ContorDB
    private EntityManager entityManager1;

}
