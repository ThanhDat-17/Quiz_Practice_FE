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

public class AnswerOption implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer optionId;
  
    private String optionContent;
   
    private boolean isCorrect;
  
    private QuestionBank questionId;
   
    private List<UserAnswers> userAnswersList;

    public AnswerOption() {
    }

    public AnswerOption(Integer optionId) {
        this.optionId = optionId;
    }

    public AnswerOption(Integer optionId, String optionContent, boolean isCorrect) {
        this.optionId = optionId;
        this.optionContent = optionContent;
        this.isCorrect = isCorrect;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public QuestionBank getQuestionId() {
        return questionId;
    }

    public void setQuestionId(QuestionBank questionId) {
        this.questionId = questionId;
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
        hash += (optionId != null ? optionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnswerOption)) {
            return false;
        }
        AnswerOption other = (AnswerOption) object;
        if ((this.optionId == null && other.optionId != null) || (this.optionId != null && !this.optionId.equals(other.optionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AnswerOption[ optionId=" + optionId + " ]";
    }
    
}
