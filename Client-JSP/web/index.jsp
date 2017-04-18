<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 18.04.2017
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Filmovy Watchlist</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>
  </head>
  <body>
  <%
    out.println("Your IP address is " + request.getRemoteAddr());
  %>

  <div class="col-md-4"></div>
  <div class="col-md-4">
    <form action="loginForm.jsp">
      <div class="form-group">
        <label for="email">Email address:</label>
        <input type="email" class="form-control" id="email">
      </div>
      <div class="form-group">
        <label for="pwd">Password:</label>
        <input type="password" class="form-control" id="pwd">
      </div>
      <div class="checkbox">
        <label><input type="checkbox"> Remember me</label>
      </div>
      <button type="submit" class="btn btn-default">Submit</button>
    </form>
  </div>
  <div class="col-md-4"></div>

  </body>

<script src="${pageContext.request.contextPath}/styles/bootstrap-3.3.7-dist/js/bootstrap.min.js"/>
<script src="${pageContext.request.contextPath}/styles/bootstrap-3.3.7-dist/js/npm.js"/>
</html>