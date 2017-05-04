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
                ${actualRoom.name}
                <small>#${actualRoom.id}</small>
            </h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">Movies</h3>
                            <a href="${path}/content/addmovie?id=${param['id']}" class="pull-right btn btn-app">
                                <i class="fa fa-plus"></i> Add new
                            </a>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div id="example1_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">

                                <div class="row">
                                    <div class="col-sm-12">
                                        <table id="example1" class="table table-bordered table-striped dataTable"
                                               role="grid" aria-describedby="example1_info">
                                            <%-- HEADER --%>
                                            <thead>
                                            <tr role="row">
                                                <th class="sorting_asc" tabindex="0" aria-controls="example1"
                                                    rowspan="1"
                                                    colspan="1" aria-sort="ascending"
                                                    aria-label="Rendering engine: activate to sort column descending"
                                                    >Title
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="example1" rowspan="1"
                                                    colspan="1" aria-label="Browser: activate to sort column ascending"
                                                    >Year
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="example1" rowspan="1"
                                                    colspan="1"
                                                    aria-label="CSS grade: activate to sort column ascending"
                                                    >Director
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="example1" rowspan="1"
                                                    colspan="1"
                                                    aria-label="Platform(s): activate to sort column ascending"
                                                    style="width: 100px;">Length
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="example1" rowspan="1"
                                                    colspan="1"
                                                    aria-label="Engine version: activate to sort column ascending"
                                                    style="width: 150px;">Genre
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="example1" rowspan="1"
                                                    colspan="1"
                                                    aria-label="CSS grade: activate to sort column ascending"
                                                    style="width: 100px;">IMDB Rating
                                                </th>
                                                <th class="sorting" tabindex="0" aria-controls="example1" rowspan="1"
                                                         colspan="1"
                                                         aria-label="CSS grade: activate to sort column ascending"
                                                    style="width: 300px;">Seen by
                                            </th>
                                            </tr>
                                            </thead>
                                            <%-- BODY --%>
                                            <%-- TODO dorobit ofarbovanie labelov --%>
                                            <tbody>
                                            <tr role="row" class="odd">
                                                <td class="sorting_1">Batman</td>
                                                <td>1999</td>
                                                <td>Scasny</td>
                                                <td>115 min</td>
                                                <td>Drama</td>
                                                <td><span class="badge bg-red">75%</span></td>
                                            </tr>
                                            <tr role="row" class="even">
                                                <td class="sorting_1">Superman</td>
                                                <td>2005</td>
                                                <td>Scasny</td>
                                                <td>118 min</td>
                                                <td>Drama</td>
                                                <td><span class="badge bg-red">55%</span></td>
                                            </tr>

                                            </tbody>
                                            <%-- FOOT --%>
                                            <tfoot>
                                            <tr>
                                                <th rowspan="1" colspan="1">Title</th>
                                                <th rowspan="1" colspan="1">Year</th>
                                                <th rowspan="1" colspan="1">Director</th>
                                                <th rowspan="1" colspan="1">Length</th>
                                                <th rowspan="1" colspan="1">Genre</th>
                                                <th rowspan="1" colspan="1">IMDB rating</th>
                                                <th rowspan="1" colspan="1">Seen by</th>
                                            </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                </div>

                            </div>
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
