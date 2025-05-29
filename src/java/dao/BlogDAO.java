package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import utils.DBContext;

public class BlogDAO {

    // Lấy tất cả blogs với phân trang
    public List<Blog> getAllBlogs(int page, int pageSize) {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT b.blog_id, b.content, b.created_date, b.image, b.is_active, b.title, b.created_by, u.full_name " +
                     "FROM blogs b LEFT JOIN users u ON b.created_by = u.user_id " +
                     "ORDER BY b.created_date DESC " +
                     "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedDate(rs.getDate("created_date").toLocalDate());
                blog.setImage(rs.getString("image"));
                blog.setActive(rs.getBoolean("is_active"));
                blog.setTitle(rs.getString("title"));
                blog.setCreatedBy(rs.getInt("created_by"));
                blog.setAuthorName(rs.getString("full_name"));
                blogs.add(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogs;
    }

    // Đếm tổng số blogs
    public int getTotalBlogsCount() {
        String sql = "SELECT COUNT(*) FROM blogs";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy blog theo ID
    public Blog getBlogById(int blogId) {
        String sql = "SELECT b.blog_id, b.content, b.created_date, b.image, b.is_active, b.title, b.created_by, u.full_name " +
                     "FROM blogs b LEFT JOIN users u ON b.created_by = u.user_id " +
                     "WHERE b.blog_id = ?";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedDate(rs.getDate("created_date").toLocalDate());
                blog.setImage(rs.getString("image"));
                blog.setActive(rs.getBoolean("is_active"));
                blog.setTitle(rs.getString("title"));
                blog.setCreatedBy(rs.getInt("created_by"));
                blog.setAuthorName(rs.getString("full_name"));
                return blog;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm blog mới
    public boolean insertBlog(Blog blog) {
        String sql = "INSERT INTO blogs (content, created_date, image, is_active, title, created_by) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, blog.getContent());
            ps.setDate(2, Date.valueOf(blog.getCreatedDate()));
            ps.setString(3, blog.getImage());
            ps.setBoolean(4, blog.isActive());
            ps.setString(5, blog.getTitle());
            ps.setInt(6, blog.getCreatedBy());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật blog
    public boolean updateBlog(Blog blog) {
        String sql = "UPDATE blogs SET content = ?, image = ?, is_active = ?, title = ? WHERE blog_id = ?";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, blog.getContent());
            ps.setString(2, blog.getImage());
            ps.setBoolean(3, blog.isActive());
            ps.setString(4, blog.getTitle());
            ps.setInt(5, blog.getBlogId());
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa blog
    public boolean deleteBlog(int blogId) {
        String sql = "DELETE FROM blogs WHERE blog_id = ?";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, blogId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tìm kiếm blogs theo title
    public List<Blog> searchBlogs(String keyword, int page, int pageSize) {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT b.blog_id, b.content, b.created_date, b.image, b.is_active, b.title, b.created_by, u.full_name " +
                     "FROM blogs b LEFT JOIN users u ON b.created_by = u.user_id " +
                     "WHERE b.title LIKE ? OR b.content LIKE ? " +
                     "ORDER BY b.created_date DESC " +
                     "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchKeyword = "%" + keyword + "%";
            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);
            ps.setInt(3, (page - 1) * pageSize);
            ps.setInt(4, pageSize);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedDate(rs.getDate("created_date").toLocalDate());
                blog.setImage(rs.getString("image"));
                blog.setActive(rs.getBoolean("is_active"));
                blog.setTitle(rs.getString("title"));
                blog.setCreatedBy(rs.getInt("created_by"));
                blog.setAuthorName(rs.getString("full_name"));
                blogs.add(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogs;
    }

    // Lấy recent blogs
    public List<Blog> getRecentBlogs(int limit) {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT TOP(?) b.blog_id, b.content, b.created_date, b.image, b.is_active, b.title, b.created_by, u.full_name " +
                     "FROM blogs b LEFT JOIN users u ON b.created_by = u.user_id " +
                     "WHERE b.is_active = 1 " +
                     "ORDER BY b.created_date DESC";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedDate(rs.getDate("created_date").toLocalDate());
                blog.setImage(rs.getString("image"));
                blog.setActive(rs.getBoolean("is_active"));
                blog.setTitle(rs.getString("title"));
                blog.setCreatedBy(rs.getInt("created_by"));
                blog.setAuthorName(rs.getString("full_name"));
                blogs.add(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogs;
    }

    // Đếm số lượng blogs tìm kiếm được
    public int getSearchBlogsCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM blogs b " +
                     "WHERE b.title LIKE ? OR b.content LIKE ?";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchKeyword = "%" + keyword + "%";
            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
} 