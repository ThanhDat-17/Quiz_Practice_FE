/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entity.Categories;
import entity.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ulti.DBContext;

/**
 *
 * @author The Shuyy
 */
public class CategoryDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public List<Categories> getAll(){
        List<Categories> list = new ArrayList<>();
        String query = "select * from categories";
        Connection conn = new DBContext().getConnection();

        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Categories(rs.getInt(1), rs.getString(2)));
                }
            }
        } catch (SQLException e) {
        }

        return list;
    }
    
    public Categories findById(int id){
        String query = "select * from categories where category_id = " + id;
        Connection conn = new DBContext().getConnection();

        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Categories(rs.getInt(1), rs.getString(2));
                }
            }
        } catch (SQLException e) {
        }

        return null;
    }
}
