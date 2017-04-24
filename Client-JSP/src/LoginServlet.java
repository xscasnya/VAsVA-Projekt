import model.User;
import user.UserPersistentBeanRemote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @EJB
    UserPersistentBeanRemote remote;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = remote.getAuthentication(req.getParameter("nickname"),req.getParameter("pwd"));
        req.getSession().setAttribute("user",user);

        if(user == null) {
            resp.sendRedirect("index.jsp");
        }

        else {
            resp.sendRedirect("dashboard.jsp");
        }

    }
}
