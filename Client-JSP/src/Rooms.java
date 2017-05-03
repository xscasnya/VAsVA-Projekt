import beans.room.RoomPersistentBean;
import beans.room.RoomPersistentBeanRemote;
import model.Room;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/content/rooms")
public class Rooms extends HttpServlet {

    @EJB
    RoomPersistentBeanRemote remote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roomID = Integer.parseInt(req.getParameter("id"));
        Room actualRoom = remote.getRoom(roomID);
        req.setAttribute("actualRoom", actualRoom);
        req.getRequestDispatcher("/content/rooms.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
