/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class ProductVariant {
    private int variant_id;
    Product pro_id;
    private String volume; // dung tích
    private double price;
    private int quantity;

    public ProductVariant() {
    }

    public ProductVariant(int variant_id, Product pro_id, String volume, double price, int quantity) {
        this.variant_id = variant_id;
        this.pro_id = pro_id;
        this.volume = volume;
        this.price = price;
        this.quantity = quantity;
    }

    public int getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(int variant_id) {
        this.variant_id = variant_id;
    }

    public Product getPro_id() {
        return pro_id;
    }

    public void setPro_id(Product pro_id) {
        this.pro_id = pro_id;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductVariant{" + "variant_id=" + variant_id + ", pro_id=" + pro_id + ", volume=" + volume + ", price=" + price + ", quantity=" + quantity + '}';
    }

}