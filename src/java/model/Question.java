package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Question implements Serializable {
    private int questionId;
    private int quizId;
    private String questionText;
    private String questionType; // Multiple choice, True/False, etc.
    private int questionOrder;
    private double points;
    private boolean isActive;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    
    // For joined data
    private List<QuestionOption> options;

    // Default constructor
    public Question() {}

    // Constructor with essential fields
    public Question(int quizId, String questionText, String questionType, 
                   int questionOrder, double points, boolean isActive) {
        this.quizId = quizId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.questionOrder = questionOrder;
        this.points = points;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<QuestionOption> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOption> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", quizId=" + quizId +
                ", questionText='" + questionText + '\'' +
                ", questionType='" + questionType + '\'' +
                ", questionOrder=" + questionOrder +
                ", points=" + points +
                ", isActive=" + isActive +
                '}';
    }
} 