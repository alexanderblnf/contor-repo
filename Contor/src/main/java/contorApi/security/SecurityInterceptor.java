package contorApi.security;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created by C311939 on 21.09.2016.
 */
@WebFilter("/service/contor/*")
public class SecurityInterceptor implements Filter {

    @Inject
    SessionStore sessionStore;

    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        /*HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            chain.doFilter(request, response); // Logged in, just continue chain.
        }*/

        if (sessionStore == null || sessionStore.getUsername() == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            chain.doFilter(request, response);
        }
    }
    public void destroy() {
        sessionStore.setUsername(null);
    }
}
