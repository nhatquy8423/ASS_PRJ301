package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import utils.DBContext;

public class CategoryDAO extends DBContext {

    /**
     * Lấy tất cả danh mục.
     * @return Danh sách các đối tượng Category.
     */
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("cat_id"),
                        rs.getString("cat_name"),
                        rs.getString("cat_des")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy một danh mục bằng ID.
     * @param id ID của danh mục cần lấy.
     * @return Đối tượng Category hoặc null nếu không tìm thấy.
     */
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM Category WHERE cat_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Category(
                        rs.getInt("cat_id"),
                        rs.getString("cat_name"),
                        rs.getString("cat_des"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}