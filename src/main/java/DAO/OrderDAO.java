package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Orders;
import utils.DBContext;

public class OrderDAO extends DBContext {

    // --- Chức năng cho Admin ---
    /**
     * [Admin] Lấy tất cả đơn hàng trong hệ thống để quản lý. Sắp xếp theo ngày
     * mới nhất để dễ theo dõi.
     */
    public List<Orders> getAllOrders() {
        List<Orders> list = new ArrayList<>();
        // Dùng JOIN để lấy luôn tên khách hàng, tiện cho việc hiển thị
        String sql = "SELECT o.*, c.full_name, c.email "
                + "FROM Orders o LEFT JOIN Customer c ON o.cus_id = c.cus_id "
                + "ORDER BY o.order_date DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            CustomerDAO customerDAO = new CustomerDAO(); // Dùng để lấy đối tượng Customer hoàn chỉnh
            while (rs.next()) {
                Customer c = customerDAO.getCustomerById(rs.getInt("cus_id"));
                Orders order = new Orders(
                        rs.getInt("order_id"),
                        c,
                        rs.getString("order_date"),
                        rs.getDouble("total"),
                        rs.getString("status")
                );
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * [Admin] Cập nhật trạng thái của một đơn hàng (Đang xử lý -> Đã giao ->
     * Hủy).
     *
     * @param orderId ID của đơn hàng cần cập nhật.
     * @param status Trạng thái mới.
     */
    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Chức năng cho Người dùng (Quy trình thanh toán) ---
    /**
     * [User] Tạo một đơn hàng mới trong bảng Orders. Hàm này sẽ trả về ID của
     * đơn hàng vừa được tạo để dùng cho OrderDetail.
     *
     * @param order Đối tượng Orders chứa thông tin (cus_id, total, status).
     * @return ID của đơn hàng vừa tạo, hoặc -1 nếu có lỗi.
     */
    public int createOrder(Orders order) {
        // Lấy về ID của bản ghi vừa được chèn
        String sql = "INSERT INTO Orders (cus_id, total, status, order_date) VALUES (?, ?, ?, GETDATE())";
        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getCus_id().getCus_id());
            ps.setDouble(2, order.getTotal());
            ps.setString(3, order.getStatus());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Trả về order_id
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu thất bại
    }
    
    
    
    
    // Trong file: OrderDAO.java

    /**Nhật Quy_Admin
     * [Admin Dashboard] Tính tổng doanh thu từ các đơn hàng đã giao thành công.
     */
    public double getTotalRevenue() {
        String sql = "SELECT SUM(total) AS TotalRevenue FROM Orders WHERE status = 'Delivered'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("TotalRevenue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu không có doanh thu
    }

    /**
     * [Admin Dashboard] Đếm số lượng đơn hàng theo một trạng thái cụ thể.
     *
     * @param status Trạng thái cần đếm (ví dụ: "Pending", "Delivered")
     */
    public int getOrderCountByStatus(String status) {
        String sql = "SELECT COUNT(order_id) AS OrderCount FROM Orders WHERE status = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("OrderCount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu không có
    }
}
