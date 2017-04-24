<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Movie Watchlist</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>
  </head>
  <body>


  <div class="col-md-4"></div>
  <div class="col-md-4" style="margin-top: 10%;">
    <h1 class="center">Movie Watchlist</h1>
    <form action="LoginServlet" method="post">
      <div class="form-group">
        <label for="nickname">Nickname:</label>
        <input type="text" name="nickname" class="form-control" id="nickname">
      </div>
      <div class="form-group">
        <label for="pwd">Password:</label>
        <input type="password" name="pwd" class="form-control" id="pwd">
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