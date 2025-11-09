<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Enter OTP Code</title>
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');

            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: sans-serif;
                background: grey;
                height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 20px;
                color: #1e293b;
            }

            .container {
                background: #ffffff;
                padding: 48px 40px;
                border-radius: 16px;
                box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
                width: 100%;
                max-width: 440px;
                text-align: center;
                border: 1px solid #e2e8f0;
            }

            h1 {
                font-size: 30px;
                font-weight: 700;
                margin-bottom: 40px;
                color: #0f172a;
            }

            .form-group {
                margin-bottom: 28px;
                text-align: left;
                position: relative;
            }

            .form-group label {
                display: block;
                font-size: 15px;
                font-weight: 600;
                margin-bottom: 8px;
                color: #1e293b;
            }

            .form-group label span {
                color: #ef4444;
            }

            .form-group input {
                width: 100%;
                padding: 14px 16px;
                font-size: 16px;
                border: 1.5px solid #cbd5e1;
                border-radius: 10px;
                outline: none;
                transition: all 0.25s;
                background: #ffffff;
            }

            .form-group input:focus {
                border-color: #10b981;
                box-shadow: 0 0 0 4px rgba(16, 185, 129, 0.1);
            }

            .form-group input::placeholder {
                color: #94a3b8;
            }

            .otp-hint {
                font-size: 13px;
                color: #64748b;
                margin-top: 6px;
                margin-left: 2px;
            }

            .btn {
                background: #2c3e50;
                color: white;
                font-size: 16px;
                font-weight: 600;
                padding: 14px 0;
                border: none;
                border-radius: 12px;
                cursor: pointer;
                width: 100%;
                margin-top: 10px;
                transition: all 0.25s;
                box-shadow: 0 4px 12px rgba(16, 185, 129, 0.25);
            }

            .btn:hover {
                background: #059669;
                transform: translateY(-1px);
            }

            .btn:active {
                transform: translateY(0);
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Enter OTP Code</h1>

            <form action="verify-otp" method="post">
               <div class="form-group">
                    <label>Email <span>*</span></label>
                    <input type="email" value="${sessionScope.emailValue}" name="email" readonly>
                   
                </div>

                <div class="form-group">
                     <c:if test="${not empty error}">
                        <span style="color:red">${error}</span>
                    </c:if>
                    <label>OTP Code <span>*</span></label>
                    <input type="text" placeholder="OTP" maxlength="6" name="otp" required>
                    <!-- inline error cho OTP -->
                   
                    <div class="otp-hint">Nhập mã OTP vừa gửi vào email</div>
                </div>

                <button class="btn" type="submit">Next</button>
            </form>
        </div>
    </body>
</html>