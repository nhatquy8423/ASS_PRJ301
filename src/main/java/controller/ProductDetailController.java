/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.ProductDAO;
import DAO.ProductVariantDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Product;
import model.ProductVariant;

/**
 * Controller xử lý hiển thị chi tiết một sản phẩm.
 * URL: /productdetail?pro_id=X
 */
@WebServlet(name = "ProductDetailController", urlPatterns = {"/productdetail"})
public class ProductDetailController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Không sử dụng processRequest mặc định
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    /**
     * Handles the HTTP <code>GET</code> method.
     * Xử lý hiển thị chi tiết sản phẩm dựa trên pro_id.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy pro_id từ query parameter
        String proId_raw = request.getParameter("pro_id");

        ProductDAO productDAO = new ProductDAO();
        ProductVariantDAO variantDAO = new ProductVariantDAO();

        try {
            // Lấy pro_id và parse
            int pro_id = Integer.parseInt(proId_raw);

            // Lấy sản phẩm chính (ProductDAO đã được sửa để lấy min_price)
            Product product = productDAO.getProductById(pro_id);

            if (product != null) {
                // Nếu sản phẩm tồn tại, lấy các biến thể (variants)
                List<ProductVariant> variants = variantDAO.getVariantsByProductId(pro_id);

                // Lấy các sản phẩm liên quan (cùng category)
                int cat_id = product.getCat_id().getCat_id();
                // Lấy các sản phẩm cùng loại (chúng ta sẽ lọc bỏ sản phẩm hiện tại trong JSP)
                // Ta dùng hàm cũ không cần sorting cho Related Products
                List<Product> relatedProducts = productDAO.getProductsByCategoryId(cat_id, "default"); 

                // Gửi dữ liệu sang JSP
                request.setAttribute("product", product);
                request.setAttribute("variants", variants);
                request.setAttribute("related", relatedProducts);

                request.getRequestDispatcher("productDetail.jsp").forward(request, response);
            } else {
                // Nếu không tìm thấy sản phẩm
                request.getSession().setAttribute("errorMsg", "Không tìm thấy sản phẩm với ID: " + proId_raw);
                response.sendRedirect(request.getContextPath() + "/product");
            }

        } catch (NumberFormatException e) {
            // Nếu pro_id không phải là số (hoặc null)
            request.getSession().setAttribute("errorMsg", "ID sản phẩm không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/product");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Chuyển tiếp yêu cầu thêm vào giỏ hàng đến CartController.
     * Logic chi tiết được xử lý trong CartController.doPost
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển tiếp yêu cầu POST này (Thêm vào giỏ hàng) đến CartController
        request.getRequestDispatcher("/cart").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller for displaying product details and related info.";
    }// </editor-fold>
}