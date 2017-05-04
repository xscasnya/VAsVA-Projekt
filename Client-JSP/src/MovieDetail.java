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
 * Author : Andrej Ščasný
 * Date : 04.05.2017
 */

@WebServlet("/content/movieDetail")
public class MovieDetail extends HttpServlet {

    @EJB
    MovieApiBeanRemote remote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Response res = remote.getMovie(req.getParameter("mID"));
        if(res.getCode()==Response.success) {
            ApiMovie movie = (ApiMovie) remote.getMovie(req.getParameter("mID")).getData();
            req.setAttribute("movie",movie);
        }

        req.getRequestDispatcher("/content/movieDetail.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
