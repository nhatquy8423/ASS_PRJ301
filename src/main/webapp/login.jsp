<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login Form</title>
        <link href="bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <style>
            
            .container {
                display: flex;
                width: 100%;
                height: 100vh;
                margin: 0;
                padding: 0;
            }

            * {
                box-sizing: border-box;
                font-family: 'Poppins', sans-serif;
            }

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

            /* Phần form bên trái */
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
                font-size: 16px;
                transition: border-color 0.3s;
            }

            .form-control:focus {
                border-color: #d00000;
                outline: none;
            }

            .remember-section {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 30px;
                font-size: 14px;
            }

            .remember-section div {
                display: flex;
                align-items: center;
            }

            #remember {
                margin-right: 5px;
                width: auto;
                height: auto;
            }

            .btn-login {
                width: 100%;
                padding: 12px;
                background-color: #d00000;
                color: white;
                border: none;
                border-radius: 5px;
                font-size: 18px;
                font-weight: 600;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .btn-login:hover {
                background-color: #a00000;
            }

            /* Phần ảnh bên phải */
            .right-panel {
                width: 50%;
                background-color: #f8f8f8;
                /* Cập nhật đường dẫn ảnh của bạn tại đây */
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
                font-size: 14px;
                text-align: center;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="left-panel">
                <div class="login-form">
                    
                    <%-- ---------------------------------------------------- --%>
                    <%--               1. LOGIC XỬ LÝ COOKIE & ATTRIBUTES     --%>
                    <%-- ---------------------------------------------------- --%>
                    <%
                        // Lấy các attribute được set từ Controller (dùng khi login thất bại)
                        String savedEmail = (String) request.getAttribute("savedEmail");
                        String savedPassword = (String) request.getAttribute("savedPassword");
                        Boolean rememberCheckedObj = (Boolean) request.getAttribute("rememberChecked");
                        boolean rememberChecked = (rememberCheckedObj != null && rememberCheckedObj);
                        
                        // Nếu không có attribute (login lần đầu), kiểm tra cookie
                        if (savedEmail == null) {
                            savedEmail = "";
                            savedPassword = "";
                            
                            Cookie[] cookies = request.getCookies();
                            if (cookies != null) {
                                for (Cookie c : cookies) {
                                    if (c.getName().equals("userEmail")) {
                                        savedEmail = c.getValue();
                                    }
                                    if (c.getName().equals("userPassword")) {
                                        savedPassword = c.getValue();
                                    }
                                }
                            }
                            if (!savedPassword.isEmpty()) {
                                rememberChecked = true;
                            }
                        }
                    %>
                    
                    <%-- ---------------------------------------------------- --%>
                    <%--             2. HIỂN THỊ TIÊU ĐỀ ĐỘNG (DÙNG JSTL)     --%>
                    <%-- ---------------------------------------------------- --%>
                    <h2>
                    <c:choose>
                        <%-- Nếu AdminController set loginTarget là 'admin' --%>
                        <c:when test="${loginTarget == 'admin'}">
                            Đăng nhập Quản trị viên
                        </c:when>
                        <%-- Mặc định là Customer Login --%>
                        <c:otherwise>
                            Đăng nhập
                        </c:otherwise>
                    </c:choose>
                    </h2>
                    <p>Đăng nhập để truy cập vào website của chúng tôi</p>

                    <%-- ---------------------------------------------------- --%>
                    <%--              3. ĐIỀU CHỈNH FORM ACTION (DÙNG JSTL)   --%>
                    <%-- ---------------------------------------------------- --%>
                    <form action="<c:choose>
                                    <%-- Nếu AdminController set loginTarget là 'admin', POST về /admin --%>
                                    <c:when test='${loginTarget == "admin"}'>
                                        admin
                                    </c:when>
                                    <%-- Mặc định, POST về /auth (AuthController) --%>
                                    <c:otherwise>
                                        auth
                                    </c:otherwise>
                                  </c:choose>" method="POST"> 
                        
                        <%-- Input name="email" và name="password" được giữ nguyên cho cả 2 luồng --%>
                        <input type="email" class="form-control" name="email" placeholder="Email" value="<%= savedEmail%>" required>
                        <input type="password" class="form-control" name="password" placeholder="Password" value="<%= savedPassword%>" required>

                        <div class="remember-section">
                            <div>
                                <input type="checkbox" id="remember" name="remember" <%= rememberChecked ? "checked" : ""%>>
                                <label for="remember">Ghi nhớ tài khoản</label>
                            </div>
                            <a href="#" style="color:#d00000;">Quên mật khẩu</a>
                        </div>

                        <button type="submit" class="btn-login">Đăng nhập</button>

                        <div class="text-center mt-3">
                            <span>Bạn chưa có tài khoản? </span>
                            <a href="register" style="color:#d00000; font-weight:600;">Đăng ký</a>
                        </div>
                    </form>

                    <%-- ---------------------------------------------------- --%>
                    <%--         4. HIỂN THỊ THÔNG BÁO LỖI (DÙNG JSTL)        --%>
                    <%-- ---------------------------------------------------- --%>
                    <%-- errorMessage: Lỗi từ AuthController (Customer) --%>
                    <c:if test="${not empty errorMessage}">
                        <p class="alert alert-danger" role="alert">${errorMessage}</p>
                    </c:if>
                    
                    <%-- error: Lỗi từ AdminController (Admin) --%>
                    <c:if test="${not empty error}">
                        <p class="alert alert-danger" role="alert">${error}</p>
                    </c:if>
                </div>
            </div>

            <div class="right-panel"></div>
        </div>

    </body>
</html>