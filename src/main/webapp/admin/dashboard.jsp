<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Admin"%>
<%
    // Lấy thông tin Admin từ session để cá nhân hóa
    Admin currentAdmin = (Admin) session.getAttribute("admin");
    if (currentAdmin == null) {
        // Trường hợp khẩn cấp: nếu Filter bị bypass (mặc dù AdminAuthFilter đã xử lý)
        response.sendRedirect(request.getContextPath() + "/admin");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link href="${pageContext.request.contextPath}/css/admin-style.css" rel="stylesheet" type="text/css"/>
    </head>
<body>

    <div class="admin-container">
        
        <%-- ------------------------------------------------ --%>
        <%--                  KHU VỰC SIDEBAR/MENU             --%>
        <%-- ------------------------------------------------ --%>
        <div class="sidebar">
            <div class="logo">ADMIN PANEL</div>
            <div class="nav-links">
                
                <a href="${pageContext.request.contextPath}/admin" class="active">
                    <i class="icon"></i> Dashboard
                </a>
                
                <a href="${pageContext.request.contextPath}/admin/order">
                    <i class="icon"></i> Quản lý Đơn hàng
                </a>
                
                <a href="${pageContext.request.contextPath}/admin/product">
                    <i class="icon"></i> Quản lý Sản phẩm
                </a>
                
                <a href="${pageContext.request.contextPath}/admin?action=logout" class="logout-link">
                    <i class="icon"></i> Đăng xuất (<%= currentAdmin.getAdmin_name() %>)
                </a>
            </div>
        </div>

        <%-- ------------------------------------------------ --%>
        <%--                  KHU VỰC NỘI DUNG CHÍNH          --%>
        <%-- ------------------------------------------------ --%>
        <div class="main-content">
            <header class="admin-header">
                <h1>Chào mừng, <%= currentAdmin.getAdmin_name() %>!</h1>
            </header>
            
            <div class="dashboard-widgets">
                <h2>Tổng quan hệ thống</h2>
                <div class="widget">
                    <h3>Tổng Doanh thu</h3>
                    <p>$15,000,000 VND</p>
                </div>
                <div class="widget">
                    <h3>Đơn hàng mới</h3>
                    <p>12 đơn hàng</p>
                </div>
                <div class="widget">
                    <h3>Sản phẩm sắp hết</h3>
                    <p>5 loại</p>
                </div>
            </div>
            
            <%-- Thêm các biểu đồ, thống kê chi tiết tại đây --%>
            
        </div>
    </div>

</body>
</html>