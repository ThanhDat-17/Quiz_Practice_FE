/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;


import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author The Shuyy
 */

public class QuestionBank implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer questionId;
    
    private String questionContent;
    
    private String questionType;
   
    private List<Quizzes> quizzesList;
    
    private List<AnswerOption> answerOptionList;
   
    private List<UserAnswers> userAnswersList;
   
    private Lessons lessonId;
    
    private Subjects subjectId;

    public QuestionBank() {
    }

    public QuestionBank(Integer questionId) {
        this.questionId = questionId;
    }

    public QuestionBank(Integer questionId, String questionContent, String questionType) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.questionType = questionType;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @XmlTransient
    public List<Quizzes> getQuizzesList() {
        return quizzesList;
    }

    public void setQuizzesList(List<Quizzes> quizzesList) {
        this.quizzesList = quizzesList;
    }

    @XmlTransient
    public List<AnswerOption> getAnswerOptionList() {
        return answerOptionList;
    }

    public void setAnswerOptionList(List<AnswerOption> answerOptionList) {
        this.answerOptionList = answerOptionList;
    }

    @XmlTransient
    public List<UserAnswers> getUserAnswersList() {
        return userAnswersList;
    }

    public void setUserAnswersList(List<UserAnswers> userAnswersList) {
        this.userAnswersList = userAnswersList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionBank)) {
            return false;
        }
        QuestionBank other = (QuestionBank) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.QuestionBank[ questionId=" + questionId + " ]";
    }
    
}
