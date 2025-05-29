/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class Users {
     private int user_id;
private Date created_date;
 private String description;
 private String email;
 private String full_name;
 private boolean is_active;
  private String password;
  private String profile_image;
   private Date updated_date;
    private Date date_of_birth;
     private boolean gender;
     private String phone;

    public Users() {
    }

    public Users(int user_id, Date created_date, String description, String email, String full_name, boolean is_active, String password, String profile_image, Date updated_date, Date date_of_birth, boolean gender, String phone) {
        this.user_id = user_id;
        this.created_date = created_date;
        this.description = description;
        this.email = email;
        this.full_name = full_name;
        this.is_active = is_active;
        this.password = password;
        this.profile_image = profile_image;
        this.updated_date = updated_date;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.phone = phone;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
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

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public boolean isGender() {
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

    @Override
    public String toString() {
        return "Users{" + "user_id=" + user_id + ", created_date=" + created_date + ", description=" + description + ", email=" + email + ", full_name=" + full_name + ", is_active=" + is_active + ", password=" + password + ", profile_image=" + profile_image + ", updated_date=" + updated_date + ", date_of_birth=" + date_of_birth + ", gender=" + gender + ", phone=" + phone + '}';
    }
     
     
}
