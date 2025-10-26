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
 * Controller xử lý hiển thị danh sách sản phẩm, lọc theo danh mục, tìm kiếm, lọc giá và SẮP XẾP.
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

        // 1. Lấy tham số sắp xếp (Nếu không có, mặc định là 'default')
        String sortOrder = request.getParameter("sort");
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "default";
        }
        request.setAttribute("sort", sortOrder); // Gửi lại giá trị sắp xếp cho JSP

        // Luôn lấy danh sách danh mục để hiển thị sidebar
        List<Category> listCategories = categoryDAO.getAllCategories();
        request.setAttribute("categories", listCategories);

        String action = request.getParameter("action");
        List<Product> listProducts;

        if (action != null) {
            switch (action) {
                case "filter":
                    // Xử lý lọc theo category VÀ SẮP XẾP
                    String catId_raw = request.getParameter("cat_id");
                    try {
                        int cat_id = Integer.parseInt(catId_raw);
                        // Kêu gọi DAO với tham số sắp xếp
                        listProducts = productDAO.getProductsByCategoryId(cat_id, sortOrder);
                        // Đánh dấu category đang active
                        request.setAttribute("active_cat_id", cat_id);
                    } catch (NumberFormatException e) {
                        // Nếu cat_id không hợp lệ, tải tất cả sản phẩm
                        listProducts = productDAO.getAllProducts(sortOrder);
                    }
                    break;
                case "search":
                    // Xử lý tìm kiếm VÀ SẮP XẾP
                    String keyword = request.getParameter("keyword");
                    // Kiểm tra null và trim
                    if (keyword == null || keyword.trim().isEmpty()) {
                        listProducts = productDAO.getAllProducts(sortOrder);
                    } else {
                        // Kêu gọi DAO với tham số sắp xếp
                        listProducts = productDAO.searchProductsByName(keyword.trim(), sortOrder);
                        // Gửi lại keyword về view để hiển thị trong ô search
                        request.setAttribute("keyword", keyword);
                    }
                    break;
                case "price_filter":
                    // Xử lý LỌC THEO KHOẢNG GIÁ VÀ SẮP XẾP
                    String minPrice_raw = request.getParameter("min_price");
                    String maxPrice_raw = request.getParameter("max_price");

                    try {
                        double minPrice = Double.parseDouble(minPrice_raw);
                        double maxPrice = Double.parseDouble(maxPrice_raw);

                        // Kêu gọi DAO với tham số sắp xếp
                        listProducts = productDAO.getProductsByPriceRange(minPrice, maxPrice, sortOrder);

                        // Truyền lại giá trị đã lọc
                        request.setAttribute("min_price", minPrice);
                        request.setAttribute("max_price", maxPrice);

                    } catch (NumberFormatException e) {
                        listProducts = productDAO.getAllProducts(sortOrder);
                        request.getSession().setAttribute("errorMsg", "Giá trị lọc không hợp lệ.");
                    }
                    break;
                default:
                    // Mặc định (nếu action không hợp lệ) VÀ SẮP XẾP
                    listProducts = productDAO.getAllProducts(sortOrder);
                    break;
            }
        } else {
            // Mặc định (nếu không có action) VÀ SẮP XẾP
            listProducts = productDAO.getAllProducts(sortOrder);
        }

        // Gửi danh sách sản phẩm (đã lọc hoặc tìm kiếm) sang view
        request.setAttribute("products", listProducts);

        // Chuyển tiếp đến trang JSP
        request.getRequestDispatcher("product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển POST sang GET
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller for displaying product list, filtering, and searching.";
    }// </editor-fold>
}