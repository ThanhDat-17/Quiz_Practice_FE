/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entity.Subjects;
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
public class CourseDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Subjects> getSubjects(Integer categoryId) {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Subjects> list = new ArrayList<>();
        String query = "SELECT * from subjects where 1=1";  // Truy vấn tất cả nếu không có categoryId

        if (categoryId != null) {
            query += " AND category_id = ?";
        }

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            if (categoryId != null) {
                ps.setInt(1, categoryId);  // Thêm categoryId vào câu lệnh SQL
            }

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Subjects(rs.getInt(1), rs.getBoolean(2), rs.getString(3), rs.getLong(6), rs.getString(4), categoryDAO.findById(rs.getInt(5))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }

        return list;
    }

    public List<Subjects> searchSubjects(String searchQuery) {
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Subjects> list = new ArrayList<>();
        String query = "SELECT * from subjects WHERE subject_name LIKE ?";
        Connection conn = new DBContext().getConnection();

        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "%" + searchQuery + "%");  // Tìm kiếm theo tên môn học
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Subjects(rs.getInt(1), rs.getBoolean(2), rs.getString(3), rs.getLong(6), rs.getString(4), categoryDAO.findById(rs.getInt(5))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }

        return list;
    }

    public Subjects getSubjectById(Integer categoryId) {
        CategoryDAO categoryDAO = new CategoryDAO();
        String query = "SELECT *\n"
                + "  FROM [QuizPractice].[dbo].[subjects] where subject_id = ?";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, categoryId);  // Thêm categoryId vào câu lệnh SQL

            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subjects subjects = new Subjects(rs.getInt(1), rs.getBoolean(2), rs.getString(3), rs.getLong(6), rs.getString(4), categoryDAO.findById(rs.getInt(5)));
                    subjects.setDescription(rs.getString(7));
                    return subjects;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }

        return null;
    }
}
