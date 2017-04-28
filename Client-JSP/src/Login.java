import model.Response;
import model.User;
import beans.user.UserPersistentBeanRemote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/Login")
public class Login extends HttpServlet {

    @EJB
    UserPersistentBeanRemote remote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("nickname");
        String password = req.getParameter("pwd");
        Map<String, String> messages = new HashMap<String, String>();

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            messages.put("error", "Please fill all fields");
        }

        if (messages.isEmpty()) {
            Response res = remote.getAuthentication(username,password);
            if (res.getCode() != Response.error) {
                req.getSession().setAttribute("user", (User)res.getData());
                resp.sendRedirect(req.getContextPath() + "/content/dashboard");
                return;
            } else {
                messages.put("error", res.getDescription());
            }
        }

        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
