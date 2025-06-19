package model;

import java.io.Serializable;

public class Subject implements Serializable {
    private int subjectId;
    private String subjectName;
    private String subjectImage;
    private String description;
    private boolean isActive;
    private int categoryId;
    private String categoryName; // For display purposes

    // Default constructor
    public Subject() {}

    // Constructor with all fields
    public Subject(int subjectId, String subjectName, String subjectImage, String description, boolean isActive, int categoryId) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectImage = subjectImage;
        this.description = description;
        this.isActive = isActive;
        this.categoryId = categoryId;
    }

    // Getters and Setters
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

    public String getSubjectImage() {
        return subjectImage;
    }

    public void setSubjectImage(String subjectImage) {
        this.subjectImage = subjectImage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", subjectImage='" + subjectImage + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
} 