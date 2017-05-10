package beans.room;

import model.Response;
import model.Room;
import model.RoomType;
import model.api.ApiMovie;

import javax.ejb.Remote;
import java.sql.Connection;
import java.util.List;

/**
 * Remote interface pre RoomPersistentBeanu
 * @author Andrej Ščasný, Dominik Števlík
 */
@Remote
public interface RoomPersistentBeanRemote {

    public Response getUserRooms(int id);
    public Response getRoomTypes();
    public boolean createRoom(Room room);
    public Response getRoom(int id);
    public boolean insertUserToRoom(int userID, int roomID);
    public boolean isUserInRoom(int userID, int roomID);
    public int getRoomsCount(int userID);
    public Response addMovie (ApiMovie movie, int roomID, int userID);
    public Response getMovies(int roomID);
    public boolean createQrCode(int roomID, Connection conn);
    public Response getQrCode(int roomID);
    // mozno do inej beany
    public int getUsersCount(int roomID);
    public int getFilmsCount(int roomID);
}
