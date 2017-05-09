<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Movie Watchlist</title>
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
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <!-- Main Header -->
    <header class="main-header">
        <c:import url="../pages/logo.jsp"/>
        <c:import url="../pages/navbar.jsp"/>
    </header>
    <c:import url="../pages/sidebar.jsp"/>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1><fmt:message key="joinRoom.title"/></h1>
        </section>

        <section class="content">
            <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title"><fmt:message key="joinRoom.formTitle"/></h3>
                    </div>

                    <form role="form" method="post">
                        <div class="box-body">
                            <div class="form-group">
                                <label for="RoomID"><fmt:message key="joinRoom.roomNumberLbl"/></label>
                                <input type="RoomID" class="form-control" id="RoomID" name="RoomID"
                                       placeholder="<fmt:message key="joinRoom.roomNumberPh"/>"
                                       <c:if test="${not empty param.id}">
                                            value="${param.id}"
                                        </c:if>>
                            </div>
                            <div class="form-group">
                                <label for="RoomPassword"><fmt:message key="joinRoom.passwordLbl"/></label>
                                <input type="password" class="form-control" id="RoomPassword" name="RoomPassword"
                                       placeholder="<fmt:message key="joinRoom.passwordPh"/>">
                            </div>
                        </div>
                        <div class="box-footer">
                            <button type="submit" class="btn btn-primary"><fmt:message key="joinRoom.joinBtn"/></button>
                        </div>
                    </form>
                </div>

                <%-- if --%>
                <c:if test="${ejbError == false && badPassword == false && alreadyJoined == false}">
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <h4><i class="icon fa fa-check"></i> Success!</h4>
                        Room was successfuly added.
                    </div>
                </c:if>
                <c:if test="${valid == false}">
                    <div class="alert alert-warning alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <h4><i class="icon fa fa-warning"></i> Alert!</h4>
                        Please fill all fields !
                    </div>
                </c:if>
                <c:if test="${ejbError == true}">
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <h4><i class="icon fa fa-ban"></i> Error!</h4>
                        There was an error on server side. Please check loggs or let trained monkeys solve this.
                    </div>
                </c:if>
                <c:if test="${badPassword == true}">
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <h4><i class="icon fa fa-ban"></i> Error!</h4>
                        Password is wrong!
                    </div>
                </c:if>
                <c:if test="${alreadyJoined == true}">
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <h4><i class="icon fa fa-ban"></i> Error!</h4>
                        You are already member of this room.
                    </div>
                </c:if>
            </div>


        </section>
    </div>

    <c:import url="../pages/footer.jsp"/>
</div>
</body>
<c:import url="../pages/dependancies.jsp"></c:import>
</html>
