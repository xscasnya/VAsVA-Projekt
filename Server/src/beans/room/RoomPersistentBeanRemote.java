package beans.room;

import model.Room;
import model.RoomType;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by Dominik on 25.4.2017.
 */
@Remote
public interface RoomPersistentBeanRemote {

    public List<Room> getUserRooms(int id);
    public List<RoomType> getRoomTypes();
    public boolean createRoom(Room room);
    public Room getRoom(int id);
    public boolean insertUserToRoom(int userID, int roomID);
    public boolean isUserInRoom(int userID, int roomID);

}
