/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package controller;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter bảo vệ các trang yêu cầu đăng nhập (xem sản phẩm, giỏ hàng, đặt hàng).
 */
@WebFilter(urlPatterns = {
    "/product",         // <--- THÊM: Bảo vệ trang danh sách sản phẩm
    "/productdetail",   // <--- THÊM: Bảo vệ trang chi tiết sản phẩm
    "/cart", 
    "/order", 
    "/checkout", 
    "/addToCart", 
    "/updateCart",
    "/order-history"
})
public class CustomerAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); 

        // Kiểm tra xem session có tồn tại VÀ có attribute "customer" không
        boolean isLoggedIn = (session != null && session.getAttribute("customer") != null);

        if (isLoggedIn) {
            // Nếu đã đăng nhập, cho phép đi tiếp
            chain.doFilter(request, response);
        } else {
            // Nếu CHƯA đăng nhập: Lưu URL đích và chuyển hướng đến trang đăng nhập
            
            // Lưu URL đích (ví dụ: /productdetail?id=123)
            String queryString = req.getQueryString() != null ? "?" + req.getQueryString() : "";
            String targetURL = req.getRequestURI().substring(req.getContextPath().length()) + queryString;
            
            // Tạo session (nếu chưa có) để lưu targetURL
            if (session == null) {
                session = req.getSession(true);
            }
            session.setAttribute("targetURL", targetURL);
            
            // Chuyển hướng đến AuthController (hiển thị login.jsp)
            res.sendRedirect(req.getContextPath() + "/auth"); 
        }
    }
    

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // (Không cần code)
    }

    @Override
    public void destroy() {
        // (Không cần code)
    }
}