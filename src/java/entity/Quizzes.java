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

public class Quizzes implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer quizId;
   
    private Date createdDate;
   
    private String description;
   
   
    private boolean isActive;
   
   
    private String quizName;
   
    private Date updatedDate;
  
    private List<QuestionBank> questionBankList;
   
    private List<QuizResults> quizResultsList;
   
    private Lessons lessonId;
   
    private Subjects subjectId;
   
    private Users createdBy;

    public Quizzes() {
    }

    public Quizzes(Integer quizId) {
        this.quizId = quizId;
    }

    public Quizzes(Integer quizId, boolean isActive, String quizName) {
        this.quizId = quizId;
        this.isActive = isActive;
        this.quizName = quizName;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @XmlTransient
    public List<QuestionBank> getQuestionBankList() {
        return questionBankList;
    }

    public void setQuestionBankList(List<QuestionBank> questionBankList) {
        this.questionBankList = questionBankList;
    }

    @XmlTransient
    public List<QuizResults> getQuizResultsList() {
        return quizResultsList;
    }

    public void setQuizResultsList(List<QuizResults> quizResultsList) {
        this.quizResultsList = quizResultsList;
    }

    public Lessons getLessonId() {
        return lessonId;
    }

    public void setLessonId(Lessons lessonId) {
        this.lessonId = lessonId;
    }

    public Subjects getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Subjects subjectId) {
        this.subjectId = subjectId;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quizId != null ? quizId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quizzes)) {
            return false;
        }
        Quizzes other = (Quizzes) object;
        if ((this.quizId == null && other.quizId != null) || (this.quizId != null && !this.quizId.equals(other.quizId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Quizzes[ quizId=" + quizId + " ]";
    }
    
}
