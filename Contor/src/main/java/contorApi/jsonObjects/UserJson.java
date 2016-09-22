package contorApi.jsonObjects;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * Created by C311939 on 21.09.2016.
 */
public class UserJson implements Serializable{
    private String username;
    private String password;

    public UserJson() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
