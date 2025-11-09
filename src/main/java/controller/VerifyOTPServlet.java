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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;

/**
 *
 * @author Trien
 */
@WebServlet(name = "VerifyOTPServlet", urlPatterns = {"/verify-otp"})
public class VerifyOTPServlet extends HttpServlet {

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
            out.println("<title>Servlet VerifyOTPServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VerifyOTPServlet at " + request.getContextPath() + "</h1>");
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
       request.getRequestDispatcher("enter-otp.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String enteredOtp = req.getParameter("otp");

        CustomerDAO dao = new CustomerDAO();
        Customer customer = dao.getCustomerByEmail(email);

        if (customer == null || customer.getOtp_code() == null) {
            req.setAttribute("error", "Mã OTP không hợp lệ.");
            req.getRequestDispatcher("/enter-otp.jsp").forward(req, resp);
            return;
        }

        java.util.Date now = new java.util.Date();

        if (!enteredOtp.equals(customer.getOtp_code())) {
            dao.incrementOtpAttempts(customer.getCus_id());
            req.setAttribute("error", "Mã OTP không đúng.");
            req.getRequestDispatcher("/enter-otp.jsp").forward(req, resp);
            return;
        }

        if (now.after(customer.getOtp_expires_at())) {
            req.setAttribute("error", "Mã OTP đã hết hạn.");
            req.getRequestDispatcher("/enter-otp.jsp").forward(req, resp);
            return;
        }

        req.getSession().setAttribute("resetCustomerId", customer.getCus_id());
        resp.sendRedirect(req.getContextPath() + "/reset-password.jsp");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
