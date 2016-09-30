package contorApi.converter;

import contorApi.entities.Users;
import contorApi.jsonObjects.UserJson;
import javax.ejb.Stateless;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by C311939 on 21.09.2016.
 */
@Stateless
public class UserConverter {
    public Users fromJson(UserJson userJson) {
        Users user = new Users();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] encryptedPassword = null;
        try {
            encryptedPassword = md.digest(userJson.getPassword().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        BigInteger bigInteger = new BigInteger(1, encryptedPassword);
        String password = bigInteger.toString(16);

        user.setUsername(userJson.getUsername());
        user.setPassword(password);

        if(userJson.getLastName() != null) {
            user.setLastName(userJson.getLastName());
        }

        if(userJson.getFirstName() != null) {
            user.setFirstName(userJson.getFirstName());
        }

        return user;
    }

    public UserJson toJson(Users user) {
        UserJson userJson = new UserJson();
        userJson.setUsername(user.getUsername());
        userJson.setPassword(user.getPassword());

        if(user.getFirstName() != null) {
            userJson.setFirstName(user.getFirstName());
        }

        if(user.getLastName() != null) {
            userJson.setLastName(user.getLastName());
        }

        return userJson;
    }
}
