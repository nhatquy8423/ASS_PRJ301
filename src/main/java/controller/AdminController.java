/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.AdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AdminController", urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        // Lấy session hiện có (tránh tạo session mới nếu chưa có)
        HttpSession session = request.getSession(false); 
        
        // ---------------------------------------------------------------------
        // 1. XỬ LÝ ĐĂNG XUẤT (LOGOUT)
        // ---------------------------------------------------------------------
        if (action != null && action.equals("logout")) {
            // Chỉ xóa session hiện có
            if (session != null) {
                session.invalidate(); // Xóa tất cả các attribute và hủy session
            }
            // Chuyển hướng về trang đăng nhập Admin (là chính /admin)
            response.sendRedirect(request.getContextPath() + "/admin");
            return; // Dừng xử lý
        }
        
        // ---------------------------------------------------------------------
        // 2. XỬ LÝ HIỂN THỊ (DASHBOARD HOẶC LOGIN)
        // ---------------------------------------------------------------------
        // Kiểm tra xem Admin đã đăng nhập chưa
        boolean isLoggedIn = (session != null && session.getAttribute("admin") != null);

        if (isLoggedIn) {
            // Đã đăng nhập: Chuyển đến trang dashboard
            request.getRequestDispatcher("admin/dashboard.jsp").forward(request, response);
        } else {
            // Chưa đăng nhập: Hiển thị form đăng nhập Admin
            
            // Báo cho login.jsp biết đây là luồng Admin để form action đúng
            request.setAttribute("loginTarget", "admin");
            
            // Chuyển tiếp tới trang login.jsp
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Trong file controller/AdminController.java

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    // THAY ĐỔI: Thêm .trim() để loại bỏ khoảng trắng thừa
    String email = request.getParameter("email").trim();
    String pass = request.getParameter("password").trim();

    // Kiểm tra trong DB (Giả định AdminDAO.getAdmin được cập nhật để nhận email)
    AdminDAO adminDAO = new AdminDAO();
    // Thay đổi: Truyền email đã được trim()
    Admin admin = adminDAO.getAdmin(email, pass); 
    
    // ... (Phần xử lý kết quả giữ nguyên)
    
    if (admin != null) {
        // Đăng nhập thành công
        HttpSession session = request.getSession(true);
        session.setAttribute("admin", admin);

        // Chuyển đến trang dashboard 
        response.sendRedirect(request.getContextPath() + "/admin");
    } else {
        // Đăng nhập thất bại
        request.setAttribute("error", "Email hoặc mật khẩu không đúng!");

        // Báo lại cho login.jsp biết đây là login Admin
        request.setAttribute("loginTarget", "admin");
        request.setAttribute("loginTitle", "Admin Login");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles Admin Authentication and Dashboard access";
    }

}
