package contorApi.entities;

import javax.persistence.*;

/**
 * Created by C311939 on 05.09.2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "User.findById",
                query = "SELECT u FROM Users u WHERE u.id = :id"),
        @NamedQuery(name = "User.findByUsername",
                query = "SELECT u FROM Users u WHERE u.username = :username"),
        @NamedQuery(name= "User.findAll",
                query = "SELECT u FROM Users u")
})
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;
    private String password;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
