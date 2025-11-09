/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Trien
 */
public class Comments {
    private int id;
    private int pro_id;
    private String full_name;
    private String content;
    private Timestamp created_at;

    
    
    public Comments(){
        
    }
    public Comments(int id, int pro_id, String full_name, String content, Timestamp created_at) {
        this.id = id;
        this.pro_id = pro_id;
        this.full_name = full_name;
        this.content = content;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "comment{" + "id=" + id + ", pro_id=" + pro_id + ", full_name=" + full_name + ", content=" + content + ", created_at=" + created_at + '}';
    }
    
    
}
