package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Quiz;
import model.Subject;
import utils.DBContext;

public class QuizDAO {
    private Connection connection;

    public QuizDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all quizzes with pagination
    public List<Quiz> getAllQuizzes(int page, int pageSize) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT q.quiz_id, q.quiz_name, q.description, q.subject_id, s.subject_name, " +
                    "q.level, q.duration, q.pass_rate, q.quiz_type, q.number_of_questions, q.is_active, " +
                    "q.created_date, q.updated_date, q.created_by " +
                    "FROM quizzes q LEFT JOIN subjects s ON q.subject_id = s.subject_id " +
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

    // Search quizzes by name with filters and pagination
    public List<Quiz> searchQuizzes(String searchKeyword, String subjectFilter, String typeFilter, int page, int pageSize) {
        List<Quiz> quizzes = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT q.quiz_id, q.quiz_name, q.description, q.subject_id, s.subject_name, ");
        sql.append("q.level, q.duration, q.pass_rate, q.quiz_type, q.number_of_questions, q.is_active, ");
        sql.append("q.created_date, q.updated_date, q.created_by ");
        sql.append("FROM quizzes q LEFT JOIN subjects s ON q.subject_id = s.subject_id ");
        sql.append("WHERE 1=1 ");
        
        List<Object> params = new ArrayList<>();
        
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            sql.append("AND q.quiz_name LIKE ? ");
            params.add("%" + searchKeyword.trim() + "%");
        }
        
        if (subjectFilter != null && !subjectFilter.isEmpty() && !"all".equals(subjectFilter)) {
            sql.append("AND q.subject_id = ? ");
            params.add(Integer.parseInt(subjectFilter));
        }
        
        if (typeFilter != null && !typeFilter.isEmpty() && !"all".equals(typeFilter)) {
            sql.append("AND q.quiz_type = ? ");
            params.add(typeFilter);
        }
        
        sql.append("ORDER BY q.quiz_id DESC ");
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add((page - 1) * pageSize);
        params.add(pageSize);
        
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
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

    // Get total count of search results
    public int getSearchQuizzesCount(String searchKeyword, String subjectFilter, String typeFilter) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM quizzes q WHERE 1=1 ");
        
        List<Object> params = new ArrayList<>();
        
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            sql.append("AND q.quiz_name LIKE ? ");
            params.add("%" + searchKeyword.trim() + "%");
        }
        
        if (subjectFilter != null && !subjectFilter.isEmpty() && !"all".equals(subjectFilter)) {
            sql.append("AND q.subject_id = ? ");
            params.add(Integer.parseInt(subjectFilter));
        }
        
        if (typeFilter != null && !typeFilter.isEmpty() && !"all".equals(typeFilter)) {
            sql.append("AND q.quiz_type = ? ");
            params.add(typeFilter);
        }
        
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
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
                    "q.level, q.duration, q.pass_rate, q.quiz_type, q.number_of_questions, q.is_active, " +
                    "q.created_date, q.updated_date, q.created_by " +
                    "FROM quizzes q LEFT JOIN subjects s ON q.subject_id = s.subject_id " +
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
                    "pass_rate, quiz_type, is_active, created_date, updated_date, created_by) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE(), ?)";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, quiz.getQuizName());
            ps.setString(2, quiz.getDescription());
            ps.setInt(3, quiz.getSubjectId());
            ps.setString(4, quiz.getLevel());
            ps.setInt(5, quiz.getDuration());
            ps.setDouble(6, quiz.getPassRate());
            ps.setString(7, quiz.getType());
            ps.setBoolean(8, quiz.isActive());
            ps.setInt(9, quiz.getCreatedBy());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update quiz
    public boolean updateQuiz(Quiz quiz) {
        String sql = "UPDATE quizzes SET quiz_name = ?, description = ?, subject_id = ?, level = ?, " +
                    "duration = ?, pass_rate = ?, quiz_type = ?, is_active = ?, " +
                    "updated_date = GETDATE() WHERE quiz_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, quiz.getQuizName());
            ps.setString(2, quiz.getDescription());
            ps.setInt(3, quiz.getSubjectId());
            ps.setString(4, quiz.getLevel());
            ps.setInt(5, quiz.getDuration());
            ps.setDouble(6, quiz.getPassRate());
            ps.setString(7, quiz.getType());
            ps.setBoolean(8, quiz.isActive());
            ps.setInt(9, quiz.getQuizId());
            
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

    // Get all subjects for dropdown
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT subject_id, subject_name FROM subjects WHERE is_active = 1 ORDER BY subject_name";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subject_id"));
                subject.setSubjectName(rs.getString("subject_name"));
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subjects;
    }

    // Helper method to create Quiz from ResultSet
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
        quiz.setType(rs.getString("quiz_type"));
        quiz.setNumberOfQuestions(rs.getInt("number_of_questions"));
        quiz.setActive(rs.getBoolean("is_active"));
        quiz.setCreatedDate(rs.getTimestamp("created_date"));
        quiz.setUpdatedDate(rs.getTimestamp("updated_date"));
        quiz.setCreatedBy(rs.getInt("created_by"));
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