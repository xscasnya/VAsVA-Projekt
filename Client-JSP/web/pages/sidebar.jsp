<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- Sidebeansuser panel (optional) -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="../styles/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p><c:out value="${user.nickname}"></c:out></p>
                <!-- Status -->
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>


        <!-- Sidebar Menu -->
        <ul class="sidebar-menu">
            <li class="active"><a href="#"><i class="fa fa-home"></i><span><b>Home</b></span></a></li>
            <li>
                <a href="${pageContext.request.contextPath}/content/createRoom"><i class="fa fa-plus-square"></i>
                    <span>Create room</span></a>
            </li>
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-users"></i> <span>My rooms</span>
                    <span class="pull-right-container">
                            <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" style="display: none;">
                    <c:forEach items="${rooms}" var="r">
                        <li><a href="#"><i class="fa"></i> <c:out value="${r.name}"/> </a></li>
                    </c:forEach>
                </ul>
            </li>

        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>