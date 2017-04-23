package test;

import javax.ejb.Remote;

/**
 * Remote rozhranie test.TestBean-y pre pristup z klienta/klientov
 * @author Jaroslav Jakubik
 */
@Remote
public interface TestBeanRemote {

	public String testMe(String input);
	public String getUsers();
	
}
