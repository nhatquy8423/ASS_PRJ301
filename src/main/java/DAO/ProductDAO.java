package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Product;
import utils.DBContext;

public class ProductDAO extends DBContext {

    
    private final String BASE_QUERY = "SELECT p.*, c.cat_name, c.cat_des " +
                                      "FROM Product p " +
                                      "LEFT JOIN Category c ON p.cat_id = c.cat_id ";

    /**
     * Helper function để tạo đối tượng Product từ ResultSet.
     * Tránh lặp lại code ở nhiều nơi.
     */
    private Product createProductFromResultSet(ResultSet rs) throws Exception {
        // 1. Tạo đối tượng Category trực tiếp từ kết quả JOIN
        Category category = new Category(
                rs.getInt("cat_id"),
                rs.getString("cat_name"),
                rs.getString("cat_des")
        );

        // 2. Tạo đối tượng Product và gán Category vào
        return new Product(
                rs.getInt("pro_id"),
                category,
                rs.getString("pro_name"),
                rs.getString("brand"),
                rs.getString("description"), 
                rs.getString("image_url")
        );
    }

    /**
     * Lấy tất cả sản phẩm.
     */
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = BASE_QUERY; // Sử dụng câu lệnh gốc
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createProductFromResultSet(rs));
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
        String sql = BASE_QUERY + "WHERE p.pro_id = ?"; // Thêm điều kiện WHERE
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pro_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createProductFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy sản phẩm theo danh mục.
     */
    public List<Product> getProductsByCategoryId(int cat_id) {
        List<Product> list = new ArrayList<>();
        String sql = BASE_QUERY + "WHERE p.cat_id = ?"; // Thêm điều kiện WHERE
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cat_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createProductFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Lấy các sản phẩm nổi bật (sản phẩm mới nhất).
     */
    public List<Product> getFeaturedProducts(int limit) {
        List<Product> list = new ArrayList<>();
        // Cần chỉnh lại câu lệnh TOP cho phù hợp với JOIN
        String sql = "SELECT TOP (?) p.*, c.cat_name, c.cat_des " +
                     "FROM Product p LEFT JOIN Category c ON p.cat_id = c.cat_id " +
                     "ORDER BY p.pro_id DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createProductFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Tìm kiếm sản phẩm theo tên.
     */
    public List<Product> searchProductsByName(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = BASE_QUERY + "WHERE p.pro_name LIKE ?"; // Thêm điều kiện WHERE
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createProductFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- CÁC HÀM CHO ADMIN (KHÔNG THAY ĐỔI) ---

    public void addProduct(Product p) {
        String sql = "INSERT INTO Product (cat_id, pro_name, brand, description, image_url) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getCat_id().getCat_id());
            ps.setString(2, p.getPro_name());
            ps.setString(3, p.getBrand());
            ps.setString(4, p.getDes()); // Lấy theo tên biến `des` trong model Product của bạn
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
}