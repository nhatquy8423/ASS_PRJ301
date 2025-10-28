/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.AdminDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;

@WebServlet(name = "AdminController", urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {

    /**
     * SỬA LỖI QUAN TRỌNG NHẤT NẰM Ở ĐÂY
     * * Phải set "loginTarget" = "admin" để login.jsp
     * biết cần POST form về /admin
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); 
        String action = request.getParameter("action");

        // Xử lý LOGOUT
        if (action != null && action.equals("logout")) {
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }

        // Kiểm tra đã đăng nhập chưa
        if (session != null && session.getAttribute("admin") != null) {
            // Nếu đã đăng nhập, chuyển đến trang quản lý (ví dụ: /admin/order)
            response.sendRedirect(request.getContextPath() + "/admin/order");
        } else {
            // Nếu CHƯA đăng nhập, hiển thị form login
            
            // **BẮT BUỘC PHẢI CÓ DÒNG NÀY**
            request.setAttribute("loginTarget", "admin");
            request.setAttribute("loginTitle", "Admin Login");

            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * Xử lý POST (logic .trim() và session đã sửa)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Thêm .trim()
        String email = request.getParameter("email").trim();
        String pass = request.getParameter("password").trim();

        AdminDAO adminDAO = new AdminDAO();
        Admin admin = adminDAO.login(email, pass);
        
        if (admin != null && admin.getAdmin_id() > 0) { 
            // Đăng nhập thành công
            HttpSession session = request.getSession(true);
            session.setAttribute("admin", admin); // Lưu ĐỐI TƯỢNG admin
            response.sendRedirect(request.getContextPath() + "/admin"); // Quay lại doGet
        } else {
            // Đăng nhập thất bại
            request.setAttribute("error", "Email hoặc mật khẩu không đúng!");
            request.setAttribute("loginTarget", "admin");
            request.setAttribute("loginTitle", "Admin Login");
            request.setAttribute("savedEmail", email);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles Admin Authentication and Dashboard access";
    }
}