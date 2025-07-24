/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author The Shuyy
 */

public class Subjects implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer subjectId;
    
    private Boolean isActive;
   
    private String subjectImage;
    
    private Long price;
    
    private String subjectName;
    
    private String description;
   
    private Categories categoryId;
   
    private List<QuestionBank> questionBankList;
   
    private List<Quizzes> quizzesList;
   
    private List<Lessons> lessonsList;

    public Subjects() {
    }

    public Subjects(Integer subjectId, Boolean isActive, String subjectImage, Long price, String subjectName, Categories categoryId) {
        this.subjectId = subjectId;
        this.isActive = isActive;
        this.subjectImage = subjectImage;
        this.price = price;
        this.subjectName = subjectName;
        this.categoryId = categoryId;
    }
    
    

    public Subjects(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Subjects(Integer subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getSubjectImage() {
        return subjectImage;
    }

    public void setSubjectImage(String subjectImage) {
        this.subjectImage = subjectImage;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Categories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
    }

    public List<QuestionBank> getQuestionBankList() {
        return questionBankList;
    }

    public void setQuestionBankList(List<QuestionBank> questionBankList) {
        this.questionBankList = questionBankList;
    }

    public List<Quizzes> getQuizzesList() {
        return quizzesList;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setQuizzesList(List<Quizzes> quizzesList) {
        this.quizzesList = quizzesList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Lessons> getLessonsList() {
        return lessonsList;
    }

    public void setLessonsList(List<Lessons> lessonsList) {
        this.lessonsList = lessonsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectId != null ? subjectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subjects)) {
            return false;
        }
        Subjects other = (Subjects) object;
        if ((this.subjectId == null && other.subjectId != null) || (this.subjectId != null && !this.subjectId.equals(other.subjectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Subjects[ subjectId=" + subjectId + " ]";
    }
    
}
