<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!-- Header Navbar -->
<nav class="navbar navbar-static-top" role="navigation">
    <!-- Sidebar toggle button-->
    <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
    </a>
    <!-- Navbar Right Menu -->
    <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">

            <!-- User Account Menu -->
            <li class="dropdown user user-menu">
                <!-- Menu Toggle Button -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <!-- beansuser image in the navbar-->
                    <img src="${path}/styles/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                    <!-- hidden-xs hides the username on small devices so only the image appears. -->
                    <span class="hidden-xs"><c:out value="${user.nickname}"></c:out></span>
                </a>
                <ul class="dropdown-menu">
                    <!-- beansuser image in the menu -->
                    <li class="user-header">
                        <img src="${path}/styles/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                        <p>
                            <c:out value="${user.email}"></c:out>
                            <small><fmt:message key="navbar.member"/> <c:out value="${user.registered_at}"></c:out></small>
                        </p>
                    </li>
                    <!-- Menu Footer-->
                    <li class="user-footer">
                        <div class="pull-left">
                            <a href="#" class="btn btn-default btn-flat"><fmt:message key="navbar.profileBtn"/></a>
                        </div>
                        <div class="pull-right">
                            <a href="${path}/content/logout" class="btn btn-default btn-flat"><fmt:message key="navbar.signOutBtn"/></a>
                        </div>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</nav>