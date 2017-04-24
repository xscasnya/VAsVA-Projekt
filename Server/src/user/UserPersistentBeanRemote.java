package user;

import model.User;

import javax.ejb.Remote;

/**
 * Remote rozhranie user.UserPersistentBean-y pre pristup z klienta/klientov
 * @author Jaroslav Jakubik
 */
@Remote
public interface UserPersistentBeanRemote {

	public String testMe(String input);
	public User getAuthentication(String nickname, String password);
	
}
