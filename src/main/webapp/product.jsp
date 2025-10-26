<%-- 
    Document   : product.jsp (Đã loại bỏ logic fn và sử dụng đường dẫn Context Path đơn giản)
    Created on : Oct 26, 2025
    Author     : [Your Name]
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- Loại bỏ JSTL functions (fn) để đơn giản hóa --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cửa hàng nước hoa</title>
    
    <link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">

    <style>
        /* CSS tùy chỉnh (Đảm bảo các class này khớp với file CSS của bạn) */
        .breadcrumb-section {
            padding: 50px 0;
            background-size: cover;
            background-position: center;
            background-color: #f3f6fa; 
            color: #212529;
            margin-bottom: 2rem;
        }
        .product-card-img {
            height: 250px;
            object-fit: cover;
            width: 100%;
        }
        .product-item {
            border: 1px solid #e1e1e1;
            border-radius: 0.5rem;
            overflow: hidden;
            text-align: center;
            padding-bottom: 1rem;
            transition: all 0.3s;
        }
        .product-item:hover {
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
        }
        .product-hover-icons {
            position: absolute;
            top: 50%;
            left: 0;
            right: 0;
            transform: translateY(-50%);
            display: flex;
            justify-content: center;
            gap: 10px;
            opacity: 0;
            transition: opacity 0.3s;
        }
        .product-item:hover .product-hover-icons {
            opacity: 1;
        }
        .product-hover-icons a {
            width: 40px;
            height: 40px;
            background: #fff;
            color: #333;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            font-size: 1.1rem;
            border: 1px solid #ccc;
        }
        .product-hover-icons a:hover {
            background-color: #7fad39; /* Màu xanh lá Ogani */
            color: #fff;
        }
    </style>
</head>

