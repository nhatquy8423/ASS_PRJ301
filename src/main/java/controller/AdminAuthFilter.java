/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter; // Import
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter này CHỈ chặn các yêu cầu đến các trang NỘI BỘ của admin
 * (KHÔNG BAO GỒM trang đăng nhập /admin)
 */
// SỬA Ở ĐÂY: Thay "/admin/*" bằng URL cụ thể cần bảo vệ
@WebFilter(urlPatterns = {"/admin/order"}) 
public class AdminAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); 

        boolean isLoggedIn = (session != null && session.getAttribute("admin") != null);

        if (isLoggedIn) {
            // Nếu đã đăng nhập, cho phép đi tiếp (vào /admin/order)
            chain.doFilter(request, response);
        } else {
            // Nếu CHƯA đăng nhập, chuyển hướng về trang đăng nhập Admin
            // (Chính là AdminController tại URL /admin)
            System.out.println("AdminAuthFilter: Access denied. Redirecting to admin login page.");
            res.sendRedirect(req.getContextPath() + "/admin");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // (Không cần code ở đây)
    }

    @Override
    public void destroy() {
        // (Không cần code ở đây)
    }
}