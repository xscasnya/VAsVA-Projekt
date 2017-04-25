package beans.user;

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

	public User getAuthentication(String nickname, String password);

}
