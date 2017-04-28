import beans.room.RoomPersistentBeanRemote;
import model.Room;
import model.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Dominik on 27.4.2017.
 */


@WebServlet("/content/joinRoom")
public class JoinRoom extends HttpServlet{

    @EJB
    RoomPersistentBeanRemote remote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/content/joinRoom.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomID = req.getParameter("RoomID");
        String roomPassword = req.getParameter("RoomPassword");

        if(roomID.equals("")){
            req.setAttribute("valid", false);
            req.getRequestDispatcher("/content/joinRoom.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("valid", true);
        req.setAttribute("ejbError",false);
        req.setAttribute("badPassword", false);
        req.setAttribute("alreadyJoined",false);

        Room room = remote.getRoom(Integer.parseInt(roomID));
        int userID = ((User)req.getSession().getAttribute("user")).getId();

        if(room == null) {
            req.setAttribute("ejbError",true);
            req.getRequestDispatcher("/content/joinRoom.jsp").forward(req, resp);
            return;
        }

        // kontrola ci user uz nie je v danej roomke
        if(remote.isUserInRoom(userID, room.getId())){
            req.setAttribute("alreadyJoined", true);
            req.getRequestDispatcher("/content/joinRoom.jsp").forward(req, resp);
            return;
        }


        if (!room.getPassword().equals(roomPassword) && !room.getPassword().equals("")){
            req.setAttribute("badPassword", true);
            req.getRequestDispatcher("/content/joinRoom.jsp").forward(req, resp);
            return;
        }

        if(remote.insertUserToRoom(userID, room.getId())){
            req.setAttribute("ejbError",false);
            req.getSession().setAttribute("rooms",remote.getUserRooms(userID));
        }
        else {
            req.setAttribute("ejbError",true);
            req.getRequestDispatcher("/content/joinRoom.jsp").forward(req, resp);
            return;
        }


        req.getRequestDispatcher("/content/joinRoom.jsp").forward(req, resp);

    }
}
