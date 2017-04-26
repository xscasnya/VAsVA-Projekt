<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Movie Watchlist</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${path}/styles/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${path}/styles/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${path}/styles/dist/css/skins/skin-blue.min.css">
  </head>
  <body>


  <div class="col-md-4"></div>
  <div class="col-md-4" style="margin-top: 10%;">
    <h1 class="center">Movie Watchlist</h1>
    <form action="Login" method="post">
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