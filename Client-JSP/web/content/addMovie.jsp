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
                Add new movie
                <small>room <c:out value="${param['id']}"/></small>
            </h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box box-primary">
                        <div class="box-header">
                            <h3 class="box-title">Find movie</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <form role="form" method="post">
                                <div class="box-body">
                                    <div class="form-group col-xs-4">
                                        <label for="MovieName">Movie name</label>
                                        <input type="MovieName" class="form-control" id="MovieName" name="MovieName"
                                               placeholder="MovieName">
                                    </div>
                                    <div class="form-group col-xs-4">
                                        <label for="Year">Year</label>
                                        <input type="text" class="form-control" id="Year" name="Year"
                                               placeholder="Year">
                                    </div>

                                    <div class="form-group col-xs-4">
                                        <label>Type </label>
                                        <select class="form-control" name="MovieType">
                                            <option value="" selected></option>
                                            <option value="movie">Movie</option>
                                            <option value="series">Series</option>
                                            <option value="episode">Episode</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button type="submit" class="btn btn-primary">Search</button>
                                </div>
                            </form>

                            <c:if test="${list != null}">
                                <div class="box-body table-responsive no-padding">
                                    <table class="table table-hover">
                                        <tbody>
                                        <tr>
                                            <th>Title</th>
                                            <th>Year</th>
                                            <th>Director</th>
                                            <th>Length</th>
                                            <th>Genre</th>
                                            <th>IMDB Rating</th>
                                            <th>Options</th>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>

            </div>
        </section>
    </div>
    <c:import url="../pages/footer.jsp"/>
</div>

<c:import url="../pages/dependancies.jsp"/>
</body>
</html>
