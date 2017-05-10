import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Pomocný servlet pre nastavenie lokalizácie
 * @author Andrej Ščasný, Dominik Števlík
 */

@WebServlet("/content/locale")
public class Locale extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("language", req.getParameter("ln"));
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer);
    }
}
