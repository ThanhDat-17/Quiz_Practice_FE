package dao;

import model.Subject;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private Connection connection;

    public SubjectDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all subjects with pagination
    public List<Subject> getAllSubjects(int page, int pageSize) {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.subject_id, s.subject_name, s.subject_image, s.description, s.is_active, " +
                    "s.category_id, c.category_name " +
                    "FROM subjects s LEFT JOIN categories c ON s.category_id = c.category_id " +
                    "ORDER BY s.subject_id " +
                    "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject subject = createSubjectFromResultSet(rs);
                    subjects.add(subject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subjects;
    }

    // Search subjects by name and category with pagination
    public List<Subject> searchSubjects(String searchName, Integer categoryId, int page, int pageSize) {
        List<Subject> subjects = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT s.subject_id, s.subject_name, s.subject_image, s.description, s.is_active, " +
            "s.category_id, c.category_name " +
            "FROM subjects s LEFT JOIN categories c ON s.category_id = c.category_id WHERE 1=1"
        );
        
        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append(" AND s.subject_name LIKE ?");
        }
        
        if (categoryId != null && categoryId > 0) {
            sql.append(" AND s.category_id = ?");
        }
        
        sql.append(" ORDER BY s.subject_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + searchName.trim() + "%");
            }
            
            if (categoryId != null && categoryId > 0) {
                ps.setInt(paramIndex++, categoryId);
            }
            
            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject subject = createSubjectFromResultSet(rs);
                    subjects.add(subject);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subjects;
    }

    // Get total count of subjects
    public int getTotalSubjectsCount() {
        String sql = "SELECT COUNT(*) FROM subjects";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    // Get search count
    public int getSearchSubjectsCount(String searchName, Integer categoryId) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM subjects s WHERE 1=1");
        
        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append(" AND s.subject_name LIKE ?");
        }
        
        if (categoryId != null && categoryId > 0) {
            sql.append(" AND s.category_id = ?");
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + searchName.trim() + "%");
            }
            
            if (categoryId != null && categoryId > 0) {
                ps.setInt(paramIndex, categoryId);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    // Get subject by ID
    public Subject getSubjectById(int subjectId) {
        String sql = "SELECT s.subject_id, s.subject_name, s.subject_image, s.description, s.is_active, " +
                    "s.category_id, c.category_name " +
                    "FROM subjects s LEFT JOIN categories c ON s.category_id = c.category_id " +
                    "WHERE s.subject_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createSubjectFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // Insert new subject
    public boolean insertSubject(Subject subject) {
        String sql = "INSERT INTO subjects (subject_name, subject_image, description, is_active, category_id) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectName());
            ps.setString(2, subject.getSubjectImage());
            ps.setString(3, subject.getDescription());
            ps.setBoolean(4, subject.isActive());
            ps.setInt(5, subject.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update subject
    public boolean updateSubject(Subject subject) {
        String sql = "UPDATE subjects SET subject_name = ?, subject_image = ?, description = ?, is_active = ?, category_id = ? WHERE subject_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectName());
            ps.setString(2, subject.getSubjectImage());
            ps.setString(3, subject.getDescription());
            ps.setBoolean(4, subject.isActive());
            ps.setInt(5, subject.getCategoryId());
            ps.setInt(6, subject.getSubjectId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete subject
    public boolean deleteSubject(int subjectId) {
        String sql = "DELETE FROM subjects WHERE subject_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to create Subject from ResultSet
    private Subject createSubjectFromResultSet(ResultSet rs) throws SQLException {
        Subject subject = new Subject();
        subject.setSubjectId(rs.getInt("subject_id"));
        subject.setSubjectName(rs.getString("subject_name"));
        subject.setSubjectImage(rs.getString("subject_image"));
        subject.setDescription(rs.getString("description"));
        subject.setActive(rs.getBoolean("is_active"));
        subject.setCategoryId(rs.getInt("category_id"));
        subject.setCategoryName(rs.getString("category_name"));
        return subject;
    }

    // Close connection
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 