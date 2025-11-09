<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Login</title>
        <%-- 
            Sử dụng CSS của trang login cũ.
            Giả sử file loginAdmin.jsp này nằm ở web root.
        --%>
        <link href="bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/login_style.css" rel="stylesheet" type="text/css"/> <%-- Hoặc dùng <style> tag như file cũ --%>

        <%-- Copy toàn bộ thẻ <style> từ file login.jsp của bạn dán vào đây --%>
        <style>
            /* Copy toàn bộ CSS từ file login.jsp [cite: 57-78] của bạn vào đây */
            body {
                margin: 0;
                height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #fff;
            }
            .container {
                display: flex;
                width: 100%;
                height: 100vh;
            }
            .left-panel {
                width: 50%;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #fff;
            }
            .login-form {
                width: 80%;
                max-width: 400px;
                padding: 30px;
            }
            .login-form h2 {
                font-weight: 700;
                color: #d00000;
                margin-bottom: 5px;
            }
            .login-form p {
                color: #777;
                margin-bottom: 25px;
            }
            .form-control {
                width: 100%;
                padding: 12px 15px;
                margin-bottom: 20px;
                border: 1px solid #ddd;
                border-radius: 5px;
            }
            .btn-login {
                width: 100%;
                padding: 12px;
                background-color: #d00000;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .right-panel {
                width: 50%;
                background-image: url('img/background_login.jpg');
                background-size: cover;
                background-position: center;
            }
            .alert {
                padding: 10px;
                margin-bottom: 15px;
                border-radius: 5px;
                color: #721c24;
                background-color: #f8d7da;
                border: 1px solid #f5c6cb;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="left-panel">
                <div class="login-form">
                    
                    <%-- Tiêu đề đã được hard-code cho Admin --%>
                    <h2>Đăng nhập Quản trị viên</h2>
                    <p>Sử dụng tài khoản admin để truy cập Dashboard</p>

                    <%-- 
                        Form này ĐƠN GIẢN, action được hard-code trỏ về "admin"
                        (chính là AdminController của bạn)
                    --%>
                    <form action="admin" method="POST"> 
                        
                        <input type="email" class="form-control" name="email" placeholder="Email" 
                               value="${requestScope.savedEmail}" required>
                        <input type="password" class="form-control" name="password" placeholder="Password" required>

                        <%-- Không cần phần "Ghi nhớ" hay "Quên mật khẩu" cho admin (trừ khi bạn muốn) --%>

                        <button type="submit" class="btn-login">Đăng nhập</button>
                    </form>

                    <%-- 
                        Hiển thị lỗi CHỈ từ AdminController (biến "error")
                        Chúng ta không cần "errorMessage" [cite: 104] (vốn dành cho Customer)
                    --%>
                    <c:if test="${not empty error}">
                        <p class="alert" role="alert" style="margin-top: 15px;">${error}</p>
                    </c:if>
                    
                    <div class="text-center mt-3">
                        <a href="home" style="color:#777;">Quay về trang chủ</a>
                    </div>
                </div>
            </div>
            <div class="right-panel"></div>
        </div>
    </body>
</html>