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

public class Lessons implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer lessonId;
  
    private Date createdDate;
   
    private String lessonName;
   
    private Date updatedDate;
   
    private List<QuestionBank> questionBankList;
    
    private List<Quizzes> quizzesList;
    
    private Subjects subjectId;

    public Lessons() {
    }

    public Lessons(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Lessons(Integer lessonId, String lessonName) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
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
    public List<Quizzes> getQuizzesList() {
        return quizzesList;
    }

    public void setQuizzesList(List<Quizzes> quizzesList) {
        this.quizzesList = quizzesList;
    }

    public Subjects getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Subjects subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lessonId != null ? lessonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lessons)) {
            return false;
        }
        Lessons other = (Lessons) object;
        if ((this.lessonId == null && other.lessonId != null) || (this.lessonId != null && !this.lessonId.equals(other.lessonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Lessons[ lessonId=" + lessonId + " ]";
    }
    
}
