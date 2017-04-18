package test;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



/**
 * Jednoducha trieda pre otestovanie volania server side biznis logiky v podobe EJB stateless session beany
 * @author Jaroslav Jakubik
 */
public class Test {

	public static void main(String[] args) throws Exception {
		Context context = createRemoteEjbContext("localhost", "8180");
		TestBeanRemote remote = (TestBeanRemote)context.lookup("ejb:/Server_ejb exploded//TestBean!test.TestBeanRemote");
		System.out.println(remote.testMe("Hello"));
	}

	/**
	 * Vytvorenie kontextu pre spojenie sa s JBoss aplikacnym serverom
	 * @param host
	 * @param port
	 * @return
	 * @throws NamingException
	 */
	private static Context createRemoteEjbContext(String host, String port) throws NamingException {
		Hashtable<Object, Object> props = new Hashtable<Object, Object>();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		
		props.put("jboss.naming.client.ejb.context", false);
		props.put("org.jboss.ejb.client.scoped.context", true);
 
		props.put("endpoint.name", "client-endpoint");
		props.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", false);
		props.put("remote.connections", "default");
		props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", false);
 
        props.put(Context.PROVIDER_URL, "http-remoting://" + host + ":" + port);
        props.put("remote.connection.default.host", host);
        props.put("remote.connection.default.port", port);
		props.put("remote.connection.default.username", "vava");
		props.put("remote.connection.default.password", "vava");


		return new InitialContext(props);
    }
}
