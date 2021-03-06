import beans.movie.MovieApiBeanRemote;
import model.api.Search;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Author : Andrej Ščasný
 * Date : 03.05.2017
 */

/**
 * Servlet ktorý sa stará o vyhľadanie filmov a následne presmerovanie na samotné pridanie filmov do miestností
 * @author Andrej Ščasný, Dominik Števlík
 */
@WebServlet("/content/addmovie")
public class AddMovie extends HttpServlet {
    @EJB
    MovieApiBeanRemote remote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        List<Search> movies = (List<Search>)remote.searchMovie(title,year,type).getData();
        req.setAttribute("movies",movies);

        req.getRequestDispatcher("/content/addMovie.jsp"+"?id="+req.getParameter("id")).forward(req,resp);
    }
}
