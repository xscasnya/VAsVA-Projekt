<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="NotFound" value="N/A"/>
<c:set var="success" value="${Response.getSuccess()}"/>


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
                <fmt:message key="addMovie.title"/>
                <small><fmt:message key="addMovie.smallTitle"/> #<c:out value="${param['id']}"/></small>
            </h1>
        </section>
        <section class="content">
            <div class="col-md-12">
                <div class="box box-primary">
                    <div class="box-header">
                        <h3 class="box-title"><fmt:message key="addMovie.formTitle"/></h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <form role="form" method="post">
                            <div class="box-body">
                                <div class="form-group col-xs-4">
                                    <label for="MovieName"><fmt:message key="addMovie.movieNameLbl"/></label>
                                    <input type="MovieName" class="form-control" id="MovieName" name="MovieName"
                                           placeholder="<fmt:message key="addMovie.movieNamePh"/>">
                                </div>
                                <div class="form-group col-xs-4">
                                    <label for="Year"><fmt:message key="addMovie.yearLbl"/></label>
                                    <input type="text" class="form-control" id="Year" name="Year"
                                           placeholder="<fmt:message key="addMovie.yearPh"/>">
                                </div>

                                <div class="form-group col-xs-4">
                                    <label><fmt:message key="addMovie.typeLbl"/> </label>
                                    <select class="form-control" name="MovieType">
                                        <option value="" selected></option>
                                        <option value="movie"><fmt:message key="addMovie.typeMovie"/></option>
                                        <option value="series"><fmt:message key="addMovie.typeSeries"/></option>
                                        <option value="episode"><fmt:message key="addMovie.typeEpisode"/></option>
                                    </select>
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="submit" class="btn btn-primary"><fmt:message
                                        key="addMovie.searchBtn"/></button>
                            </div>
                        </form>

                        <c:if test="${movies != null}">
                            <div class="box-body table-responsive no-padding">
                                <table class="table table-hover">
                                    <tbody>
                                    <tr>
                                        <th><fmt:message key="addMovie.tableColumnPoster"/></th>
                                        <th><fmt:message key="addMovie.tableColumnTitle"/></th>
                                        <th><fmt:message key="addMovie.tableColumnYear"/></th>
                                        <th><fmt:message key="addMovie.tableColumnType"/></th>
                                        <th><fmt:message key="addMovie.tableColumnOptions"/></th>
                                    </tr>
                                    <c:forEach items="${movies}" var="m">
                                        <tr>
                                            <td>
                                                <c:if test="${m.poster != 'N/A'}">
                                                    <img src="${m.poster}" width="50px" height="50px"/>
                                                </c:if>
                                            </td>
                                            <td><c:out value="${m.title}"/></td>
                                            <td><c:out value="${m.year}"/></td>
                                            <td><c:out value="${m.type}"/></td>
                                            <td>
                                                <a href="${path}/content/doAddMovie?roomID=${param["id"]}&mID=${m.imdbID}">
                                                    <button class="btn btn-success">
                                                        <i class="fa fa-plus-square-o" aria-hidden="true"></i>
                                                    </button>
                                                </a>
                                                <a href="${path}/content/movieDetail?mID=${m.imdbID}">
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
                        </c:if>
                    </div>
                </div>
                <c:out value="${success}"/>
                <c:if test="${addResp != null && addResp.code == success}">
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <h4><i class="icon fa fa-check"></i> Success!</h4>
                        Room was successfuly added.
                    </div>
                </c:if>

            </div>
        </section>
    </div>
    <c:import url="../pages/footer.jsp"/>
</div>

<c:import url="../pages/dependancies.jsp"/>
</body>
</html>
