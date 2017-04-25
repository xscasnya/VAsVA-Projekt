import model.Room;
import model.User;
import user.UserPersistentBeanRemote;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/pages/*")
public class LoginFilter implements Filter {

    private String contextPath;

    @EJB
    UserPersistentBeanRemote remote;

    @Override
    public void init(FilterConfig fc) throws ServletException {
        contextPath = fc.getServletContext().getContextPath();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getSession().getAttribute("user") == null) { //checks if there's a LOGIN_USER set in session...
            res.sendRedirect(contextPath + "/LoginServlet"); //or page where you want to redirect
        }
        else {

            if (req.getSession().getAttribute("rooms") == null) {
                List<Room> rooms = remote.getUserRooms(((User)req.getSession().getAttribute("user")).getId());
                req.getSession().setAttribute("rooms", rooms);
            }

        }
        fc.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}