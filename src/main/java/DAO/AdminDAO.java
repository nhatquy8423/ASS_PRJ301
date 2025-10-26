package DAO;

import model.Admin;
import utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Cần có thư viện BCrypt được import và thêm vào pom.xml
import org.mindrot.jbcrypt.BCrypt;

/**
 * Lớp DAO xử lý các thao tác liên quan đến bảng Admin. Kế thừa DBContext để sử
 * dụng Connection đã được khởi tạo.
 */
// Kế thừa DBContext
public class AdminDAO extends DBContext {

    // THAY ĐỔI: Chỉ truy vấn bằng EMAIL để lấy HASHED PASSWORD
    private static final String GET_ADMIN_BY_EMAIL_SQL = "SELECT admin_id, admin_name, username, password, email FROM Admin WHERE email = ?";

    /**
     * Xác thực Admin bằng email và password (sử dụng BCrypt để so sánh hash).
     * ... (phương thức getAdmin giữ nguyên logic BCrypt đã trình bày trước đó)
     * ...
     */
    public Admin getAdmin(String email, String password) {
        // ... (Nội dung của getAdmin đã được thay đổi để dùng BCrypt, giữ nguyên) ...
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Lấy Admin bằng email
            ps = conn.prepareStatement(GET_ADMIN_BY_EMAIL_SQL);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Mật khẩu khớp
                    Admin admin = new Admin();
                    admin.setAdmin_id(rs.getInt("admin_id"));
                    admin.setAdmin_name(rs.getString("admin_name"));
                    admin.setUsername(rs.getString("username"));
                    admin.setPassword(hashedPassword);
                    admin.setEmail(rs.getString("email"));
                    return admin;
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, "Lỗi truy vấn Admin.", e);
        } catch (Exception e) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, "Lỗi BCrypt hoặc kết nối.", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, "Lỗi đóng tài nguyên.", e);
            }
        }
        return null;
    }

    /**
     * Phương thức tiện ích để tạo hash cho mật khẩu mới.
     *
     * @param plainPassword Mật khẩu chưa hash.
     * @return Chuỗi mật khẩu đã hash.
     */
    public String hashPassword(String plainPassword) {
        // BCrypt.gensalt() tạo ra một salt ngẫu nhiên
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * PHƯƠNG THỨC MAIN ĐỂ TEST VÀ TẠO HASH
     */
    public static void main(String[] args) {
        AdminDAO dao = new AdminDAO();
        String[] passwords = {"888888", "999999", "686868", "868686", "363636"};
        String[] usernames = {"nhatquy", "tientrien", "hoangkhang", "hoangnhan", "dangkhoa"};

        System.out.println("--- BƯỚC 3: TẠO HASH CHO CÁC MẬT KHẨU ADMIN ---");

        for (int i = 0; i < passwords.length; i++) {
            String plainPass = passwords[i];

            // Tạo hash
            String hashedPassword = dao.hashPassword(plainPass);

            // In ra lệnh SQL UPDATE
            System.out.println("------------------------------------------");
            System.out.println("Admin: " + usernames[i]);
            System.out.println("Mật khẩu gốc: " + plainPass);
            System.out.println("Mật khẩu Hash: " + hashedPassword);
            System.out.println("\nLỆNH SQL CẦN CHẠY:");
            System.out.println("UPDATE Admin SET password = '" + hashedPassword + "' WHERE username = '" + usernames[i] + "';");
            System.out.println("------------------------------------------");
        }

        // --- Ví dụ test BCrypt (Không bắt buộc) ---
        // String testHash = "$2a$10$wTfHqfVzFmD.V8C1E0l9i.K2L6l9Q2A7O2G7E2L7F2K7D2H8J9I"; // Giả định
        // boolean matched = BCrypt.checkpw("888888", testHash);
        // System.out.println("\nTest BCrypt match: " + matched);
        String dbHash = "$2a$10$.rQBi4d4n6tyuzp51bYnO.BRMI5g/mcS5AETaAkx5l2K8Pvm0kh1u";
        boolean isMatch = BCrypt.checkpw("888888", dbHash); // Kiểm tra với mật khẩu gốc
        System.out.println("Kiểm tra khớp mật khẩu với DB: " + isMatch);
    }
}
