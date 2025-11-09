package DAO;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import model.Customer;
import utils.DBContext;

public class CustomerDAO extends DBContext {

    /**
     * Kiểm tra thông tin đăng nhập của khách hàng.
     *
     * @param email Email người dùng nhập.
     * @param password Mật khẩu người dùng nhập.
     * @return Trả về đối tượng Customer nếu thành công, ngược lại trả về null.
     */
    public Customer getCustomer(String email, String password) {
        // Tên cột trong DB là 'password', trong model là 'pass'
        String sql = "SELECT * FROM Customer WHERE email = ? AND password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, hashMD5(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("cus_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"), // Lấy từ cột 'password'
                        rs.getString("phone"),
                        rs.getString("address")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String hashMD5(String pass) {
        String hashPass = "";
        try {
            MessageDigest ms = MessageDigest.getInstance("MD5");
            byte[] bytePass = ms.digest(pass.getBytes());
            //[0x1a, 0x09, 0x1b, 0xa, 0x77,...]
            for (byte bytePas : bytePass) {
                //0x1a, 0x09, 0x1b, 0xa
                String ch = String.format("%02x", bytePas);
                //1a, 09, 1b, 0a
                hashPass += ch;
            }
        } catch (Exception e) {
        }
        return hashPass;
    }

    /**
     * Kiểm tra xem một email đã tồn tại trong hệ thống chưa.
     *
     * @param email Email cần kiểm tra.
     * @return Trả về đối tượng Customer nếu email đã tồn tại, ngược lại null.
     */
    public Customer checkEmailExists(String email) {
        String sql = "SELECT * FROM Customer WHERE email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("cus_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer checkPhoneExists(String numberPhone) {
        String sql = "Select * from customer WHERE phone = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, numberPhone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("cus_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("address"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tạo một tài khoản khách hàng mới (đăng ký).
     *
     * @param customer Đối tượng Customer chứa thông tin đăng ký.
     */
//    public void createCustomer(String fullName, String email, String password, String phone, String address) {
//        String sql = "INSERT INTO Customer (full_name, email, password, phone, address) \n"
//                + "VALUES (N'?', '?', ?, ?,N'?')";
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, customer.getFullname());
//            ps.setString(2, customer.getEmail());
//            ps.setString(3, customer.getPass()); // Dùng getPass() từ model
//            ps.setString(4, customer.getPhone());
//            ps.setString(5, customer.getAddress());
//            ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public Boolean createCustomer(String name, String email, String password, String numberPhone, String address) {
        String sql = "INSERT INTO Customer (full_name, email, password, phone, address) \n"
                + "VALUES (?, ?, ?, ?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hashMD5(password));
            ps.setString(4, numberPhone);
            ps.setString(5, address);
            int rs = ps.executeUpdate();
            return rs > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Lấy thông tin khách hàng bằng ID. (Hàm này sẽ cần thiết cho các DAO sau
     * như OrderDAO, CartDAO)
     *
     * @param cus_id ID của khách hàng.
     * @return Đối tượng Customer hoặc null.
     */
    public Customer getCustomerById(int cus_id) {
        String sql = "SELECT * FROM Customer WHERE cus_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cus_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("cus_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("address")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // Thêm hàm này vào file DAO/CustomerDAO.java đã có

    /**
     * [Admin] Xóa một khách hàng khỏi cơ sở dữ liệu.
     *
     * @param cus_id ID của khách hàng cần xóa.
     */
    public void deleteCustomer(int cus_id) {
        // Lưu ý: Cần xử lý xóa các đơn hàng và giỏ hàng của khách này trước
        // để tránh lỗi khóa ngoại.
        String sql = "DELETE FROM Customer WHERE cus_id = ?";
        try {
            // Ví dụ: Xóa các mục liên quan trong giỏ hàng trước
            // PreparedStatement psCart = conn.prepareStatement("DELETE FROM Cart WHERE cus_id = ?");
            // psCart.setInt(1, cus_id);
            // psCart.executeUpdate();

            // (Tương tự cần xóa trong Orders và OrderDetail)
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cus_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        Boolean c = dao.updateProfile("123","123","123",1);
        System.out.println(c);
    }

    // Lấy customer theo Email
    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM customer WHERE email = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Date otpExpires = null;
                if (rs.getTimestamp("otp_expires_at") != null) {
                    otpExpires = new Date(rs.getTimestamp("otp_expires_at").getTime());
                }
                return new Customer(
                        rs.getInt("cus_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("otp_code"),
                        otpExpires,
                        rs.getInt("otp_attempts")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật OTP
    public void updateOTP(int cus_id, String otp, Date expiresAt) {
        String sql = "UPDATE customer SET otp_code=?, otp_expires_at=?, otp_attempts=0 WHERE cus_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, otp);
            ps.setTimestamp(2, new java.sql.Timestamp(expiresAt.getTime()));

            ps.setInt(3, cus_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tăng số lần thử OTP
    public void incrementOtpAttempts(int cus_id) {
        String sql = "UPDATE customer SET otp_attempts = otp_attempts + 1 WHERE cus_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cus_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Đổi mật khẩu và xóa OTP
    public void resetPassword(int cus_id, String hashedPassword) {
        String sql = "UPDATE customer SET password=?, otp_code=NULL, otp_expires_at=NULL, otp_attempts=0 WHERE cus_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, hashedPassword); // mật khẩu mới đã hash
            ps.setInt(2, cus_id);            // ID customer cần đổi mật khẩu
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateProfile(String name, String address, String phone,int cus_id) {
        String sql = "update customer \n"
                + "set full_name=?,\n"
                + "\n"
                + "phone=?\n,"
                + "address=?\n"
                + "where cus_id =?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,phone);
            ps.setString(3,address);
            ps.setInt(4,cus_id);
            int num = ps.executeUpdate();
            return num>0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isValidPassword(String password){
        if(password.length()<6){
            return false;
        }
        
        boolean hasSpecialCharacter = false;
        for(char c : password.toCharArray()){
            if(!Character.isLetterOrDigit(c)){
                hasSpecialCharacter = true;
            }
        }
        return hasSpecialCharacter;
        
        
        
    }
  
}
