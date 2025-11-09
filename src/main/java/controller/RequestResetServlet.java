/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CustomerDAO;
import DAO.EmailUtil;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;

/**
 *
 * @author Trien
 */
@WebServlet(name = "RequestResetServlet", urlPatterns = {"/request-reset"})

public class RequestResetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        CustomerDAO dao = new CustomerDAO();
        Customer customer = dao.getCustomerByEmail(email);

        if (customer == null) {
            req.setAttribute("message", "Email không tồn tại!!");
            req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
            return;
        }

// Tạo OTP 6 chữ số
        String otp = String.format("%06d", new java.util.Random().nextInt(1000000));

// Thời hạn OTP (5 phút)
        java.util.Date expiresAt = new java.util.Date(System.currentTimeMillis() + 5 * 60 * 1000);

// Lưu OTP và thời hạn vào DB
        dao.updateOTP(customer.getCus_id(), otp, expiresAt);

        boolean emailSent = EmailUtil.sendOTP(customer.getEmail(), otp);

        if (!emailSent) {
            req.setAttribute("message", "Gửi OTP thất bại. Vui lòng thử lại.");
            req.getRequestDispatcher("/forgot-password.jsp").forward(req, resp);
            return;
        }
        req.getSession().setAttribute("emailValue",email);
        req.setAttribute("message", "Nếu email tồn tại, chúng tôi đã gửi mã OTP.");
        req.getRequestDispatcher("/enter-otp.jsp").forward(req, resp);

    }
}
