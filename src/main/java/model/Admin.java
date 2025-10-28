/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Admin {
    private int admin_id;
    // Bổ sung email vào trường khai báo
    private String admin_name, username, password, email; 

    public Admin() {
        this.admin_id = -1;
    }

    // Cập nhật Constructor
    public Admin(int admin_id, String admin_name, String username, String password, String email) {
        this.admin_id = admin_id;
        this.admin_name = admin_name;
        this.username = username;
        this.password = password;
        this.email = email; // Thêm email
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    // Bổ sung Getter và Setter cho email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" + "admin_id=" + admin_id + ", admin_name=" + admin_name + ", username=" + username + ", password=" + password + ", email=" + email + '}';
    }
    
}