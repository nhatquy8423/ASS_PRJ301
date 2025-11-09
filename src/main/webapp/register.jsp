<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register Form</title>
        <link href="bootstrap.min.css" rel="stylesheet" type="text/css">
        <style>
            /* Reset và font */
            * {
                box-sizing: border-box;
                font-family: 'Poppins', sans-serif;
            }
            body, html {
                margin: 0;
                padding: 0;
                height: 100%;
                background-color: #fff;
            }

            .container {
                display: flex;
                width: 100%;
                height: 100vh;
            }

            /* Left panel - form */
            .left-panel {
                margin-left:100px;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #fff;
            }

            .register-form {
                display: flex;
                flex-direction: column;
                justify-content: center;
               
                width: 1000px;
                padding: 30px;
                 box-shadow: 0 0 20px rgba(208, 0, 0, 0.4);
                border-radius:20px;
                
            }

            .register-form h2 {
                color: #d00000;
                font-weight: 600;
                margin-bottom: 10px;
                text-align: center;
            }

            .register-form p {
                color: #555;
                margin-bottom: 25px;
                text-align: center;
            }

            .form-control {
                border-radius: 0;
                margin-bottom: 20px;
                height: 45px;
            }

            .btn-register {
                background-color: #d00000;
                color: white;
                border: none;
                width: 100%;
                height: 45px;
                font-size: 16px;
                font-weight: 600;
                border-radius: 6px;
            }

            .btn-register:hover {
                background-color: #a30000;
            }

            .alert-danger {
                margin-top: 5px;
                font-size: 0.9rem;
            }

        

            a {
                text-decoration: none;
                color: #d00000;
                font-weight: 600;
            }
            a:hover {
                color: #a30000;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <!-- Left panel - form -->
            <div class="left-panel">
                <div class="register-form">
                    <h2>Đăng ký tài khoản</h2>
                    <p>Đăng ký tài khoản mới</p>

                    <form action="register" method="post">
                        <!-- Name -->
                        <input type="text" class="form-control" name="name" placeholder="Họ và tên" required>

                        <!-- Email -->
                        <input type="email" class="form-control" name="email" placeholder="Địa chỉ Email" required>
                        <c:if test="${not empty errorDuplicateEmail}">
                            <small style="color: red;font-size:17px">${errorDuplicateEmail}</small>
                        </c:if>

                        <!-- Phone -->
                        <input type="tel" class="form-control" name="numberPhone" pattern="0[0-9]{9,10}" placeholder="Số điện thoại (10-11 số)" required>
                        <c:if test="${not empty errorDuplicatePhone}">
                            <small style="color: red;font-size:17px" >${errorDuplicatePhone}</small>
                        </c:if>

                        <!-- Password -->
                        <input type="password" pattern="(?=.*[^a-zA-Z0-0]).{6,}" title="Mật khẩu phải có ít nhất 6 kí tự và 1 kí tự đặc biệt" class="form-control" name="password" placeholder="Mật khẩu" required>

                        <!-- Confirm Password -->
                        <input type="password" pattern="(?=.*[^a-zA-Z0-0]).{6,}" title="Mật khẩu phải có ít nhất 6 kí tự và 1 kí tự đặc biệt" class="form-control" name="confirmPassword" placeholder="Xác nhận mật khẩu" required>
                        <c:if test="${not empty errorConfirmPassword}">
                            <small style="color: red;font-size:17px">${errorConfirmPassword}</small>
                        </c:if>

                        <!-- Address -->
                        <input type="text" class="form-control" name="address" placeholder="Địa chỉ sinh sống" required>

                        <!-- Terms -->
                        <div class="form-check mb-3">
                            <input type="checkbox" class="form-check-input" id="terms" required>
                            <label class="form-check-label" for="terms">Tôi đồng ý với các điều khoản</label>
                        </div>

                        <button type="submit" class="btn-register">Đăng ký</button>
                    </form>

                    <div class="text-center mt-3">
                        <span>Bạn đã có tài khoản? </span><a href="login.jsp">Đăng nhập tại đây</a>
                    </div>
                </div>
            </div>

            <!-- Right panel - ảnh -->
            <div class="right-panel"></div>
        </div>

    </body>
</html>
