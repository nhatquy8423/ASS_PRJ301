/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Product {
    private int pro_id;
    Category cat_id;
    private String pro_name, brand, des, image_url;
    private double min_price;
    
    public Product(){
        
    }

    public Product(int pro_id, Category cat_id, String pro_name, String brand, String des, String image_url) {
        this.pro_id = pro_id;
        this.cat_id = cat_id;
        this.pro_name = pro_name;
        this.brand = brand;
        this.des = des;
        this.image_url = image_url;
        this.min_price = min_price;
    }
    public double getMin_price() {
        return min_price;
    }

    public void setMin_price(double min_price) {
        this.min_price = min_price;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public Category getCat_id() {
        return cat_id;
    }

    public void setCat_id(Category cat_id) {
        this.cat_id = cat_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Product{" + "pro_id=" + pro_id + ", cat_id=" + cat_id + ", pro_name=" + pro_name + ", brand=" + brand + ", des=" + des + ", image_url=" + image_url + ", min_price=" + min_price + '}';
    }

    
    
}
