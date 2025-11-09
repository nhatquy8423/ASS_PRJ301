<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    model.Customer customer = (model.Customer) session.getAttribute("customer");
    if (customer == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Your Profile</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

        <style>
            body {
                background-color: #f8f9fa;
            }

            .profile-container {
                min-height: calc(100vh - 60px); /* trừ chiều cao navbar */
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .page-container {
                flex: 1;
                display: flex;
                justify-content: center;
                align-items: center;
                margin-top: 100px; /* card dịch xuống 50px */
            }
            .profile-card {
                position: relative;
                padding: 2rem;
                border-radius: 1rem;
                background-color: #ffffff;
                box-shadow: 0 8px 20px rgba(0,0,0,0.1);
                width: 100%;
                max-width: 400px;
            }

            /* Icon xung quanh card */
            .icon-bg {
                position: absolute;
                font-size: 2rem;
                color: rgba(0, 123, 255, 0.1);
            }
            .icon-top-left {
                top: -10px;
                left: -10px;
            }
            .icon-top-right {
                top: -10px;
                right: -10px;
            }
            .icon-bottom-left {
                bottom: -10px;
                left: -10px;
            }
            .icon-bottom-right {
                bottom: -10px;
                right: -10px;
            }

        </style>

    </head>
    <body>
        <div id="navbar">  <jsp:include page="navbar.jsp"/> </div>
        <div class="page-container d-flex flex-column justify-content-center align-items-center">

            <div class="profile-card">
                <!-- Background icons -->
                <i class="bi bi-stars icon-bg icon-top-left"></i>
                <i class="bi bi-heart-fill icon-bg icon-top-right"></i>
                <i class="bi bi-lightning-fill icon-bg icon-bottom-left"></i>
                <i class="bi bi-gem icon-bg icon-bottom-right"></i>

                <h5 class="card-title mb-4 text-center">Your Profile</h5>

                <form action="update-profile?id=${sessionScope.customer.cus_id}" method="post">
                    <div class="mb-3">
                        <label class="form-label">Full name</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-person-fill"></i></span>
                            <input type="text" class="form-control" name="fullname" value="<%= customer.getFullname()%>">
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Address</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-geo-alt-fill"></i></span>
                            <input type="text" class="form-control" name="address" value="<%= customer.getAddress()%>">
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Email address</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-envelope-fill"></i></span>
                            <input type="email" class="form-control" value="<%= customer.getEmail()%>" readonly>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Phone number</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-telephone-fill"></i></span>
                            <input type="text" class="form-control" name="phone" value="<%= customer.getPhone()%>">
                        </div>
                    </div>

                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Edit Profile</button>
                    </div>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
