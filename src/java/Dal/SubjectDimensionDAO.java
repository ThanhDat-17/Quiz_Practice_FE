/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;

import java.sql.Connection;
import entity.SubjectDimension;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Asus
 */
public class SubjectDimensionDAO {
    private Connection conn;

    public SubjectDimensionDAO(Connection conn) {
        this.conn = conn;
    }

    public List<SubjectDimension> getAllDimensions() {
        List<SubjectDimension> list = new ArrayList<>();
        String query = "SELECT d.dimension_id, c.category_name, d.dimension_name, d.description " +
                     "FROM subject_dimensions d " +
                     "JOIN categories c ON d.category_id = c.category_id";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SubjectDimension s = new SubjectDimension();
                s.setId(rs.getInt("dimension_id"));
                s.setType(rs.getString("category_name"));
                s.setName(rs.getString("dimension_name"));
                s.setDescription(rs.getString("description"));
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public SubjectDimension getDimensionById(int id) {
    String sql = "SELECT d.dimension_id, d.dimension_name, d.description, d.is_active, c.category_name " +
                 "FROM subject_dimensions d JOIN categories c ON d.category_id = c.category_id " +
                 "WHERE d.dimension_id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new SubjectDimension(
                rs.getInt("dimension_id"),
                rs.getString("dimension_name"),
                rs.getString("description"),
                rs.getString("category_name"),
                rs.getBoolean("is_active")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public void updateDimension(int id, String name, String description, int categoryId) {
    String sql = "UPDATE subject_dimensions SET dimension_name = ?, description = ?, category_id = ?, updated_at = GETDATE() WHERE dimension_id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, name);
        ps.setString(2, description);
        ps.setInt(3, categoryId);
        ps.setInt(4, id);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


public void insertDimension(String name, String description, int categoryId) {
    String sql = "INSERT INTO subject_dimensions (subject_id, category_id, dimension_name, description, is_active, created_at, updated_at) " +
                 "VALUES (?, ?, ?, ?, 1, GETDATE(), GETDATE())";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, 1); // subject_id tạm thời mặc định
        ps.setInt(2, categoryId);
        ps.setString(3, name);
        ps.setString(4, description);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}