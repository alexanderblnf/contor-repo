package contorApi.restservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import contorApi.domUtils.UserDAO;
import contorApi.entities.Users;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by C311939 on 05.09.2016.
 */
@Stateless
public class UserServiceREST {

    @Inject
    UserDAO userDAO;

    public String getUsers() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(userDAO.getAllUsers());
    }

    public Users addUser(String username, String password) {
        Users aux = userDAO.getByUsername(username);

        if(aux == null) {
            Users users = new Users(username, password);
            userDAO.add(users);
            return users;
        } else {
            return null;
        }
    }

    public Users remove(String username) {
        Users aux = userDAO.getByUsername(username);

        if(aux == null) {
            return null;
        } else {
            userDAO.remove(aux);
            return aux;
        }
    }



}
