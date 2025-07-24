package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Quiz implements Serializable {
    private int quizId;
    private String quizName;
    private String description;
    private int subjectId;
    private String subjectName; // For display purposes
    private String level; // Basic, Intermediate, Advanced
    private int duration; // Duration in minutes
    private double passRate; // Pass rate percentage (0-100)
    private String type; // Practice, Exam, Assignment
    private int totalQuestions;
    private boolean isActive;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private int createdBy;

    // Default constructor
    public Quiz() {}

    // Constructor with essential fields
    public Quiz(String quizName, String description, int subjectId, String level, 
                int duration, double passRate, String type, boolean isActive) {
        this.quizName = quizName;
        this.description = description;
        this.subjectId = subjectId;
        this.level = level;
        this.duration = duration;
        this.passRate = passRate;
        this.type = type;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPassRate() {
        return passRate;
    }

    public void setPassRate(double passRate) {
        this.passRate = passRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", quizName='" + quizName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", level='" + level + '\'' +
                ", type='" + type + '\'' +
                ", isActive=" + isActive +
                '}';
    }
} 