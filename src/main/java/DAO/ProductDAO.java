package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Product;
import utils.DBContext;

public class ProductDAO extends DBContext {

    // Common Table Expression (CTE) để tính toán min_price (giá thấp nhất) cho mỗi sản phẩm
    private final String BASE_QUERY_CTE = 
        "WITH ProductWithMinPrice AS (" +
            "SELECT p.pro_id, p.pro_name, p.brand, p.description, p.image_url, " +
                   "p.cat_id, c.cat_name, c.cat_des, " +
                   "MIN(pv.price) AS min_price " +
            "FROM Product p " +
            "LEFT JOIN Category c ON p.cat_id = c.cat_id " +
            "LEFT JOIN ProductVariant pv ON p.pro_id = pv.pro_id " +
            "GROUP BY p.pro_id, p.pro_name, p.brand, p.description, p.image_url, p.cat_id, c.cat_name, c.cat_des" +
        ") ";

    /**
     * Helper function để tạo đối tượng Product từ ResultSet của CTE.
     */
    private Product createProductFromResultSetCTE(ResultSet rs) throws Exception {
        // 1. Tạo đối tượng Category
        Category category = new Category(
                rs.getInt("cat_id"),
                rs.getString("cat_name"),
                rs.getString("cat_des")
        );

        // 2. Tạo đối tượng Product cơ bản
        Product product = new Product(
                rs.getInt("pro_id"),
                category,
                rs.getString("pro_name"),
                rs.getString("brand"),
                rs.getString("description"), 
                rs.getString("image_url")
        );
        
        // 3. Lấy và gán min_price (được tính trong CTE)
        product.setMin_price(rs.getDouble("min_price")); 

        return product;
    }
    
    /**
     * Hàm phụ trợ tạo mệnh đề ORDER BY dựa trên tham số.
     */
    private String getOrderByClause(String sortOrder) {
        switch (sortOrder) {
            case "price_asc":
                return "ORDER BY min_price ASC";
            case "price_desc":
                return "ORDER BY min_price DESC";
            case "pro_id_desc":
            case "default":
            default:
                return "ORDER BY pro_id DESC"; // Mặc định theo ID mới nhất
        }
    }
    
    // ===============================================
    // CẬP NHẬT CÁC HÀM TRUY VẤN
    // ===============================================

    /**
     * Lấy tất cả sản phẩm với tùy chọn sắp xếp.
     */
    public List<Product> getAllProducts(String sortOrder) {
        List<Product> list = new ArrayList<>();
        String sql = BASE_QUERY_CTE + " SELECT * FROM ProductWithMinPrice " + getOrderByClause(sortOrder); 
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createProductFromResultSetCTE(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Lấy một sản phẩm bằng ID.
     */
    public Product getProductById(int pro_id) {
        String sql = BASE_QUERY_CTE + " SELECT * FROM ProductWithMinPrice WHERE pro_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pro_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createProductFromResultSetCTE(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy sản phẩm theo danh mục và sắp xếp. (Đã sửa lỗi)
     */
    public List<Product> getProductsByCategoryId(int cat_id, String sortOrder) {
        List<Product> list = new ArrayList<>();
        // Đã sửa lỗi: Hàm này nhận đủ 2 tham số và sử dụng chúng.
        String sql = BASE_QUERY_CTE + " SELECT * FROM ProductWithMinPrice WHERE cat_id = ? " + getOrderByClause(sortOrder);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cat_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createProductFromResultSetCTE(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // HÀM BỊ LỖI ĐÃ ĐƯỢC XÓA TẠI ĐÂY

    /**
     * Lấy sản phẩm nổi bật. (Không có sắp xếp tùy chỉnh, dùng mặc định ID DESC)
     */
    public List<Product> getFeaturedProducts(int limit) {
        List<Product> list = new ArrayList<>();
        // Sử dụng CTE và TOP
        String sql = "SELECT TOP (?) p.pro_id, p.pro_name, p.brand, p.description, p.image_url, " +
                     "p.cat_id, c.cat_name, c.cat_des, MIN(pv.price) AS min_price " +
                     "FROM Product p LEFT JOIN Category c ON p.cat_id = c.cat_id " +
                     "LEFT JOIN ProductVariant pv ON p.pro_id = pv.pro_id " +
                     "GROUP BY p.pro_id, p.pro_name, p.brand, p.description, p.image_url, p.cat_id, c.cat_name, c.cat_des " +
                     "ORDER BY p.pro_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createProductFromResultSetCTE(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Tìm kiếm sản phẩm theo tên và sắp xếp.
     */
    public List<Product> searchProductsByName(String keyword, String sortOrder) {
        List<Product> list = new ArrayList<>();
        String sql = BASE_QUERY_CTE + " SELECT * FROM ProductWithMinPrice WHERE pro_name LIKE ? " + getOrderByClause(sortOrder);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createProductFromResultSetCTE(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy các sản phẩm có giá nằm trong khoảng [minPrice, maxPrice] và sắp xếp.
     */
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice, String sortOrder) {
        List<Product> list = new ArrayList<>();
        
        String sql = BASE_QUERY_CTE + 
                     " SELECT * FROM ProductWithMinPrice WHERE min_price >= ? AND min_price <= ? " + 
                     getOrderByClause(sortOrder);

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createProductFromResultSetCTE(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // --- CÁC HÀM CHO ADMIN (Giữ nguyên) ---

    public void addProduct(Product p) {
        String sql = "INSERT INTO Product (cat_id, pro_name, brand, description, image_url) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getCat_id().getCat_id());
            ps.setString(2, p.getPro_name());
            ps.setString(3, p.getBrand());
            ps.setString(4, p.getDes());
            ps.setString(5, p.getImage_url());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product p) {
        String sql = "UPDATE Product SET cat_id = ?, pro_name = ?, brand = ?, description = ?, image_url = ? WHERE pro_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getCat_id().getCat_id());
            ps.setString(2, p.getPro_name());
            ps.setString(3, p.getBrand());
            ps.setString(4, p.getDes());
            ps.setString(5, p.getImage_url());
            ps.setInt(6, p.getPro_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteProduct(int pro_id) {
        String sql = "DELETE FROM Product WHERE pro_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pro_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   

    /**NhậtQuy_Admin
     * [Admin Dashboard] Đếm tổng số sản phẩm có trong CSDL.
     */
    public int getTotalProductCount() {
        String sql = "SELECT COUNT(pro_id) AS ProductCount FROM Product";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ProductCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}