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


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @EJB
    UserPersistentBeanRemote remote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("nickname");
        String password = req.getParameter("pwd");
        Map<String, String> messages = new HashMap<String, String>();


        if (username == null || username.isEmpty()) {
            messages.put("username", "Please enter username");
        }

        if (password == null || password.isEmpty()) {
            messages.put("password", "Please enter password");
        }

        if (messages.isEmpty()) {
            User user = remote.getAuthentication(username,password);

            if (user != null) {
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/content/dashboard.jsp");
                return;
            } else {
                messages.put("login", "Unknown login, please try again");
            }
        }

        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);

    }
}
