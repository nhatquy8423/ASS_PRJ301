/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Product;

/**
 * Controller xử lý hiển thị danh sách sản phẩm, lọc theo danh mục và tìm kiếm.
 */
@WebServlet(name = "ProductController", urlPatterns = {"/product"})
public class ProductController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không sử dụng processRequest mặc định
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Đặt UTF-8 cho request để xử lý tiếng Việt cho tìm kiếm
        request.setCharacterEncoding("UTF-8");
        
        ProductDAO productDAO = new ProductDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        // Luôn lấy danh sách danh mục để hiển thị sidebar
        List<Category> listCategories = categoryDAO.getAllCategories();
        request.setAttribute("categories", listCategories);

        String action = request.getParameter("action");
        List<Product> listProducts;

        if (action != null) {
            switch (action) {
                case "filter":
                    // Xử lý lọc theo category
                    String catId_raw = request.getParameter("cat_id");
                    try {
                        int cat_id = Integer.parseInt(catId_raw);
                        listProducts = productDAO.getProductsByCategoryId(cat_id);
                        // Đánh dấu category đang active
                        request.setAttribute("active_cat_id", cat_id); 
                    } catch (NumberFormatException e) {
                        // Nếu cat_id không hợp lệ, tải tất cả sản phẩm
                        listProducts = productDAO.getAllProducts();
                    }
                    break;
                case "search":
                    // Xử lý tìm kiếm
                    String keyword = request.getParameter("keyword");
                    // Kiểm tra null và trim
                    if (keyword == null || keyword.trim().isEmpty()) {
                        listProducts = productDAO.getAllProducts();
                    } else {
                        listProducts = productDAO.searchProductsByName(keyword.trim());
                        // Gửi lại keyword về view để hiển thị trong ô search
                        request.setAttribute("keyword", keyword); 
                    }
                    break;
                default:
                    // Mặc định (nếu action không hợp lệ)
                    listProducts = productDAO.getAllProducts();
                    break;
            }
        } else {
            // Mặc định (nếu không có action)
            listProducts = productDAO.getAllProducts();
        }

        // Gửi danh sách sản phẩm (đã lọc hoặc tìm kiếm) sang view
        request.setAttribute("products", listProducts);

        // Chuyển tiếp đến trang JSP
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hầu hết các trang sản phẩm chỉ dùng GET
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller for displaying product list, filtering, and searching.";
    }// </editor-fold>
}