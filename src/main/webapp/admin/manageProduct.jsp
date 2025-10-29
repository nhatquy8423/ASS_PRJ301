<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản lý Sản phẩm</title>
        <link href="../bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    </head>
    <body>

        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/admin">Admin Panel</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin">Dashboard</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/admin/product">Quản lý Sản phẩm</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/order">Quản lý Đơn hàng</a>
                        </li>
                    </ul>
                </div>
                <div class="d-flex">
                    <span class="navbar-text text-white me-3">
                        Chào, ${sessionScope.admin.admin_name}
                    </span>
                    <a href="${pageContext.request.contextPath}/admin?action=logout" class="btn btn-outline-light">Đăng xuất</a>
                </div>
            </div>
        </nav>

        <div class="container mt-4">

            <c:if test="${not empty sessionScope.successMsg}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${sessionScope.successMsg}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="successMsg" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.errorMsg}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${sessionScope.errorMsg}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="errorMsg" scope="session" />
            </c:if>

            <div class="row">

                <div class="col-md-4">
                    <c:choose>
                        <c:when test="${not empty requestScope.productToEdit}">
                            <h3>Cập nhật Sản phẩm</h3>
                        </c:when>
                        <c:otherwise>
                            <h3>Thêm Sản phẩm mới</h3>
                        </c:otherwise>
                    </c:choose>

                    <form action="${pageContext.request.contextPath}/admin/product" method="POST">

                        <c:choose>
                            <c:when test="${not empty requestScope.productToEdit}">
                                <input type="hidden" name="action" value="update"> 
                                <input type="hidden" name="pro_id" value="${requestScope.productToEdit.pro_id}">
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" name="action" value="add"> 
                            </c:otherwise>
                        </c:choose>


                        <div class="mb-3">
                            <label for="pro_name" class="form-label">Tên sản phẩm</label>
                            <input type="text" class="form-control" id="pro_name" name="pro_name" 
                                   value="${requestScope.productToEdit.pro_name}" required>
                        </div>

                        <div class="mb-3">
                            <label for="brand" class="form-label">Thương hiệu</label>
                            <input type="text" class="form-control" id="brand" name="brand" 
                                   value="${requestScope.productToEdit.brand}">
                        </div>

                        <div class="mb-3">
                            <label for="image_url" class="form-label">Đường dẫn ảnh (VD: img/nuochoa/ten.jpg)</label>
                            <input type="text" class="form-control" id="image_url" name="image_url" 
                                   value="${requestScope.productToEdit.image_url}">
                        </div>

                        <div class="mb-3">
                            <label for="cat_id" class="form-label">Danh mục</label>
                            <select class="form-select" id="cat_id" name="cat_id">
                                <c:forEach var="cat" items="${requestScope.categoryList}">
                                    <option value="${cat.cat_id}" 
                                            ${cat.cat_id == requestScope.productToEdit.cat_id.cat_id ? 'selected' : ''}
                                            >
                                        ${cat.cat_name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="des" class="form-label">Mô tả</label>
                            <textarea class="form-control" id="des" name="des" rows="3">${requestScope.productToEdit.des}</textarea>
                        </div>

                        <c:choose>
                            <c:when test="${not empty requestScope.productToEdit}">
                                <button type="submit" class="btn btn-primary w-100">Lưu Cập nhật</button>
                                <a href="${pageContext.request.contextPath}/admin/product" class="btn btn-secondary w-100 mt-2">Hủy Sửa</a>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="btn btn-primary w-100">Thêm Sản phẩm</button>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </div>

                <div class="col-md-8">
                    <h3>Danh sách Sản phẩm</h3>
                    <table class="table table-striped table-bordered align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Ảnh</th>
                                <th>Tên sản phẩm</th>
                                <th>Thương hiệu</th>
                                <th>Danh mục</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${requestScope.productList}">
                                <tr>
                                    <td>${p.pro_id}</td>
                                    <td>
                                        <img src="../${p.image_url}" alt="${p.pro_name}" style="width: 50px; height: 50px; object-fit: cover;">
                                    </td>
                                    <td>${p.pro_name}</td>
                                    <td>${p.brand}</td>

                                    <td>${p.cat_id.cat_name}</td> 

                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/product?action=edit&pro_id=${p.pro_id}" 
                                           class="btn btn-warning btn-sm" title="Sửa">
                                            <i class="fa fa-edit"></i>
                                        </a>

                                        <a href="${pageContext.request.contextPath}/admin/product?action=delete&pro_id=${p.pro_id}" 
                                           class="btn btn-danger btn-sm" title="Xóa" 
                                           onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm ${p.pro_id} không?')">
                                            <i class="fa fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="../bootstrap.bundle.min.js" type="text/javascript"></script>
    </body>
</html>