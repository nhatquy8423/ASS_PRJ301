<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title >Nhập Email</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: sans-serif;
            background-color:grey;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .container {
            background: #fff;
            padding: 40px 30px;
            border-radius: 12px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 420px;
            text-align: center;
        }

        h1 {
            font-size: 28px;
            font-weight: 700;
            color: #333;
            margin-bottom: 8px;
        }

        p {
            font-size: 14px;
            color: #666;
            margin-bottom: 30px;
            line-height: 1.5;
        }

        .form-group {
            position: relative;
            margin-bottom: 30px;
        }

        .form-group label {
            position: absolute;
            top: -8px;
            left: 15px;
            background: #fff;
            padding: 0 8px;
            font-size: 14px;
            color: #e74c3c;
            font-weight: 500;
        }

        .form-group input {
            width: 100%;
            padding: 16px 18px;
            font-size: 16px;
            border: 2px solid #ddd;
            border-radius: 8px;
            outline: none;
            transition: all 0.3s;
        }

        .form-group input:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.15);
        }

        .placeholder {
            color: #aaa;
            font-size: 14px;
            margin-top: 8px;
            text-align: left;
            padding-left: 18px;
        }

        .safe-info {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            margin-bottom: 30px;
            color: #27ae60;
            font-size: 14px;
        }

        .safe-info img {
            width: 20px;
            height: 20px;
        }

        .btn {
            background: #2c3e50;
            color: #fff;
            font-size: 16px;
            font-weight: 500;
            padding: 14px 0;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            width: 100%;
            transition: background 0.3s;
        }

        .btn:hover {
            background: #1a252f;
        }
    </style>
</head>
<body>
    <form action="request-reset" method="post">
    <div class="container">
        <h1>Nhập Email</h1>
        <p>Vui lòng nhập email tài khoản bạn đã làm mất mật khẩu</p>

        
        <div class="form-group">
            <label>E-mail <span style="color:black;"></span></label>
            <input type="email" placeholder="Email Address" name="email" required>
            <div class="placeholder">example@example.com</div>
            <c:if test="${not empty message}">
                <div class="error-inline">${message}</div><!-- comment -->
            </c:if>
        </div>

       

        <button class="btn">Tiếp tục</button>
        </div>
    </form>
    
</body>
</html>