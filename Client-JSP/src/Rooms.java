import beans.room.RoomPersistentBean;
import beans.room.RoomPersistentBeanRemote;
import beans.user.UserPersistentBean;
import beans.user.UserPersistentBeanRemote;
import model.Response;
import model.Room;
import model.UserDetails;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/content/rooms")
public class Rooms extends HttpServlet {

    @EJB
    RoomPersistentBeanRemote remoteRoom;

    @EJB
    UserPersistentBeanRemote remoteUser;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roomID = Integer.parseInt(req.getParameter("id"));
        Room actualRoom = remoteRoom.getRoom(roomID);
        System.out.println("descr " + actualRoom.getDescription());
        Response response = remoteUser.getUsersInRoom(roomID);

        if(response.getCode() == Response.error)
        {
            // TODO osetrit a vypisat error
        }
        else
        {
            List<UserDetails> users = (List<UserDetails>)response.getData();
            req.setAttribute("usersInRoom", users);
        }

        req.setAttribute("actualRoom", actualRoom);
        req.getRequestDispatcher("/content/rooms.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
