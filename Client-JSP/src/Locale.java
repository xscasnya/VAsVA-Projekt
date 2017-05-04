import com.sun.javaws.Globals;
import jdk.nashorn.internal.objects.Global;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

/**
 * Created by Dominik on 4.5.2017.
 */

@WebServlet("/content/locale")
public class Locale extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("language", req.getParameter("ln"));
        Config.set( req.getSession(), Config.FMT_LOCALE, new java.util.Locale(req.getParameter("ln"),req.getParameter("ln").toUpperCase()) );
        req.getRequestDispatcher("/content/joinRoom.jsp").forward(req, resp);
    }
}
