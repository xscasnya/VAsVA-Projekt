<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="itemsPerPage" value="10"/>


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
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <header class="main-header">
        <c:import url="../pages/logo.jsp"/>
        <c:import url="../pages/navbar.jsp"/>
    </header>
    <c:import url="../pages/sidebar.jsp" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                <fmt:message key="dashboard.title"/>
                <small><fmt:message key="dashboard.titleSmall"/></small>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <!-- Your Page Content Here -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title"><fmt:message key="dashboard.joinedRooms"/></h3>
                </div>
                <div class="box-body">
                    <table class="table table-bordered">
                        <tbody>
                            <%-- HEADER --%>
                            <tr>
                                <th><fmt:message key="dashboard.tableColumnName"/></th>
                                <th style="width: 120px;"><fmt:message key="dashboard.tableColumnUsers"/></th>
                                <th style="width: 100px;"><fmt:message key="dashboard.tableColumnMovies"/></th>
                                <th style="width: 400px;"><fmt:message key="dashboard.tableColumnSeen"/></th>
                                <th style="width: 100px;"><fmt:message key="dashboard.tableColumnRole"/></th>
                            </tr>
                            <%-- BODY --%>
                            <c:forEach items="${rooms}" var="r">
                                <tr>
                                    <td><a href="${path}/content/rooms?id=${r.id}"><c:out value="${r.name}"/></a></td>
                                    <td><c:out value="${remote.getUsersCount(r.id)}"/></td>
                                    <td><c:out value="${remote.getFilmsCount(r.id)}"/></td>
                                    <td>
                                        <div class="progress progress-xs progress-striped active">
                                            <div class="progress-bar progress-bar-primary" style="width: 75%"></div>
                                        </div>
                                    </td>
                                    <%-- TODO mozno zmenit na nacitavanie z DB --%>
                                    <td><span class="label ${r.created_by == user.id ? 'label-success' : 'label-primary'}">${r.created_by == user.id ? 'Owner' : 'Guest'}</span></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="box-footer clearfix">
                    <ul class="pagination pagination-sm no-margin pull-right">
                        <li><a href="#">«</a></li>
                        <c:forEach begin="1" end="5" var="i">
                            <li><a href="#"> <c:out value="${i}"/></a></li>
                        </c:forEach>
                        <li><a href="#">»</a></li>
                    </ul>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <c:import url="../pages/footer.jsp"/>
</div>
</body>

<c:import url="../pages/dependancies.jsp"></c:import>
</html>
