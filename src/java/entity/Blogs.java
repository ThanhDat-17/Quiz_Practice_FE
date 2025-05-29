/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author The Shuyy
 */

public class Blogs implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer blogId;
  
    private String content;
  
    private Date createdDate;
   
    private String image;
   
    private boolean isActive;
   
    private String title;
   
    private Users createdBy;

    public Blogs() {
    }

    public Blogs(Integer blogId) {
        this.blogId = blogId;
    }

    public Blogs(Integer blogId, String content, Date createdDate, boolean isActive, String title) {
        this.blogId = blogId;
        this.content = content;
        this.createdDate = createdDate;
        this.isActive = isActive;
        this.title = title;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        hash += (blogId != null ? blogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Blogs)) {
            return false;
        }
        Blogs other = (Blogs) object;
        if ((this.blogId == null && other.blogId != null) || (this.blogId != null && !this.blogId.equals(other.blogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Blogs[ blogId=" + blogId + " ]";
    }
    
}
