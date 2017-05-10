import beans.movie.MovieApiBeanRemote;
import model.Response;
import model.api.ApiMovie;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet ktorý sa stará o zobrazenie detailov jednotlivých filmov
 * @author Andrej Ščasný, Dominik Števlík
 */

@WebServlet("/content/movieDetail")
public class MovieDetail extends HttpServlet {

    @EJB
    MovieApiBeanRemote remote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.setAttribute("caller",req.getHeader("Referer"));
        Response res = remote.getMovie(req.getParameter("mID"));
        if(res.getCode()==Response.success) {
            ApiMovie movie = (ApiMovie) res.getData();
            req.setAttribute("movie",movie);
        }

        req.getRequestDispatcher("/content/movieDetail.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
