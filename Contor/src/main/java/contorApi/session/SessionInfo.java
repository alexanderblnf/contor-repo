package contorApi.session;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * Created by C311939 on 05.09.2016.
 */
@SessionScoped
public class SessionInfo implements Serializable {
    private String username;
    private String password;

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
