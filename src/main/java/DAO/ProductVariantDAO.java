package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Product;
import model.ProductVariant;
import utils.DBContext;

public class ProductVariantDAO extends DBContext {

    /**
     * Hàm helper để tạo một đối tượng ProductVariant đầy đủ từ ResultSet.
     * Hàm này đọc dữ liệu từ một hàng kết quả đã được JOIN từ 3 bảng
     * (ProductVariant, Product, Category) để xây dựng các đối tượng lồng nhau.
     *
     * @param rs ResultSet hiện tại.
     * @return một đối tượng ProductVariant hoàn chỉnh.
     * @throws Exception
     */
    private ProductVariant createVariantFromResultSet(ResultSet rs) throws Exception {
        // 1. Tạo đối tượng Category từ các cột của bảng Category
        Category category = new Category(
                rs.getInt("cat_id"),
                rs.getString("cat_name"),
                rs.getString("cat_des")
        );

        // 2. Tạo đối tượng Product, sử dụng đối tượng Category ở trên
        // Lưu ý: Lấy từ cột "description" trong DB và gán vào biến "des" của model
        Product product = new Product(
                rs.getInt("pro_id"),
                category,
                rs.getString("pro_name"),
                rs.getString("brand"),
                rs.getString("description"), // Cột DB là "description"
                rs.getString("image_url")
        );

        // 3. Tạo đối tượng ProductVariant, sử dụng đối tượng Product ở trên
        // (Đã cập nhật theo code của bạn)
        return new ProductVariant(
                rs.getInt("variant_id"),
                product,
                rs.getString("volume"),
                rs.getDouble("price"), // Model của bạn dùng double
                rs.getInt("quantity")  // <-- ĐÃ THÊM TỒN KHO
        );
    }

    /**
     * Lấy tất cả các biến thể của một sản phẩm bằng pro_id.
     *
     * @param pro_id ID của sản phẩm.
     * @return Danh sách các biến thể của sản phẩm đó.
     */
    public List<ProductVariant> getVariantsByProductId(int pro_id) {
        List<ProductVariant> list = new ArrayList<>();
        // Câu SQL "pv.*" đã bao gồm cột "quantity"
        String sql = "SELECT pv.*, p.*, c.cat_name, c.cat_des " +
                     "FROM ProductVariant pv " +
                     "LEFT JOIN Product p ON pv.pro_id = p.pro_id " +
                     "LEFT JOIN Category c ON p.cat_id = c.cat_id " +
                     "WHERE pv.pro_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pro_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createVariantFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy một biến thể cụ thể bằng variant_id của nó.
     *
     * @param variant_id ID của biến thể.
     * @return Đối tượng ProductVariant hoàn chỉnh hoặc null nếu không tìm thấy.
     */
    public ProductVariant getVariantById(int variant_id) {
        // Câu SQL "pv.*" đã bao gồm cột "quantity"
        String sql = "SELECT pv.*, p.*, c.cat_name, c.cat_des " +
                     "FROM ProductVariant pv " +
                     "LEFT JOIN Product p ON pv.pro_id = p.pro_id " +
                     "LEFT JOIN Category c ON p.cat_id = c.cat_id " +
                     "WHERE pv.variant_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, variant_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createVariantFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}