<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="dashboard" value="${path}/content/dashboard"/>
<c:set var="createRoom" value="${path}/content/createRoom"/>
<c:set var="joinRoom" value="${path}/content/joinRoom"/>

<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- Sidebeansuser panel (optional) -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${path}/styles/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p><c:out value="${user.nickname}"/></p>
                <!-- Status -->
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>


        <!-- Sidebar Menu -->
        <ul class="sidebar-menu">
            <li class="${requestPath == dashboard ? 'active' : ''}" >
                <a href="${dashboard}"><i class="fa fa-home"></i>
                    <span><b>Home</b></span>
                </a>
            </li>
            <li class="${requestPath == createRoom ? 'active' : ''}">
                <a href="${createRoom}"><i class="fa fa-plus-square"></i>
                    <span>Create room</span>
                </a>
            </li>
            <li class="${requestPath == joinRoom ? 'active' : ''}">
                <a href="${joinRoom}"><i class="fa fa-sign-in"></i>
                    <span>Join room</span></a>
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-users"></i> <span>My rooms</span>
                    <span class="pull-right-container">
                            <i class="fa fa-angle-left pull-right"></i>
                    </span>
                </a>
                <ul class="treeview-menu" style="display: none;">
                    <c:forEach items="${rooms}" var="r">
                        <li><a href="${path}/content/rooms?id=${r.id}"><i class="fa ${r.created_by == user.id ? 'fa-diamond' : ' '}"></i> <c:out value="${r.name}"/> </a></li>
                    </c:forEach>
                    <%-- fa-star fa-sign-in fa-user-plus  fa-diamond --%>
                </ul>
            </li>

        </ul>
        <!-- /.sidebar-menu  fa-sign-in   -->
    </section>
    <!-- /.sidebar -->
</aside>