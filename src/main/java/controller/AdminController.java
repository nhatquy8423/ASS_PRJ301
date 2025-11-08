/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.AdminDAO;
import DAO.OrderDAO;
import DAO.ProductDAO;
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
     * SỬA LỖI QUAN TRỌNG NHẤT NẰM Ở ĐÂY * Phải set "loginTarget" = "admin" để
     * login.jsp biết cần POST form về /admin
     */
    // Trong file: AdminController.java

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); 
        Admin admin = (session != null) ? (Admin) session.getAttribute("admin") : null;
        String action = request.getParameter("action");
        
        // 1. Xử lý LOGOUT
        if (action != null && action.equals("logout")) {
            if (session != null) {
                session.invalidate(); // Xóa session
            }
            response.sendRedirect(request.getContextPath() + "/admin"); // Quay lại trang đăng nhập
            return;
        }

        // 2. Xử lý HIỂN THỊ TRANG
        if (admin != null) {
            // Đã đăng nhập: LẤY DỮ LIỆU VÀ CHUYỂN ĐẾN DASHBOARD
            
            // Khởi tạo DAO
            OrderDAO orderDAO = new OrderDAO();
            ProductDAO productDAO = new ProductDAO();
            
            // Gọi các hàm thống kê
            double totalRevenue = orderDAO.getTotalRevenue();
            int newOrderCount = orderDAO.getOrderCountByStatus("Pending"); // "Pending" là đơn hàng mới
            int totalProducts = productDAO.getTotalProductCount();
            
            // Đặt thuộc tính (attributes) để gửi sang JSP
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("newOrderCount", newOrderCount);
            request.setAttribute("totalProducts", totalProducts);
            
            // Forward đến trang dashboard.jsp
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
            
        } else {
            // Chưa đăng nhập: Hiển thị form đăng nhập (login.jsp)
            request.setAttribute("loginTarget", "admin");
            request.setAttribute("loginTitle", "Admin Login");
            request.getRequestDispatcher("loginAdmin.jsp").forward(request, response);
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
            request.setAttribute("savedEmail", email);
            request.getRequestDispatcher("loginAdmin.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles Admin Authentication and Dashboard access";
    }
}