<body>
    <%-- <jsp:include page="header.jsp" /> --%>
    
    <section class="breadcrumb-section" 
             style="background-image: url('${pageContext.request.contextPath}/img/breadcrumb.jpg');">
        <div class="container">
            <div class="row">
                <div class="col-12 text-center">
                    <div class="breadcrumb__text">
                        <h2 class="text-white">Sản phẩm</h2>
                        <div class="breadcrumb__option text-white">
                            <a href="${pageContext.request.contextPath}/home" class="text-white text-decoration-none">Home</a>
                            <span class="text-white"> / Shop</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
    <section class="product spad py-5">
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-5">
                    <div class="sidebar">
                        
                        <div class="mb-4 p-3 border rounded shadow-sm">
                            <h4 class="mb-3">Tìm kiếm</h4>
                            <form action="${pageContext.request.contextPath}/product" method="get">
                                <input type="hidden" name="action" value="search">
                                <div class="input-group">
                                    <input type="text" name="keyword" class="form-control" 
                                           placeholder="Tên sản phẩm..." 
                                           value="${requestScope.keyword}">
                                    <button class="btn btn-primary" type="submit">
                                        <i class="fa fa-search"></i>
                                    </button>
                                </div>
                            </form>
                        </div>
                        
                        <div class="mb-4 p-3 border rounded shadow-sm">
                            <h4>Danh mục sản phẩm</h4>
                            <div class="list-group">
                                <a href="${pageContext.request.contextPath}/product" 
                                   class="list-group-item list-group-item-action ${empty requestScope.active_cat_id ? 'active' : ''}">
                                    Tất cả sản phẩm
                                </a>
                                <c:forEach var="c" items="${requestScope.categories}">
                                    <a href="${pageContext.request.contextPath}/product?action=filter&cat_id=${c.cat_id}" 
                                       class="list-group-item list-group-item-action ${requestScope.active_cat_id == c.cat_id ? 'active' : ''}">
                                        ${c.cat_name}
                                    </a>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="mb-4 p-3 border rounded shadow-sm">
                            <h4>Lọc theo giá</h4>
                            <form action="${pageContext.request.contextPath}/product" method="get">
                                <input type="hidden" name="action" value="price_filter">
                                
                                <div class="mb-2">
                                    <label class="form-label small text-muted">Giá Tối thiểu (VNĐ)</label>
                                    <input type="number" name="min_price" class="form-control form-control-sm" 
                                           value="${requestScope.min_price != null ? requestScope.min_price : 100000}" 
                                           placeholder="100000" min="0" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label small text-muted">Giá Tối đa (VNĐ)</label>
                                    <input type="number" name="max_price" class="form-control form-control-sm" 
                                           value="${requestScope.max_price != null ? requestScope.max_price : 5000000}" 
                                           placeholder="5000000" min="0" required>
                                </div>
                                <button type="submit" class="btn btn-success w-100">LỌC</button>
                            </form>
                        </div>
                        
                    </div>
                </div>

                <div class="col-lg-9 col-md-7">
                    
                    <c:if test="${not empty sessionScope.errorMsg}">
                        <div class="alert alert-danger" role="alert">
                            ${sessionScope.errorMsg}
                        </div>
                        <c:remove var="errorMsg" scope="session"/>
                    </c:if>

                    <div class="d-flex justify-content-between align-items-center mb-4 p-3 border rounded bg-light">
                        <h6>Tìm thấy: <span class="text-primary">${products.size()}</span> sản phẩm</h6>
                        <div class="d-flex align-items-center">
                            <span class="me-2">Sắp xếp:</span>
                            <select id="sortSelector" class="form-select form-select-sm" style="width: auto;">
                                <option value="default" ${requestScope.sort == 'default' ? 'selected' : ''}>Mặc định</option>
                                <option value="price_asc" ${requestScope.sort == 'price_asc' ? 'selected' : ''}>Giá: Thấp -> Cao</option>
                                <option value="price_desc" ${requestScope.sort == 'price_desc' ? 'selected' : ''}>Giá: Cao -> Thấp</option>
                            </select>
                        </div>
                    </div>

                    <div class="row g-4">
                        <c:forEach var="p" items="${requestScope.products}">
                            <div class="col-lg-4 col-md-6 col-sm-6">
                                <div class="product-item shadow-sm">
                                    <div class="position-relative">
                                        
                                        <%-- ĐÃ FIX: CHỈ SỬ DỤNG CONTEXT PATH + GIÁ TRỊ DB --%>
                                        <img src="${pageContext.request.contextPath}/${p.image_url}" 
                                             class="card-img-top product-card-img" alt="${p.pro_name}">
                                        
                                        <div class="product-hover-icons">
                                            <a href="#"><i class="fa-regular fa-heart"></i></a>
                                            <a href="${pageContext.request.contextPath}/productdetail?pro_id=${p.pro_id}"><i class="fa-regular fa-eye"></i></a> 
                                        </div>
                                    </div>
                                    
                                    <div class="p-3">
                                        <small class="text-muted">${p.brand}</small>
                                        <h6 class="my-1">
                                            <a href="${pageContext.request.contextPath}/productdetail?pro_id=${p.pro_id}" class="text-dark text-decoration-none">
                                                ${p.pro_name}
                                            </a>
                                        </h6>
                                        
                                        <c:choose>
                                            <c:when test="${p.min_price > 0}">
                                                <h5 class="text-danger mt-1">
                                                    Từ <fmt:formatNumber value="${p.min_price}" type="number" maxFractionDigits="0"/> VNĐ
                                                </h5>
                                                <a href="${pageContext.request.contextPath}/productdetail?pro_id=${p.pro_id}" class="btn btn-sm btn-primary w-75 mt-2">
                                                    Xem chi tiết
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                 <h5 class="text-danger mt-1">Hết hàng</h5>
                                                 <a href="${pageContext.request.contextPath}/productdetail?pro_id=${p.pro_id}" class="btn btn-sm btn-secondary w-75 mt-2 disabled">
                                                    Xem chi tiết
                                                 </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <c:if test="${empty requestScope.products}">
                        <div class="alert alert-warning mt-4" role="alert">
                            Không tìm thấy sản phẩm nào phù hợp với điều kiện tìm kiếm/lọc.
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </section>
    <%-- <jsp:include page="footer.jsp" /> --%>

    <script src="${pageContext.request.contextPath}/bootstrap.bundle.min.js" type="text/javascript"></script>

    <script>
        document.getElementById('sortSelector').addEventListener('change', function() {
            const sortValue = this.value;
            const currentUrl = new URL(window.location.href);
            
            // 1. Xóa tham số sắp xếp cũ (nếu có)
            currentUrl.searchParams.delete('sort');
            
            // 2. Thêm tham số sắp xếp mới (nếu không phải là mặc định)
            if (sortValue !== 'default') {
                currentUrl.searchParams.set('sort', sortValue);
            }
            
            // 3. Chuyển hướng để tải lại trang với các tham số mới (giữ lại lọc, tìm kiếm, v.v.)
            window.location.href = currentUrl.toString();
        });
    </script>
</body>
</html>