/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;
import java.util.Date;


import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author The Shuyy
 */

public class QuizResults implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer resultId;
   
    private Date completedAt;
   
    private double score;
    
    private Quizzes quizId;
   
    private Users userId;
   
    private List<UserAnswers> userAnswersList;

    public QuizResults() {
    }

    public QuizResults(Integer resultId) {
        this.resultId = resultId;
    }

    public QuizResults(Integer resultId, Date completedAt, double score) {
        this.resultId = resultId;
        this.completedAt = completedAt;
        this.score = score;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Quizzes getQuizId() {
        return quizId;
    }

    public void setQuizId(Quizzes quizId) {
        this.quizId = quizId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @XmlTransient
    public List<UserAnswers> getUserAnswersList() {
        return userAnswersList;
    }

    public void setUserAnswersList(List<UserAnswers> userAnswersList) {
        this.userAnswersList = userAnswersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resultId != null ? resultId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuizResults)) {
            return false;
        }
        QuizResults other = (QuizResults) object;
        if ((this.resultId == null && other.resultId != null) || (this.resultId != null && !this.resultId.equals(other.resultId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.QuizResults[ resultId=" + resultId + " ]";
    }
    
}
