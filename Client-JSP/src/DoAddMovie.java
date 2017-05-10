import beans.movie.MovieApiBeanRemote;
import beans.room.RoomPersistentBean;
import beans.room.RoomPersistentBeanRemote;
import model.Response;
import model.User;
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
 * Date : 08.05.2017
 */


@WebServlet("/content/doAddMovie")
public class DoAddMovie extends HttpServlet {

    @EJB
    private MovieApiBeanRemote remote;

    @EJB
    private RoomPersistentBeanRemote roomRemote;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomID = req.getParameter("roomID");
        String imdbID = req.getParameter("mID");

        System.out.println(imdbID);
        System.out.println(roomID);

        Response res = remote.getMovie(req.getParameter("mID"));
        if (res.getCode() == Response.success) {
            ApiMovie movie = (ApiMovie) res.getData();
            Response addResp = roomRemote.addMovie(movie, Integer.parseInt(roomID), ((User) req.getSession().getAttribute("user")).getId());
            req.setAttribute("addResp", addResp);
        }


        resp.sendRedirect(req.getContextPath() + "/content/rooms" + "?id=" + req.getParameter("roomID"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPost(req, resp);
    }

}
