/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

/**
 *
 * @author The Shuyy
 */
public class Users implements Serializable {
    private Integer userId;
    private Date createdDate;
    private String description;
    private String email;
    private String fullName;
    private boolean isActive;
    private String password;
    private String profileImage;
    private Date updatedDate;
    private Date dateOfBirth;
    private boolean gender;
    private String phone;
    private List<ResetToken> resetTokenList;
    private List<Blogs> blogsList;
    private List<QuizResults> quizResultsList;
    private int roleId;
    private List<Feedback> feedbackList;
    private List<Payment> paymentList;
    private List<Quizzes> quizzesList;

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Users(Integer userId, String email, String fullName, boolean isActive, String password, boolean gender) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.isActive = isActive;
        this.password = password;
        this.gender = gender;
    }

    public Users(Integer userId, Date createdDate, String description, String email, String fullName, boolean isActive, String password, String profileImage, Date updatedDate, Date dateOfBirth, boolean gender, String phone) {
        this.userId = userId;
        this.createdDate = createdDate;
        this.description = description;
        this.email = email;
        this.fullName = fullName;
        this.isActive = isActive;
        this.password = password;
        this.profileImage = profileImage;
        this.updatedDate = updatedDate;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ResetToken> getResetTokenList() {
        return resetTokenList;
    }

    public void setResetTokenList(List<ResetToken> resetTokenList) {
        this.resetTokenList = resetTokenList;
    }

    public List<Blogs> getBlogsList() {
        return blogsList;
    }

    public void setBlogsList(List<Blogs> blogsList) {
        this.blogsList = blogsList;
    }

    public List<QuizResults> getQuizResultsList() {
        return quizResultsList;
    }

    public void setQuizResultsList(List<QuizResults> quizResultsList) {
        this.quizResultsList = quizResultsList;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<Quizzes> getQuizzesList() {
        return quizzesList;
    }

    public void setQuizzesList(List<Quizzes> quizzesList) {
        this.quizzesList = quizzesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Users[ userId=" + userId + " ]";
    }
    
}
