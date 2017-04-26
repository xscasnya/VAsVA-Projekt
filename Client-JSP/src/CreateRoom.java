import beans.room.RoomPersistentBeanRemote;
import model.Room;
import model.RoomType;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/content/createRoom")
public class CreateRoom extends HttpServlet {

    @EJB
    RoomPersistentBeanRemote remote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoomType> list = remote.getRoomTypes();
        req.getSession().setAttribute("roomTypes",list);
        req.getRequestDispatcher("/content/createRoom.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("RoomName");
        String password = req.getParameter("password");
        String description =  req.getParameter("description");
        String roomType = req.getParameter("roomtype");

        if(username.equals("") || password.equals("") || description.equals("") || roomType.equals("")) {
            req.setAttribute("valid",false);
        }
        else {
            req.setAttribute("valid",true);
        }

        req.getRequestDispatcher("/content/createRoom.jsp").forward(req, resp);
    }
}
