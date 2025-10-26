<%-- 
    Document   : product-detail.jsp
    Created on : Oct 26, 2025
    Author     : [Your Name]
    Mục tiêu: Hiển thị chi tiết sản phẩm và các biến thể.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${requestScope.product.pro_name} | Chi tiết sản phẩm</title>
    
    <link href="${pageContext.request.contextPath}/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    
    <style>
        .product-details, .related-product {padding: 60px 0;}
        .product__details__pic__item--large {max-height: 450px; width: auto; object-fit: contain;}
        .product__details__price {font-size: 30px; font-weight: 700; color: #7fad39; margin-bottom: 20px;}
        .primary-btn {color: white; background: #7fad39; border: none; padding: 10px 20px;}
        .heart-icon {color: #7fad39; font-size: 24px; margin-left: 10px;}
        .card-img-top {height: 200px; object-fit: cover;}
    </style>
</head>

<body>
    <%-- <jsp:include page="header.jsp" /> --%>
    <c:set var="product" value="${requestScope.product}"/>
    
    <c:if test="${product != null}">
        
        <section class="py-3 bg-light">
            <div class="container">
                <a href="${pageContext.request.contextPath}/home">Home</a> / 
                <a href="${pageContext.request.contextPath}/product">Shop</a> / 
                <span>${product.pro_name}</span>
            </div>
        </section>

        <section class="product-details spad">
            <div class="container">
                <div class="row">
                    
                    <%-- THÔNG TIN SẢN PHẨM & FORM --%>
                    <div class="col-lg-6 col-md-6">
                        <div class="product__details__pic">
                            <div class="product__details__pic__item">
                                <img class="product__details__pic__item--large img-fluid"
                                     src="${pageContext.request.contextPath}/${product.image_url}" 
                                     alt="${product.pro_name}">
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-lg-6 col-md-6">
                        <div class="product__details__text">
                            <h3>${product.pro_name}</h3>
                            <p class="text-muted">${product.brand}</p>
                            
                            <form action="${pageContext.request.contextPath}/productdetail" method="post">
                                <input type="hidden" name="action" value="add">
                                
                                <c:choose>
                                    <c:when test="${not empty requestScope.variants}"   >
                                        
                                        <div class="product__details__price">
                                            <span id="displayPrice">
                                                <fmt:formatNumber value="${requestScope.variants[0].price}" type="number" maxFractionDigits="0"/> VNĐ
                                            </span>
                                        </div>

                                        <div class="mb-3">
                                            <label for="variantSelect" class="form-label"><b>Chọn Dung tích:</b></label>
                                            <select name="variantId" id="variantSelect" class="form-select">
                                                <c:forEach var="v" items="${requestScope.variants}">
                                                    <option value="${v.variant_id}" data-price="${v.price}">
                                                        ${v.volume} - 
                                                        <fmt:formatNumber value="${v.price}" type="number" maxFractionDigits="0"/> VNĐ
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="product__details__quantity mb-3">
                                            <label for="quantityInput" class="form-label"><b>Số lượng:</b></label>
                                            <div class="input-group" style="width: 150px;">
                                                <input type="number" id="quantityInput" name="quantity" class="form-control" value="1" min="1" required>
                                            </div>
                                        </div>
                                        
                                        <button type="submit" class="btn primary-btn mt-3">
                                            <i class="fa fa-shopping-bag"></i> THÊM VÀO GIỎ
                                        </button>
                                        <a href="#" class="heart-icon"><i class="fa fa-heart"></i></a>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="product__details__price text-danger">Hết hàng hoặc chưa có biến thể</div>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                            
                        </div>
                    </div>
                    
                    <%-- MÔ TẢ SẢN PHẨM --%>
                    <div class="col-lg-12 mt-5">
                        <div class="product__details__tab">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active" data-bs-toggle="tab" href="#tabs-1" role="tab">Mô tả</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" data-bs-toggle="tab" href="#tabs-2" role="tab">Thông tin</a>
                                </li>
                            </ul>
                            <div class="tab-content border border-top-0 p-3 bg-white">
                                <div class="tab-pane active" id="tabs-1" role="tabpanel">
                                    <p>${product.des}</p>
                                </div>
                                <div class="tab-pane" id="tabs-2" role="tabpanel">
                                    <p>Thương hiệu: <b>${product.brand}</b></p>
                                    <p>Danh mục: <b>${product.cat_id.cat_name}</b></p>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
        </section>
        <section class="related-product pb-5">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <h2 class="mb-4">Sản phẩm liên quan</h2>
                    </div>
                </div>
                <div class="row g-4">
                    <c:forEach var="r" items="${requestScope.related}">
                        <c:if test="${r.pro_id != product.pro_id}">
                            <div class="col-lg-3 col-md-4 col-sm-6">
                                <div class="card h-100 shadow-sm text-center">
                                    <img src="${pageContext.request.contextPath}/${r.image_url}" class="card-img-top" alt="${r.pro_name}">
                                    <div class="card-body">
                                        <h6 class="card-title"><a href="${pageContext.request.contextPath}/productdetail?pro_id=${r.pro_id}" class="text-dark">${r.pro_name}</a></h6>
                                        <p class="text-danger">
                                            <c:if test="${r.min_price > 0}">
                                                Từ <fmt:formatNumber value="${r.min_price}" type="number" maxFractionDigits="0"/> VNĐ
                                            </c:if>
                                        </p>
                                        <a href="${pageContext.request.contextPath}/productdetail?pro_id=${r.pro_id}" class="btn btn-sm btn-outline-primary">Xem chi tiết</a>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </section>
        </c:if>
    <c:if test="${product == null}">
        <div class="container my-5">
            <div class="alert alert-danger" role="alert">
                Sản phẩm không tồn tại hoặc đã bị xóa.
            </div>
        </div>
    </c:if>

    <%-- <jsp:include page="footer.jsp" /> --%>

    <script src="${pageContext.request.contextPath}/bootstrap.bundle.min.js"></script>
    <script>
        // JavaScript để cập nhật giá khi chọn biến thể
        document.addEventListener('DOMContentLoaded', function() {
            const variantSelect = document.getElementById('variantSelect');
            const displayPrice = document.getElementById('displayPrice');
            
            if (variantSelect) {
                // Đảm bảo logic này khớp với logic trong Controller nếu bạn có JS phức tạp hơn
                variantSelect.addEventListener('change', function() {
                    const selectedOption = this.options[this.selectedIndex];
                    const price = selectedOption.getAttribute('data-price');
                    
                    // Định dạng giá tiền 
                    const formattedPrice = parseFloat(price).toLocaleString('vi-VN', { maximumFractionDigits: 0 }) + ' VNĐ';
                    
                    displayPrice.textContent = formattedPrice;
                });
            }
        });
    </script>
</body>
</html>