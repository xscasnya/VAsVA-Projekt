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
                    <div class="box">
                        <div class="box-header">
                            <a href="${path}/content/addmovie?id=${param['id']}" class="btn btn-app">
                                <i class="fa fa-plus"></i> <fmt:message key="room.addNewBtn"/>
                            </a>
                            <h3 class="box-title" style="display: block;"><fmt:message key="room.formTitle"/></h3>
                        </div>
                        <div class="box-body table-responsive no-padding">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th><fmt:message key="room.tableColumnTitle"/></th>
                                        <th><fmt:message key="room.tableColumnYear"/></th>
                                        <th><fmt:message key="room.tableColumnDirector"/></th>
                                        <th><fmt:message key="room.tableColumnLength"/></th>
                                        <th><fmt:message key="room.tableColumnGenre"/></th>
                                        <th><fmt:message key="room.talbeColumnImdbRating"/></th>
                                        <th><fmt:message key="room.tableColumnSeenBy"/></th>
                                    </tr>
                                </thead>
                                <tbody> <%-- TODO Farebny rating --%>
                                    <tr role="row" class="odd">
                                        <td class="sorting_1">Batman</td>
                                        <td>1999</td>
                                        <td>Scasny</td>
                                        <td>115 min</td>
                                        <td>Drama</td>
                                        <td><span class="badge bg-red">75%</span></td>
                                        <td>Nobody</td>
                                    </tr>
                                    <tr role="row" class="even">
                                        <td class="sorting_1">Superman</td>
                                        <td>2005</td>
                                        <td>Scasny</td>
                                        <td>118 min</td>
                                        <td>Drama</td>
                                        <td><span class="badge bg-red">55%</span></td>
                                        <td>Nobody</td>
                                    </tr>
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
