<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.language}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="localization.localization" scope="session"/>

<%-- TODO registracia, response pre room beanu - bool ?, osetrenie pre polia filmu, logovanie u klienta, user profil, zmenit obsah z kratkeho na dlhy--%>

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
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <b>Movie Watchlist</b>
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg"><fmt:message key="login.signInLabel"/></p>

        <form action="Login" method="post">
            <div class="form-group has-feedback">
                <input type="text" id="nickname" class="form-control" placeholder="<fmt:message key="login.nickname" /> ${language}" name="nickname" value="admin">
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" id="pwd" placeholder="<fmt:message key="login.password" />" name="pwd" value="admin">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <c:if test="${messages != null}">
            <div class="form-group has-feedback">
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <h4><i class="icon fa fa-ban"></i> Error!</h4>
                    <c:out value="${messages.error}"/>
                </div>
            </div>
            </c:if>
            <div class="row">
                <div class="col-xs-4"></div>
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat"><fmt:message key="login.signInBtn" /></button>
                </div>
                <div class="col-xs-4"></div>
            </div>
        </form>


        <a href="register.html" class="text-center"><fmt:message key="login.registerBtn" /></a>

    </div>
    <!-- /.login-box-body -->
</div>

</body>

<c:import url="pages/dependancies.jsp"></c:import>
</html>


