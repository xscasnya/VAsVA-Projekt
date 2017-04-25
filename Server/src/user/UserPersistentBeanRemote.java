package user;

import model.Room;
import model.User;

import javax.ejb.Remote;
import java.util.List;

/**
 * Remote rozhranie user.UserPersistentBean-y pre pristup z klienta/klientov
 * @author Jaroslav Jakubik
 */
@Remote
public interface UserPersistentBeanRemote {

	public String testMe(String input);
	public User getAuthentication(String nickname, String password);
	public List<Room> getUserRooms(int id);

}
