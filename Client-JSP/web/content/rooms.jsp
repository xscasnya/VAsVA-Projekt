<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link rel="stylesheet" href="${path}/styles/datatables/dataTables.bootstrap.css">
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
        <section class="content-header">
            <h1>
                ${actualRoom.name}
                <small>#${actualRoom.id}</small>
            </h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary box-solid collapsed-box">
                        <div class="box-header with-border">
                            <h3 class="box-title"><fmt:message key="room.description"/></h3>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                        class="fa fa-plus"></i>
                                </button>
                            </div>
                            <!-- /.box-tools -->
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body" style="display: none;">

                            <div class="col-md-10">
                                ${actualRoom.description}
                            </div>
                            <div class="col-md-2">
                                <img src="imageServlet?id=${actualRoom.id}" class="pull-right"/>
                            </div>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary box-solid collapsed-box">
                        <div class="box-header with-border">
                            <h3 class="box-title"><fmt:message key="room.users"/></h3>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                        class="fa fa-plus"></i>
                                </button>
                            </div>
                            <!-- /.box-tools -->
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body" style="display: none;">
                            <div class="box-body table-responsive no-padding">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th><fmt:message key="room.tableColumnName"/></th>
                                        <th><fmt:message key="room.tableColumnJoinedAt"/></th>
                                        <th><fmt:message key="room.tableColumnMoviesCount"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${usersInRoom}" var="u">
                                        <tr role="row" class="odd">
                                            <td>${u.nickname}</td>
                                            <td>${u.joined_at}</td>
                                            <td>${u.addedMoviesCount}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <a href="${path}/content/addmovie?id=${param['id']}" class="btn btn-app"
                               style="position: absolute; right: 15px;">
                                <i class="fa fa-plus"></i> <fmt:message key="room.addNewBtn"/>
                            </a>
                            <h3 class="box-title" style="display: block; margin-top: 40px;"><fmt:message
                                    key="room.formTitle"/></h3>
                        </div>
                        <div class="box-body table-responsive no-padding">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th style="width: 380px;"><fmt:message key="room.tableColumnTitle"/></th>
                                    <th style="width: 100px;"><fmt:message key="room.tableColumnYear"/></th>
                                    <th style="width: 150px;"><fmt:message key="room.tableColumnDirector"/></th>
                                    <th style="width: 100px;"><fmt:message key="room.tableColumnLength"/></th>
                                    <th style="width: 200px;"><fmt:message key="room.tableColumnGenre"/></th>
                                    <th style="width: 150px;"><fmt:message key="room.talbeColumnImdbRating"/></th>
                                    <th><fmt:message key="room.tableColumnOptions"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="movie" items="${movies}">
                                    <c:if test="${!movie.imdbRating.equals('N/A')}">
                                        <fmt:parseNumber var="rating" integerOnly="true" type="number"
                                                         value="${(movie.imdbRating/10)*100}"/>
                                    </c:if>
                                    <c:if test="${movie.imdbRating.equals('N/A')}">
                                        <c:set var="rating" value="N/A"/>
                                    </c:if>

                                    <tr role="row" class="odd">
                                        <td><c:out value="${movie.title}"/></td>
                                        <td><c:out value="${movie.year}"/></td>
                                        <td><c:out value="${movie.director}"/></td>
                                        <td><c:out value="${movie.length}"/></td>
                                        <td><c:out value="${movie.genre}"/></td>
                                        <td><c:if test="${rating.equals('N/A')}">
                                                <span class="badge bg-red">
                                                        <c:out value="N/A"/>
                                                </span>
                                            </c:if>
                                            <c:if test="${!rating.equals('N/A')}" >
                                                <c:if test="${rating > 0 && rating < 40}">
                                                    <span class="badge bg-red">
                                                    <c:out value="${rating}"/>%
                                                    </span>
                                                </c:if>
                                                <c:if test="${rating >= 40 && rating < 70}">
                                                    <span class="badge bg-yellow">
                                                    <c:out value="${rating}"/>%
                                                    </span>
                                                </c:if>
                                                <c:if test="${rating >= 70}">
                                                    <span class="badge bg-green">
                                                    <c:out value="${rating}"/>%
                                                    </span>
                                                </c:if>
                                            </c:if>
                                        </td>

                                        <td>
                                            <a href="${path}/content/movieDetail?mID=${movie.imdbid}">
                                                <button class="btn btn-default">
                                                    <i class="fa fa-eye" aria-hidden="true"></i>
                                                </button>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- /.box-body -->
            </div>
        </section>
    </div>
    <c:import url="../pages/footer.jsp"/>
</div>


<c:import url="../pages/dependancies.jsp"/>
</body>
</html>
