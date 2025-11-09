/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CustomerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import model.Customer;

/**
 * Servlet xử lý việc đăng nhập/đăng ký cho Customer. URL: /auth
 */
@WebServlet(name = "AuthController", urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {

    /**
     * Xử lý HTTP GET: Hiển thị trang đăng nhập (login.jsp)
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String savedEmail = "";
        boolean rememberChecked = false;

        // Đọc cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("userEmail".equals(c.getName())) {
                    savedEmail = c.getValue();
                    if (!savedEmail.isEmpty()) {
                        rememberChecked = true; //có email → tick remember
                    }
                    break;
                }
            }
        }

        // Gửi dữ liệu sang JSP
        request.setAttribute("savedEmail", savedEmail);
        request.setAttribute("rememberChecked", rememberChecked);

        // Chuyển tiếp đến login.jsp
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Xử lý HTTP POST: Xác thực đăng nhập Customer.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CustomerDAO cusDAO = new CustomerDAO();
        HttpServletRequest req = request;

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        // 1. Xác thực Customer
        Customer c = cusDAO.getCustomer(email, password);

        if (c == null) {
            // Đăng nhập thất bại

            // (Giữ lại thông tin đã nhập để điền lại vào form)
            request.setAttribute("savedEmail", email);

            // Đặt thông báo lỗi
            request.setAttribute("errorMessage", "Sai email hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } else {
            // 2. ĐĂNG NHẬP THÀNH CÔNG
            HttpSession session = req.getSession();
            session.setAttribute("customer", c); // Lưu đối tượng Customer vào session
            session.setMaxInactiveInterval(30 * 60); // Thời gian sống session
            session.setAttribute("loginSuccess", "Bạn đã đăng nhập thành công");

            // 3. Xử lý Cookie Remember Me (giữ nguyên logic đã có, chỉ lưu email)
            if ("on".equals(remember)) {
                Cookie emailCookie = new Cookie("userEmail", email);
                emailCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
                emailCookie.setPath("/");
                response.addCookie(emailCookie);
            } else {
                Cookie emailCookie = new Cookie("userEmail", "");
                emailCookie.setMaxAge(0);
                emailCookie.setPath("/");
                response.addCookie(emailCookie);
            }

            // 4. LOGIC CHUYỂN HƯỚNG TỚI targetURL (MỚI)
            String targetURL = (String) session.getAttribute("targetURL");

            if (targetURL != null && !targetURL.isEmpty()) {
                // Nếu có targetURL (bị Filter chặn trước đó), chuyển hướng tới trang đích
                session.removeAttribute("targetURL");
                response.sendRedirect(req.getContextPath() + targetURL);
            } else {
                // Nếu không có targetURL (người dùng tự vào /auth), chuyển hướng về home.jsp
                response.sendRedirect("product");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Handles Customer Authentication (Login/Register)";
    }
}
