package contorApi.converter;

import contorApi.entities.Users;
import contorApi.jsonObjects.UserJson;

import javax.ejb.Stateless;

/**
 * Created by C311939 on 21.09.2016.
 */
@Stateless
public class UserConverter {
    public Users fromJson(UserJson userJson) {
        Users user = new Users();
        user.setUsername(userJson.getUsername());
        user.setPassword(userJson.getPassword());

        return user;
    }

    public UserJson toJson(Users user) {
        UserJson userJson = new UserJson();
        userJson.setUsername(user.getUsername());
        userJson.setPassword(user.getPassword());

        return userJson;
    }
}
