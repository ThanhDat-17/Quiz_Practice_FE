package model;

import java.io.Serializable;
import java.util.Date;

public class Quiz implements Serializable {
    private int quizId;
    private String quizName;
    private String description;
    private int subjectId;
    private String subjectName; // For display purposes
    private String level; // EASY, MEDIUM, HARD
    private int duration; // Duration in minutes
    private double passRate; // Pass rate percentage (0-100)
    private String quizType; // PRACTICE, MOCK_TEST, ASSIGNMENT
    private int numberOfQuestions;
    private boolean isActive;
    private Date createdDate;
    private Date updatedDate;
    private int createdBy;
    private String createdByName; // For display purposes

    // Default constructor
    public Quiz() {}

    // Constructor with essential fields
    public Quiz(String quizName, String description, int subjectId, String level, 
                int duration, double passRate, String quizType, int numberOfQuestions, 
                boolean isActive, int createdBy) {
        this.quizName = quizName;
        this.description = description;
        this.subjectId = subjectId;
        this.level = level;
        this.duration = duration;
        this.passRate = passRate;
        this.quizType = quizType;
        this.numberOfQuestions = numberOfQuestions;
        this.isActive = isActive;
        this.createdBy = createdBy;
    }

    // Full constructor
    public Quiz(int quizId, String quizName, String description, int subjectId, 
                String level, int duration, double passRate, String quizType, 
                int numberOfQuestions, boolean isActive, Date createdDate, 
                Date updatedDate, int createdBy) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.description = description;
        this.subjectId = subjectId;
        this.level = level;
        this.duration = duration;
        this.passRate = passRate;
        this.quizType = quizType;
        this.numberOfQuestions = numberOfQuestions;
        this.isActive = isActive;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.createdBy = createdBy;
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

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    // Helper methods for displaying formatted values
    public String getLevelDisplay() {
        switch (level.toUpperCase()) {
            case "EASY": return "Easy";
            case "MEDIUM": return "Medium";
            case "HARD": return "Hard";
            default: return level;
        }
    }

    public String getQuizTypeDisplay() {
        switch (quizType.toUpperCase()) {
            case "PRACTICE": return "Practice";
            case "MOCK_TEST": return "Mock Test";
            case "ASSIGNMENT": return "Assignment";
            default: return quizType;
        }
    }

    public String getStatusDisplay() {
        return isActive ? "Active" : "Inactive";
    }

    public String getDurationDisplay() {
        if (duration < 60) {
            return duration + " minutes";
        } else {
            int hours = duration / 60;
            int minutes = duration % 60;
            return hours + "h " + (minutes > 0 ? minutes + "m" : "");
        }
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", quizName='" + quizName + '\'' +
                ", description='" + description + '\'' +
                ", subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", level='" + level + '\'' +
                ", duration=" + duration +
                ", passRate=" + passRate +
                ", quizType='" + quizType + '\'' +
                ", numberOfQuestions=" + numberOfQuestions +
                ", isActive=" + isActive +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", createdBy=" + createdBy +
                ", createdByName='" + createdByName + '\'' +
                '}';
    }
} 