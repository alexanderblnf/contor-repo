package contorApi.domUtils;

import contorApi.entities.Users;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by C311939 on 05.09.2016.
 */
@Stateless
public class UserDAO {

    @ContorDB
    @Inject
    EntityManager entityManager;

    public Users getById(int id) {
        try {
            return entityManager.createNamedQuery("User.findById", Users.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Users getByUsername(String username) {
        try {
            return entityManager.createNamedQuery("User.findByUsername", Users.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Users> getAllUsers() {
        try {
            return entityManager.createNamedQuery("User.findAll", Users.class).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void add(Users user) {
        entityManager.persist(user);
    }

    public void remove(Users user) {
        entityManager.remove(user);
    }
}
