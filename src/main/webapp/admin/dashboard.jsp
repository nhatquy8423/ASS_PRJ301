<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%-- THÊM DÒNG NÀY (Sửa lỗi hiển thị VNĐ) --%>
<%@page import="model.Admin"%>
<%
    // Giữ nguyên logic kiểm tra session của bạn
    Admin currentAdmin = (Admin) session.getAttribute("admin");
    if (currentAdmin == null) {
        response.sendRedirect(request.getContextPath() + "/admin");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Dashboard</title>
        
        <link href="bootstrap.min.css" rel="stylesheet" type="text/css"/>
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    </head>
    
    <body>

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/admin">Admin Panel</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/admin">Dashboard</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/order">Quản lý Đơn hàng</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/product">Quản lý Sản phẩm</a>
                        </li>
                    </ul>
                </div>
                <div class="d-flex">
                    <span class="navbar-text text-white me-3">
                        Chào, <%= currentAdmin.getAdmin_name()%>
                    </span>
                    <a href="${pageContext.request.contextPath}/admin?action=logout" class="btn btn-outline-light">Đăng xuất</a>
                </div>
            </div>
        </nav>

        <div class="container mt-4">
            
            <h1 class="mb-4">Chào mừng, <%= currentAdmin.getAdmin_name()%>!</h1>
            <h2>Tổng quan hệ thống</h2>

            <div class="row mt-4">

                <div class="col-md-4">
                    <div class="card text-white bg-success shadow-sm mb-3">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h5 class="card-title">Tổng Doanh thu</h5>
                                    <p class="fs-3 fw-bold mb-0">
                                        <fmt:formatNumber value="${requestScope.totalRevenue}" type="number" maxFractionDigits="0"/> VNĐ
                                    </p>
                                </div>
                                <i class="fa fa-dollar-sign fa-3x text-white-50"></i>
                            </div>
                            <small>(Chỉ tính đơn đã 'Delivered')</small>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card text-dark bg-warning shadow-sm mb-3">
                        <div class="card-body">
                           <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h5 class="card-title">Đơn hàng mới (Pending)</h5>
                                    <p class="fs-3 fw-bold mb-0">
                                        ${requestScope.newOrderCount} 
                                        <span class="fs-6 fw-normal">đơn</span>
                                    </p>
                                </div>
                                <i class="fa fa-inbox fa-3x text-dark-50"></i>
                            </div>
                            <a href="${pageContext.request.contextPath}/admin/order" class="card-link text-dark stretched-link">Xem chi tiết</a>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="card text-white bg-primary shadow-sm mb-3">
                        <div class="card-body">
                           <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h5 class="card-title">Tổng số sản phẩm</h5>
                                    <p class="fs-3 fw-bold mb-0">
                                        ${requestScope.totalProducts} 
                                        <span class="fs-6 fw-normal">loại</span>
                                    </p>
                                </div>
                                <i class="fa fa-boxes-stacked fa-3x text-white-50"></i>
                            </div>
                            <a href="${pageContext.request.contextPath}/admin/product" class="card-link text-white stretched-link">Xem chi tiết</a>
                        </div>
                    </div>
                </div>
                
           
    </body>
    <script src="bootstrap.bundle.min.js" type="text/javascript"></script>
</html>