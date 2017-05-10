import beans.room.RoomPersistentBeanRemote;
import beans.user.UserPersistentBeanRemote;
import model.*;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Servlet ktorý sa stará o zobrazenie miestností
 * @author Andrej Ščasný, Dominik Števlík
 */
@WebServlet("/content/rooms")
public class Rooms extends HttpServlet {

    @EJB
    RoomPersistentBeanRemote remoteRoom;

    @EJB
    UserPersistentBeanRemote remoteUser;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roomID = Integer.parseInt(req.getParameter("id"));
        Response response = remoteRoom.getRoom(roomID);
        Room actualRoom = null;
        if(response.getCode() == Response.error)
        {
            // TODO osetrit a vypisat error
        }
        else
        {
            actualRoom = (Room)response.getData();
        }


        response = remoteUser.getUsersInRoom(roomID);


        if(response.getCode() == Response.error)
        {
            // TODO osetrit a vypisat error
        }
        else
        {
            List<UserDetails> users = (List<UserDetails>)response.getData();
            req.setAttribute("usersInRoom", users);
        }

        // nacitanie movies
        Response movieResponse = remoteRoom.getMovies(roomID);

        System.out.println("Rooma : " + roomID);
        if(movieResponse.getCode() == Response.success) {
            List<Movie> movies = (List<Movie>) (movieResponse.getData());
            req.setAttribute("movies",movies);
        }



        req.setAttribute("actualRoom", actualRoom);
        req.getRequestDispatcher("/content/rooms.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
