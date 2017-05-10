import beans.room.RoomPersistentBeanRemote;
import model.Room;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Dominik on 10.5.2017.
 */
@WebServlet("/content/imageServlet")
public class ImageServlet extends HttpServlet{
    @EJB
    RoomPersistentBeanRemote remote;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        byte[] arr = (byte[]) remote.getQrCode(Integer.parseInt(id)).getData();
        OutputStream str = resp.getOutputStream();
        resp.setContentType("image/gif");

        str.write(arr);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
