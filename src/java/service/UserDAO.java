/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entity.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ulti.DBContext;

/**
 *
 * @author The Shuyy
 */
public class UserDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void update(Users user) {
        String query = "UPDATE [dbo].[users]\n"
                + "   SET [description] = ?\n"
                + "      ,[full_name] = ?\n"
                + "      ,[profile_image] = ?\n"
                + "      ,[updated_date] = getdate()\n"
                + "      ,[gender] = ?\n"
                + " WHERE user_id = ?";
        Connection conn = new DBContext().getConnection();

        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getDescription());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getProfileImage());
            ps.setBoolean(4, user.getGender());
            ps.setInt(5, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public Users getUserByUserAndPass(String email, String password) {
        Users user = null;
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Connection conn = new DBContext().getConnection();

        try ( PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new Users(
                            rs.getInt("user_id"),
                            rs.getDate("created_date"),
                            rs.getString("description"),
                            rs.getString("email"),
                            rs.getString("full_name"),
                            rs.getBoolean("is_active"),
                            rs.getString("password"),
                            rs.getString("profile_image"),
                            rs.getDate("updated_date"),
                            rs.getDate("date_of_birth"),
                            rs.getBoolean("gender"),
                            rs.getString("phone")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
