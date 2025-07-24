/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;


/**
 *
 * @author The Shuyy
 */
public class UserAnswers implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer answerId;
    private boolean isCorrect;
    private AnswerOption selectedOptionId;
    private QuestionBank questionId;
    private QuizResults resultId;

    public UserAnswers() {
    }

    public UserAnswers(Integer answerId) {
        this.answerId = answerId;
    }

    public UserAnswers(Integer answerId, boolean isCorrect) {
        this.answerId = answerId;
        this.isCorrect = isCorrect;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public AnswerOption getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(AnswerOption selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }

    public QuestionBank getQuestionId() {
        return questionId;
    }

    public void setQuestionId(QuestionBank questionId) {
        this.questionId = questionId;
    }

    public QuizResults getResultId() {
        return resultId;
    }

    public void setResultId(QuizResults resultId) {
        this.resultId = resultId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerId != null ? answerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAnswers)) {
            return false;
        }
        UserAnswers other = (UserAnswers) object;
        if ((this.answerId == null && other.answerId != null) || (this.answerId != null && !this.answerId.equals(other.answerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UserAnswers[ answerId=" + answerId + " ]";
    }
    
}
