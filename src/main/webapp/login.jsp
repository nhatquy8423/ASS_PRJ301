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

            .right-panel {
                width: 50%;
                background-color: #f8f8f8;

                background-image: url('img/background_login.jpg'); 

                background-size: cover;
                background-position: center;
            }

            /* Chỉ tự custom cảnh báo lỗi */
            .alert-danger {
                color: #721c24;
                background-color: #f8d7da;
                border: 1px solid #f5c6cb;
            }

            /* Thêm style xanh lá cho success (nếu muốn custom) */
            .alert-success {
                color: #155724;
                background-color: #d4edda;
                border: 1px solid #c3e6cb;
            }

        </style>
    </head>
    <body>

        <div class="container">
            <div class="left-panel">
                <div class="login-form">

                    

                    <%
                        String savedEmail = (String) request.getAttribute("savedEmail");
                        String savedPassword = (String) request.getAttribute("savedPassword");
                        Boolean rememberCheckedObj = (Boolean) request.getAttribute("rememberChecked");
                        boolean rememberChecked = (rememberCheckedObj != null && rememberCheckedObj);


                        

                        if (savedEmail == null) {
                            savedEmail = "";
                            savedPassword = "";

                            Cookie[] cookies = request.getCookies();
                            if (cookies != null) {
                                for (Cookie c : cookies) {
                                    if (c.getName().equals("userEmail")) {
                                        savedEmail = c.getValue();
                                    }
                                    
                                }
                            }
                            if (!savedEmail.isEmpty()) {
                                rememberChecked = true;
                            }
                        }
                    %>

                    
                    <h2>Đăng nhập</h2>
                    <p>Đăng nhập để truy cập vào website của chúng tôi</p>

                    <form action="auth" method="POST"> 
                        

                        <input type="email" class="form-control" name="email" placeholder="Email" value="<%= savedEmail%>" required>
                        <input type="password"  pattern="(?=.*[^a-zA-Z0-0]).{6,}" title="Mật khẩu phải có ít nhất 6 kí tự và 1 kí tự đặc biệt" class="form-control" name="password" placeholder="Password"  required>

                        <div class="remember-section">
                            <div>
                                <input type="checkbox" id="remember" name="remember" <%= rememberChecked ? "checked" : ""%>>
                                <label for="remember">Ghi nhớ tài khoản</label>
                            </div>
                            <a href="request-reset" style="color:#d00000;">Quên mật khẩu</a>
                        </div>


                        <button type="submit" class="btn-login">Đăng nhập</button>

                        <div class="text-center mt-3">
                            <span>Bạn chưa có tài khoản? </span>
                            <a href="register" style="color:#d00000; font-weight:600;">Đăng ký</a>
                        </div>
                        
                        <div class="text-center mt-4" style="font-size: 14px;">
                            <a href="admin" style="color:#555;">Đăng nhập với tư cách Quản trị viên</a>
                        </div>
                    </form>


                    <c:if test="${not empty errorMessage}">
                        <p class="alert alert-danger" role="alert" style="margin-top: 15px;">${errorMessage}</p>
                    </c:if>

                    

                </div>


            </div>

            <div class="right-panel"></div>
        </div>

    </body>
</html>