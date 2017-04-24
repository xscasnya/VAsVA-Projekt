import model.User;
import user.UserPersistentBeanRemote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Admin on 18.04.2017.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @EJB
    UserPersistentBeanRemote remote;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().print(heslo + " " + remote.testMe("your ass"));
        //resp.getWriter().print(remote.getAuthentication(req.getA));
        resp.getWriter().print("Nick: "+ req.getParameter("nickname") + "\n Password: " + req.getParameter("pwd"));

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
