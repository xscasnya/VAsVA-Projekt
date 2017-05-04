import beans.movie.MovieApiBeanRemote;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author : Andrej Ščasný
 * Date : 03.05.2017
 */
@WebServlet("/content/addmovie")
public class AddMovie extends HttpServlet {
    @EJB
    MovieApiBeanRemote remote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roomID = Integer.parseInt(req.getParameter("id"));
        req.getRequestDispatcher("/content/addMovie.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("MovieName");
        int year = 0;
        if(!req.getParameter("Year").equals("")){
            year = Integer.parseInt(req.getParameter("Year"));
        }
        String type = req.getParameter("MovieType");

        remote.searchMovie(title,year,type);


        resp.sendRedirect(req.getRequestURI()+"?id="+req.getParameter("id"));
    }
}
