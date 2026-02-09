<%@ page contentType="text/html;charset=UTF-8" %>

<!-- Sidebar CSS -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/static/css/admin-sidebar.css">

<!-- ADMIN LAYOUT WRAPPER -->
<div class="admin-wrapper">

    <!-- SIDEBAR -->
    <div class="admin-sidebar">
        <h4>Admin Panel</h4>

        <a href="${pageContext.request.contextPath}/Dashboard"
           class="menu-link">Dashboard</a>

        <a href="${pageContext.request.contextPath}/users.jsp"
           class="menu-link">Users</a>

        <a href="${pageContext.request.contextPath}/Product"
           class="menu-link">Product</a>

        <a href="${pageContext.request.contextPath}/roles.jsp"
           class="menu-link">Roles</a>

        <a href="${pageContext.request.contextPath}/settings.jsp"
           class="menu-link">Settings</a>

        <hr class="bg-secondary">

        <a href="#" onclick="logout()">Logout</a>
    </div>

    <!-- PAGE CONTENT START -->
    <div class="admin-content">
