package test;

import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Jednoducha stateless session beana
 */
@Stateless
@Remote(TestBeanRemote.class)
public class TestBean implements TestBeanRemote {

	/**
	 * Biznis logika ktora vrati rozsireny textovy retazec
	 */
    public String testMe(String input) {
    	return "tested " + input;
    }

}
