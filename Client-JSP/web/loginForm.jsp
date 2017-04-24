<%@ page import="user.UserPersistentBeanRemote" %>
<%@ page import="java.util.Properties" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%
    UserPersistentBeanRemote remoteInterface;

    try {
        Properties properties = new Properties();
        properties.put("jboss.naming.client.ejb.context", true);
        properties.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");
        Context ctx=new InitialContext(properties);
        remoteInterface = (UserPersistentBeanRemote) ctx.lookup("java:module/UserPersistentBean!user.UserPersistentBeanRemote");

        session.setAttribute("remoteInterface", remoteInterface); //this is to be able to use loginAction on EL Expressions!
    } catch (Exception e) {
        e.printStackTrace();
    }
%>