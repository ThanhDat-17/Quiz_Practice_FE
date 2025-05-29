package Dal;

import entity.ResetToken;
import entity.Users; // Đảm bảo import class Users của bạn
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class UserDAO extends DBContext {
    
// Tìm user theo email
    public Users findUserByEmail(String email) {
        if (getConnection() == null) {
            throw new IllegalStateException("Can't not connect to database!");
        }
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Users user = new Users();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lưu reset token vào DB
    public void saveResetToken(int userId, String token, Date expiryDate) {
        String sql = "INSERT INTO reset_token (token, user_id, expiry_date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.setInt(2, userId);
            ps.setTimestamp(3, new java.sql.Timestamp(expiryDate.getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tìm reset token
    public ResetToken findResetToken(String token) {
        String sql = "SELECT * FROM reset_token WHERE token = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ResetToken resetToken = new ResetToken();
                resetToken.setId(rs.getLong("id"));
                resetToken.setToken(rs.getString("token"));
                Users user = new Users();
                user.setUserId(rs.getInt("user_id"));
                resetToken.setUserId(user);
                resetToken.setExpiryDate(rs.getTimestamp("expiry_date"));
                return resetToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật mật khẩu mới
    public void updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPassword); 
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa token sau khi reset thành công
    public void deleteResetToken(String token) {
        String sql = "DELETE FROM reset_token WHERE token = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}