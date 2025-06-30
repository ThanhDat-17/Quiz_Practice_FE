package model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Blog {
    private int blogId;
    private String content;
    private LocalDate createdDate;
    private String image;
    private boolean isActive;
    private String title;
    private int createdBy;
    private String authorName; // Để hiển thị tên tác giả

    public Blog() {}

    public Blog(int blogId, String content, LocalDate createdDate, String image, 
                boolean isActive, String title, int createdBy) {
        this.blogId = blogId;
        this.content = content;
        this.createdDate = createdDate;
        this.image = image;
        this.isActive = isActive;
        this.title = title;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    // Utility method to get short content for listing
    public String getShortContent() {
        if (content == null) return "";
        return content.length() > 150 ? content.substring(0, 150) + "..." : content;
    }

    // Helper method to convert LocalDate to Date for JSP fmt:formatDate
    public Date getCreatedDateAsDate() {
        if (createdDate == null) return null;
        return Date.from(createdDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
} 