package test;

import javax.ejb.Remote;

/**
 * Remote rozhranie test.UserPersistentBean-y pre pristup z klienta/klientov
 * @author Jaroslav Jakubik
 */
@Remote
public interface UserPersistentBeanRemote {

	public String testMe(String input);
	public String getUsers();
	
}
