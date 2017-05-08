package beans.user;

import model.Response;
import model.Room;
import model.User;

import javax.ejb.Remote;
import java.util.List;

/**
 * Remote rozhranie beans.user.UserPersistentBean-y pre pristup z klienta/klientov
 * @author Dominik
 */
@Remote
public interface UserPersistentBeanRemote {

	public Response getAuthentication(String nickname, String password);
	public Response getUsersInRoom(int roomID);
}
