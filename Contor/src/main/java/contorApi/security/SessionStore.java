package contorApi.security;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by C311939 on 22.09.2016.
 */
@SessionScoped
public class SessionStore implements Serializable {
    public static AtomicLong instances = new AtomicLong(0);
    private String username;

    @PostConstruct
    public void onNewSession() {
        instances.incrementAndGet();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @PreDestroy
    public void onSessionDestruction() {
        instances.decrementAndGet();
    }
}
