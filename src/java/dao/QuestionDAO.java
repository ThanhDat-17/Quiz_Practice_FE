package dao;

import model.Question;
import model.QuestionOption;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private Connection connection;

    public QuestionDAO() {
        try {
            connection = new DBContext().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all questions for a specific quiz
    public List<Question> getQuestionsByQuizId(int quizId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE quiz_id = ? AND is_active = 1 ORDER BY question_order";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Question question = createQuestionFromResultSet(rs);
                    // Load options for each question
                    question.setOptions(getOptionsByQuestionId(question.getQuestionId()));
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return questions;
    }

    // Get question by ID
    public Question getQuestionById(int questionId) {
        String sql = "SELECT * FROM questions WHERE question_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Question question = createQuestionFromResultSet(rs);
                    question.setOptions(getOptionsByQuestionId(questionId));
                    return question;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // Insert new question
    public boolean insertQuestion(Question question) {
        String sql = "INSERT INTO questions (quiz_id, question_text, question_type, question_order, " +
                    "points, is_active, created_date, updated_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";
        
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, question.getQuizId());
            ps.setString(2, question.getQuestionText());
            ps.setString(3, question.getQuestionType());
            ps.setInt(4, question.getQuestionOrder());
            ps.setDouble(5, question.getPoints());
            ps.setBoolean(6, question.isActive());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int questionId = generatedKeys.getInt(1);
                        question.setQuestionId(questionId);
                        
                        // Insert options if any
                        if (question.getOptions() != null && !question.getOptions().isEmpty()) {
                            for (QuestionOption option : question.getOptions()) {
                                option.setQuestionId(questionId);
                                insertQuestionOption(option);
                            }
                        }
                        
                        // Update quiz question count
                        updateQuizQuestionCount(question.getQuizId());
                        
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    // Update question
    public boolean updateQuestion(Question question) {
        String sql = "UPDATE questions SET question_text = ?, question_type = ?, question_order = ?, " +
                    "points = ?, is_active = ?, updated_date = GETDATE() WHERE question_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, question.getQuestionText());
            ps.setString(2, question.getQuestionType());
            ps.setInt(3, question.getQuestionOrder());
            ps.setDouble(4, question.getPoints());
            ps.setBoolean(5, question.isActive());
            ps.setInt(6, question.getQuestionId());
            
            boolean updated = ps.executeUpdate() > 0;
            
            if (updated) {
                // Delete existing options
                deleteOptionsByQuestionId(question.getQuestionId());
                
                // Insert new options
                if (question.getOptions() != null && !question.getOptions().isEmpty()) {
                    for (QuestionOption option : question.getOptions()) {
                        option.setQuestionId(question.getQuestionId());
                        insertQuestionOption(option);
                    }
                }
                
                // Update quiz question count
                updateQuizQuestionCount(question.getQuizId());
            }
            
            return updated;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete question
    public boolean deleteQuestion(int questionId) {
        try {
            // Get quiz ID before deletion for count update
            Question question = getQuestionById(questionId);
            if (question == null) return false;
            
            // Delete options first
            deleteOptionsByQuestionId(questionId);
            
            // Delete question
            String sql = "DELETE FROM questions WHERE question_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, questionId);
                boolean deleted = ps.executeUpdate() > 0;
                
                if (deleted) {
                    // Update quiz question count
                    updateQuizQuestionCount(question.getQuizId());
                }
                
                return deleted;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get options for a question
    public List<QuestionOption> getOptionsByQuestionId(int questionId) {
        List<QuestionOption> options = new ArrayList<>();
        String sql = "SELECT * FROM question_options WHERE question_id = ? ORDER BY option_order";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    QuestionOption option = createOptionFromResultSet(rs);
                    options.add(option);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return options;
    }

    // Insert question option
    public boolean insertQuestionOption(QuestionOption option) {
        String sql = "INSERT INTO question_options (question_id, option_text, is_correct, " +
                    "option_order, created_date) VALUES (?, ?, ?, ?, GETDATE())";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, option.getQuestionId());
            ps.setString(2, option.getOptionText());
            ps.setBoolean(3, option.isCorrect());
            ps.setInt(4, option.getOptionOrder());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete options by question ID
    public boolean deleteOptionsByQuestionId(int questionId) {
        String sql = "DELETE FROM question_options WHERE question_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            return ps.executeUpdate() >= 0; // Could be 0 if no options exist
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get questions count for a quiz
    public int getQuestionCountByQuizId(int quizId) {
        String sql = "SELECT COUNT(*) FROM questions WHERE quiz_id = ? AND is_active = 1";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            
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

    // Update quiz question count
    public boolean updateQuizQuestionCount(int quizId) {
        int questionCount = getQuestionCountByQuizId(quizId);
        String sql = "UPDATE quizzes SET number_of_questions = ?, updated_date = GETDATE() WHERE quiz_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, questionCount);
            ps.setInt(2, quizId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get next question order for a quiz
    public int getNextQuestionOrder(int quizId) {
        String sql = "SELECT ISNULL(MAX(question_order), 0) + 1 FROM questions WHERE quiz_id = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 1;
    }

    // Helper method to create Question from ResultSet
    private Question createQuestionFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setQuestionId(rs.getInt("question_id"));
        question.setQuizId(rs.getInt("quiz_id"));
        question.setQuestionText(rs.getString("question_text"));
        question.setQuestionType(rs.getString("question_type"));
        question.setQuestionOrder(rs.getInt("question_order"));
        question.setPoints(rs.getDouble("points"));
        question.setActive(rs.getBoolean("is_active"));
        question.setCreatedDate(rs.getTimestamp("created_date"));
        question.setUpdatedDate(rs.getTimestamp("updated_date"));
        return question;
    }

    // Helper method to create QuestionOption from ResultSet
    private QuestionOption createOptionFromResultSet(ResultSet rs) throws SQLException {
        QuestionOption option = new QuestionOption();
        option.setOptionId(rs.getInt("option_id"));
        option.setQuestionId(rs.getInt("question_id"));
        option.setOptionText(rs.getString("option_text"));
        option.setCorrect(rs.getBoolean("is_correct"));
        option.setOptionOrder(rs.getInt("option_order"));
        option.setCreatedDate(rs.getTimestamp("created_date"));
        return option;
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