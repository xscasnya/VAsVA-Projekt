<%@ page import="test.TestBeanRemote" %>
<%@ page import="java.util.Properties" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%
    TestBeanRemote remoteInterface;

    try {
        Properties properties = new Properties();
        properties.put("jboss.naming.client.ejb.context", true);
        properties.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");
        Context ctx=new InitialContext(properties);
        remoteInterface = (TestBeanRemote) ctx.lookup("java:module/TestBean!test.TestBeanRemote");

        session.setAttribute("remoteInterface", remoteInterface); //this is to be able to use loginAction on EL Expressions!
    } catch (Exception e) {
        e.printStackTrace();
    }
%>