/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.events.Comment;
import model.Comments;
import utils.DBContext;

/**
 *
 * @author Trien
 */
public class CommentDAO extends DBContext {

    public void addComment(Comments comment) {
        String sql = "Insert into comments(pro_id,full_name,content)\n"
                + "values(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, comment.getPro_id());
            ps.setString(2, comment.getFull_name());
            ps.setString(3, comment.getContent());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Comments> getCommentsByProductId(int productId) throws Exception {
        List<Comments> list = new ArrayList<>();
       
        String sql = "SELECT * FROM comments WHERE pro_id=? ORDER BY created_at DESC";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, productId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Comments c = new Comments();
            c.setId(rs.getInt("id"));
            c.setPro_id(rs.getInt("pro_id"));
            c.setFull_name(rs.getString("full_name"));
            c.setContent(rs.getString("content"));
            c.setCreated_at(rs.getTimestamp("created_at"));
            list.add(c);
        }
        
        return list;
    }
}
