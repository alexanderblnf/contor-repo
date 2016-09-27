package contorApi.restservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import contorApi.converter.UserConverter;
import contorApi.domUtils.UserDAO;
import contorApi.entities.Users;
import contorApi.jsonObjects.UserJson;
import contorApi.security.SessionStore;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by C311939 on 05.09.2016.
 */
@Stateless
public class UserServiceREST {

    @Inject
    UserDAO userDAO;

    @Inject
    UserConverter userConverter;

    @Inject
    SessionStore sessionStore;

    public String getUsers() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(userDAO.getAllUsers());
    }

    public boolean addUser(UserJson userJson) {

        if(userJson == null || userJson.getFirstName() == null || userJson.getLastName() == null || userJson.getPassword() == null || userJson.getUsername() == null) {
            return false;
        }

        Users user = userConverter.fromJson(userJson);
        Users aux = userDAO.getByUsername(user.getUsername());

        if(aux == null) {
            userDAO.add(user);
            return true;
        } else {
            return false;
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

    public boolean login(UserJson userJson) {
        Users user = userConverter.fromJson(userJson);
        Users aux = userDAO.getByUsername(user.getUsername());

        if(aux == null || ! aux.getPassword().equals(user.getPassword())) {
            return false;
        } else {
            sessionStore.setUsername(user.getUsername());
            return true;
        }
    }

    public boolean logout() {
        if(sessionStore != null) {
            sessionStore.setUsername(null);
            return true;
        }

        return false;
    }

    public UserJson verifyIfLoggedIn() {
        if(sessionStore == null || sessionStore.getUsername() == null) {
            return null;
        } else {
            Users aux = userDAO.getByUsername(sessionStore.getUsername());
            return userConverter.toJson(aux);
        }
    }



}
