<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Movie Watchlist</title>
    <c:import url="pages/styles.jsp"></c:import>
  </head>
  <body>


  <div class="col-md-4"></div>
  <div class="col-md-4" style="margin-top: 10%;">
    <h1 class="center">Movie Watchlist</h1>
    <form action="LoginServlet" method="post">
      <div class="form-group">
        <label for="nickname">Nickname:</label>
        <input type="text" name="nickname" class="form-control" id="nickname" value="admin">
      </div>
      <div class="form-group">
        <label for="pwd">Password:</label>
        <input type="password" name="pwd" class="form-control" id="pwd" value="admin">
      </div>
      <button type="submit" class="btn btn-default">Submit</button>
    </form>
  <c:if test="${messages != null}">
    <p class="error-page">Bad username or password.</p>
  </c:if>
  </div>
  <div class="col-md-4"></div>
  </body>

<c:import url="pages/dependancies.jsp"></c:import>
</html>