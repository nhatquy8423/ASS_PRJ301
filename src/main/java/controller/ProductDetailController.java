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
 */
@WebServlet(name = "ProductDetailController", urlPatterns = {"/productdetail"})
public class ProductDetailController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

            // Lấy sản phẩm chính
            Product product = productDAO.getProductById(pro_id);

            if (product != null) {
                // Nếu sản phẩm tồn tại, lấy các biến thể (variants)
                List<ProductVariant> variants = variantDAO.getVariantsByProductId(pro_id);

                // Lấy các sản phẩm liên quan (cùng category), giới hạn ví dụ 4 sản phẩm
                // Giả định .getCat_id() không bao giờ null nếu product tồn tại
                int cat_id = product.getCat_id().getCat_id();
                // Lấy tất cả sản phẩm cùng loại, sau đó bạn có thể lọc bớt sản phẩm hiện tại trong JSP
                List<Product> relatedProducts = productDAO.getProductsByCategoryId(cat_id); 

                // Gửi dữ liệu sang JSP
                request.setAttribute("product", product);
                request.setAttribute("variants", variants);
                request.setAttribute("related", relatedProducts);

                request.getRequestDispatcher("product-detail.jsp").forward(request, response);
            } else {
                // Nếu không tìm thấy sản phẩm
                request.getSession().setAttribute("errorMsg", "Không tìm thấy sản phẩm với ID: " + proId_raw);
                // Chuyển về trang sản phẩm chung
                response.sendRedirect(request.getContextPath() + "/product");
            }

        } catch (NumberFormatException e) {
            // Nếu pro_id không phải là số (hoặc null)
            request.getSession().setAttribute("errorMsg", "ID sản phẩm không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/product"); // Chuyển về trang sản phẩm chung
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * POST ở đây nên được dùng để THÊM SẢN PHẨM VÀO GIỎ HÀNG (Sử dụng CartController)
     * Tuy nhiên, theo luồng hiện tại, tôi giữ logic POST đơn giản là chuyển hướng.
     * **Lưu ý:** Nếu bạn muốn thêm vào giỏ hàng từ đây, bạn nên chuyển hướng hoặc forward
     * request này đến CartController.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Giả định: Người dùng nhấn nút "Thêm vào giỏ hàng"
        // Chuyển yêu cầu này sang CartController để xử lý
        request.getRequestDispatcher("/cart").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller for displaying product details and related info.";
    }// </editor-fold>
}