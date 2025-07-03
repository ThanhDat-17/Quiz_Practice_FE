package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class QuestionOption implements Serializable {
    private int optionId;
    private int questionId;
    private String optionText;
    private boolean isCorrect;
    private int optionOrder;
    private Timestamp createdDate;

    // Default constructor
    public QuestionOption() {}

    // Constructor with essential fields
    public QuestionOption(int questionId, String optionText, boolean isCorrect, int optionOrder) {
        this.questionId = questionId;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.optionOrder = optionOrder;
    }

    // Getters and Setters
    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(int optionOrder) {
        this.optionOrder = optionOrder;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "QuestionOption{" +
                "optionId=" + optionId +
                ", questionId=" + questionId +
                ", optionText='" + optionText + '\'' +
                ", isCorrect=" + isCorrect +
                ", optionOrder=" + optionOrder +
                '}';
    }
} 