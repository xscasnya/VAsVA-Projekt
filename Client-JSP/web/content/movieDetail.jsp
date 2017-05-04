<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="NotFound" value="N/A"/>


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
                <fmt:message key="movieDetail.title"/>
            </h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${movie != null}">
                        <div class="box box-primary">
                            <div class="box-header">
                                <h3 class="box-title"><c:out value="${movie.title}"/></h3>
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">

                                <div class="col-xs-3">
                                    <img src="${movie.poster}"/>
                                </div>
                                <div class="col-xs-4">
                                    <h3><c:out value="${movie.title}"/></h3><br>
                                    <dl class="dl-vertical">
                                        <dt><fmt:message key="movieDetail.director"/></dt>
                                        <dd><c:out value="${movie.director}"/></dd>
                                        <dt><fmt:message key="movieDetail.actors"/></dt>
                                        <dd><c:out value="${movie.actors}"/></dd>
                                        <dt><fmt:message key="movieDetail.runtime"/></dt>
                                        <dd><c:out value="${movie.runtime}"/></dd>
                                        <dt><fmt:message key="movieDetail.release"/></dt>
                                        <dd><c:out value="${movie.released}"/></dd>
                                        <dt><fmt:message key="movieDetail.genre"/></dt>
                                        <dd><c:out value="${movie.genre}"/></dd>
                                        <dt><fmt:message key="movieDetail.plot"/></dt>
                                        <dd><c:out value="${movie.plot}"/>
                                        </dd>
                                    </dl>

                                </div>
                                <div class="col-xs-4">
                                    <h3><fmt:message key="movieDetail.rating"/></h3>
                                    <dl class="dl-vertical">
                                        <dt>Metascore</dt>
                                        <dd><c:out value="${movie.metascore}"/> </dd>
                                        <dt><fmt:message key="movieDetail.imdbRating"/></dt>
                                        <dd><c:out value="${movie.imdbRating}"/> </dd>
                                        <c:forEach var="rating" items="${movie.ratings}">
                                            <dt><c:out value="${rating.source}"/></dt>
                                            <dd><c:out value="${rating.value}"/></dd>
                                        </c:forEach>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </section>
    </div>
    <c:import url="../pages/footer.jsp"/>
</div>

<c:import url="../pages/dependancies.jsp"/>
</body>
</html>
