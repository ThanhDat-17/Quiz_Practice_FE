package dao;

import model.Quiz;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private Connection connection;

    public QuizDAO() {
        try {
            connection = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all quizzes with pagination
    public List<Quiz> getAllQuizzes(int page, int pageSize) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT q.quiz_id, q.quiz_name, q.description, q.subject_id, s.subject_name, " +
                    "q.level, q.duration, q.pass_rate, q.quiz_type, q.number_of_questions, " +
                    "q.is_active, q.created_date, q.updated_date, q.created_by, u.full_name as created_by_name " +
                    "FROM quizzes q " +
                    "LEFT JOIN subjects s ON q.subject_id = s.subject_id " +
                    "LEFT JOIN users u ON q.created_by = u.user_id " +
                    "ORDER BY q.quiz_id DESC " +
                    "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Quiz quiz = createQuizFromResultSet(rs);
                    quizzes.add(quiz);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return quizzes;
    }

    // Search quizzes by name, subject, and type with pagination
    public List<Quiz> searchQuizzes(String searchName, Integer subjectId, String quizType, int page, int pageSize) {
        List<Quiz> quizzes = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT q.quiz_id, q.quiz_name, q.description, q.subject_id, s.subject_name, " +
            "q.level, q.duration, q.pass_rate, q.quiz_type, q.number_of_questions, " +
            "q.is_active, q.created_date, q.updated_date, q.created_by, u.full_name as created_by_name " +
            "FROM quizzes q " +
            "LEFT JOIN subjects s ON q.subject_id = s.subject_id " +
            "LEFT JOIN users u ON q.created_by = u.user_id WHERE 1=1"
        );
        
        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append(" AND q.quiz_name LIKE ?");
        }
        
        if (subjectId != null && subjectId > 0) {
            sql.append(" AND q.subject_id = ?");
        }
        
        if (quizType != null && !quizType.trim().isEmpty() && !"ALL".equals(quizType)) {
            sql.append(" AND q.quiz_type = ?");
        }
        
        sql.append(" ORDER BY q.quiz_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + searchName.trim() + "%");
            }
            
            if (subjectId != null && subjectId > 0) {
                ps.setInt(paramIndex++, subjectId);
            }
            
            if (quizType != null && !quizType.trim().isEmpty() && !"ALL".equals(quizType)) {
                ps.setString(paramIndex++, quizType);
            }
            
            ps.setInt(paramIndex++, (page - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Quiz quiz = createQuizFromResultSet(rs);
                    quizzes.add(quiz);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return quizzes;
    }

    // Get total count of quizzes
    public int getTotalQuizzesCount() {
        String sql = "SELECT COUNT(*) FROM quizzes";
        
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
    public int getSearchQuizzesCount(String searchName, Integer subjectId, String quizType) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM quizzes q WHERE 1=1");
        
        if (searchName != null && !searchName.trim().isEmpty()) {
            sql.append(" AND q.quiz_name LIKE ?");
        }
        
        if (subjectId != null && subjectId > 0) {
            sql.append(" AND q.subject_id = ?");
        }
        
        if (quizType != null && !quizType.trim().isEmpty() && !"ALL".equals(quizType)) {
            sql.append(" AND q.quiz_type = ?");
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            if (searchName != null && !searchName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + searchName.trim() + "%");
            }
            
            if (subjectId != null && subjectId > 0) {
                ps.setInt(paramIndex++, subjectId);
            }
            
            if (quizType != null && !quizType.trim().isEmpty() && !"ALL".equals(quizType)) {
                ps.setString(paramIndex, quizType);
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

    // Get quiz by ID
    public Quiz getQuizById(int quizId) {
        String sql = "SELECT q.quiz_id, q.quiz_name, q.description, q.subject_id, s.subject_name, " +
                    "q.level, q.duration, q.pass_rate, q.quiz_type, q.number_of_questions, " +
                    "q.is_active, q.created_date, q.updated_date, q.created_by, u.full_name as created_by_name " +
                    "FROM quizzes q " +
                    "LEFT JOIN subjects s ON q.subject_id = s.subject_id " +
                    "LEFT JOIN users u ON q.created_by = u.user_id " +
                    "WHERE q.quiz_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createQuizFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // Insert new quiz
    public boolean insertQuiz(Quiz quiz) {
        String sql = "INSERT INTO quizzes (quiz_name, description, subject_id, level, duration, " +
                    "pass_rate, quiz_type, number_of_questions, is_active, created_date, " +
                    "updated_date, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, quiz.getQuizName());
            ps.setString(2, quiz.getDescription());
            ps.setInt(3, quiz.getSubjectId());
            ps.setString(4, quiz.getLevel());
            ps.setInt(5, quiz.getDuration());
            ps.setDouble(6, quiz.getPassRate());
            ps.setString(7, quiz.getQuizType());
            ps.setInt(8, quiz.getNumberOfQuestions());
            ps.setBoolean(9, quiz.isActive());
            ps.setInt(10, quiz.getCreatedBy());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update quiz
    public boolean updateQuiz(Quiz quiz) {
        String sql = "UPDATE quizzes SET quiz_name = ?, description = ?, subject_id = ?, level = ?, " +
                    "duration = ?, pass_rate = ?, quiz_type = ?, number_of_questions = ?, " +
                    "is_active = ?, updated_date = GETDATE() WHERE quiz_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, quiz.getQuizName());
            ps.setString(2, quiz.getDescription());
            ps.setInt(3, quiz.getSubjectId());
            ps.setString(4, quiz.getLevel());
            ps.setInt(5, quiz.getDuration());
            ps.setDouble(6, quiz.getPassRate());
            ps.setString(7, quiz.getQuizType());
            ps.setInt(8, quiz.getNumberOfQuestions());
            ps.setBoolean(9, quiz.isActive());
            ps.setInt(10, quiz.getQuizId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete quiz
    public boolean deleteQuiz(int quizId) {
        String sql = "DELETE FROM quizzes WHERE quiz_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get quiz count by subject
    public int getQuizCountBySubject(int subjectId) {
        String sql = "SELECT COUNT(*) FROM quizzes WHERE subject_id = ? AND is_active = 1";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            
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

    // Helper method to create Quiz object from ResultSet
    private Quiz createQuizFromResultSet(ResultSet rs) throws SQLException {
        Quiz quiz = new Quiz();
        quiz.setQuizId(rs.getInt("quiz_id"));
        quiz.setQuizName(rs.getString("quiz_name"));
        quiz.setDescription(rs.getString("description"));
        quiz.setSubjectId(rs.getInt("subject_id"));
        quiz.setSubjectName(rs.getString("subject_name"));
        quiz.setLevel(rs.getString("level"));
        quiz.setDuration(rs.getInt("duration"));
        quiz.setPassRate(rs.getDouble("pass_rate"));
        quiz.setQuizType(rs.getString("quiz_type"));
        quiz.setNumberOfQuestions(rs.getInt("number_of_questions"));
        quiz.setActive(rs.getBoolean("is_active"));
        quiz.setCreatedDate(rs.getTimestamp("created_date"));
        quiz.setUpdatedDate(rs.getTimestamp("updated_date"));
        quiz.setCreatedBy(rs.getInt("created_by"));
        quiz.setCreatedByName(rs.getString("created_by_name"));
        return quiz;
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