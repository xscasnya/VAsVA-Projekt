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
}
