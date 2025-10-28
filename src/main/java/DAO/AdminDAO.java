package DAO;

import java.security.MessageDigest;
import model.Admin;
import utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminDAO extends DBContext {

    public String HashMD5(String pass) {
        String hashPass = "";
        try {
            MessageDigest ms = MessageDigest.getInstance("MD5");
            byte[] bytePass = ms.digest(pass.getBytes());
            //[0x1a, 0x09, 0x1b...]
            for (byte bytePas : bytePass) {
                hashPass += String.format("%02x", bytePas);
            }
        } catch (Exception e) {
        }

        return hashPass;
    }

    public static void main(String[] args) {
        AdminDAO dao = new AdminDAO();
        System.out.println(dao.login("nhatquy@gmail.com", "888888"));
//        String pass = "888888";
//        System.out.println(dao.HashMD5(pass));
    }

    public Admin login(String email, String pass) {
        String sql = "select*from Admin where email = ? and password = ?";
        Admin a = new Admin();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, HashMD5(pass));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                a.setAdmin_id(rs.getInt("admin_id"));
                a.setAdmin_name(rs.getString("admin_name"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                a.setEmail(rs.getString("email"));

            }
        } catch (Exception e) {
            System.out.println("Lỗi khi login AdminDAO: " + e.getMessage());
            e.printStackTrace();
        }

        return a;
    }

}
