/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Customer {

    private int cus_id;
    private String fullname, email, pass, phone, address;
    private String full_name;
    private String password;
    private String otp_code;
    private int otp_attempts;
    private Date otp_expires_at;
    public Customer() {

    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp_code() {
        return otp_code;
    }

    public void setOtp_code(String otp_code) {
        this.otp_code = otp_code;
    }

    public int getOtp_attempts() {
        return otp_attempts;
    }

    public void setOtp_attempts(int otp_attempts) {
        this.otp_attempts = otp_attempts;
    }

    public Date getOtp_expires_at() {
        return otp_expires_at;
    }

    public void setOtp_expires_at(Date otp_expires_at) {
        this.otp_expires_at = otp_expires_at;
    }

    public Customer(int cus_id, String full_name, String email, String password,
            String phone, String address, String otp_code, Date otp_expires_at,
            int otp_attempts) {
        this.cus_id = cus_id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.otp_code = otp_code;
        this.otp_expires_at = otp_expires_at;
        this.otp_attempts = otp_attempts;
    }

    public Customer(int cus_id, String fullname, String email, String pass, String phone, String address) {
        this.cus_id = cus_id;
        this.fullname = fullname;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.address = address;
    }

    public int getCus_id() {
        return cus_id;
    }

    public void setCus_id(int cus_id) {
        this.cus_id = cus_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" + "cus_id=" + cus_id + ", fullname=" + fullname + ", email=" + email + ", pass=" + pass + ", phone=" + phone + ", address=" + address + '}';
    }

    public void setAvatarUrl(String avatarUrl) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
