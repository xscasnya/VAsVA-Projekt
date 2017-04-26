<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Movie Watchlist</title>
    <c:import url="../pages/styles.jsp"></c:import>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <header class="main-header">

        <!-- Logo -->
        <a href="index2.html" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>MW</b></span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>Movie Watchlist</b></span>
        </a>

        <c:import url="../pages/navbar.jsp"/>
    </header>


    <c:import url="../pages/sidebar.jsp" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Page Header
                <small>HERE is optional description</small>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <!-- Your Page Content Here -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <c:import url="../pages/footer.jsp"/>
</div>
</body>

<c:import url="../pages/dependancies.jsp"></c:import>
</html>
