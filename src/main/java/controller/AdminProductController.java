package controller;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Product;

@WebServlet(name = "AdminProductController", urlPatterns = {"/admin/product"})
public class AdminProductController extends HttpServlet {

    // Khởi tạo DAO
    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        // 1. XỬ LÝ ACTION XÓA (DELETE)
        if (action != null && action.equals("delete")) {
            try {
                int pro_id = Integer.parseInt(request.getParameter("pro_id"));
                // Gọi hàm deleteProduct từ DAO
                productDAO.deleteProduct(pro_id); 
                request.getSession().setAttribute("successMsg", "Đã xóa sản phẩm thành công!");
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("errorMsg", "Lỗi khi xóa sản phẩm: ID không hợp lệ.");
            }
            response.sendRedirect(request.getContextPath() + "/admin/product");
            return; // Dừng lại sau khi redirect
        }

        // 2. XỬ LÝ ACTION SỬA (EDIT) - GỬI DỮ LIỆU LÊN FORM
        if (action != null && action.equals("edit")) {
            try {
                int pro_id = Integer.parseInt(request.getParameter("pro_id"));
                // Gọi hàm getProductById từ DAO
                Product productToEdit = productDAO.getProductById(pro_id); 
                if (productToEdit != null) {
                    request.setAttribute("productToEdit", productToEdit);
                }
            } catch (NumberFormatException e) {
                 request.setAttribute("errorMsg", "Không tìm thấy sản phẩm để sửa.");
            }
        }
        
        // 3. (MẶC ĐỊNH) LUÔN LẤY DANH SÁCH ĐỂ HIỂN THỊ
        // Gọi hàm getAllProducts từ DAO
        List<Product> productList = productDAO.getAllProducts("default"); 
        // Gọi hàm getAllCategories từ DAO
        List<Category> categoryList = categoryDAO.getAllCategories(); 

        request.setAttribute("productList", productList);
        request.setAttribute("categoryList", categoryList);
        
        request.getRequestDispatcher("/admin/manageProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8"); // Xử lý tiếng Việt
        
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/product");
            return;
        }
        
        try {
            // 1. Lấy dữ liệu chung từ form
            String pro_name = request.getParameter("pro_name");
            String brand = request.getParameter("brand");
            String des = request.getParameter("des");
            String image_url = request.getParameter("image_url");
            int cat_id = Integer.parseInt(request.getParameter("cat_id"));

            // 2. Lấy đối tượng Category từ cat_id (vì Product Model cần)
            //
            Category category = categoryDAO.getCategoryById(cat_id); 

            if (category == null) {
                request.getSession().setAttribute("errorMsg", "Lỗi: Danh mục không tồn tại.");
                response.sendRedirect(request.getContextPath() + "/admin/product");
                return;
            }

            // 3. Xử lý Thêm (ADD) hoặc Cập nhật (UPDATE)
            if (action.equals("add")) {
                // Tạo Product mới và gọi DAO
                Product newProduct = new Product(0, category, pro_name, brand, des, image_url);
                productDAO.addProduct(newProduct); //
                request.getSession().setAttribute("successMsg", "Đã thêm sản phẩm mới thành công!");

            } else if (action.equals("update")) {
                // Lấy Pro_ID và gọi DAO
                int pro_id = Integer.parseInt(request.getParameter("pro_id"));
                Product updatedProduct = new Product(pro_id, category, pro_name, brand, des, image_url);
                productDAO.updateProduct(updatedProduct); //
                request.getSession().setAttribute("successMsg", "Đã cập nhật sản phẩm thành công!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMsg", "Đã xảy ra lỗi. Vui lòng thử lại.");
        }
        
        // 4. Quay lại trang quản lý
        response.sendRedirect(request.getContextPath() + "/admin/product");
    }
}