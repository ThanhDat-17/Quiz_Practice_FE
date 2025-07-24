package service;

import entity.Blogs;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ulti.DBContext;

public class BlogDAO {

    // Lấy tất cả blogs với phân trang
    public List<Blogs> getAllBlogs(int page, int pageSize) {
        List<Blogs> blogs = new ArrayList<>();
        String sql = "SELECT b.blog_id, b.content, b.created_date, b.image, b.is_active, b.title, b.created_by, u.full_name "
                + "FROM blogs b LEFT JOIN users u ON b.created_by = u.user_id "
                + "ORDER BY b.created_date DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);

            ResultSet rs = ps.executeQuery();
            UserDAO dao = new UserDAO();
            while (rs.next()) {
                Blogs blog = new Blogs();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedDate(rs.getDate("created_date"));
                blog.setImage(rs.getString("image"));
                blog.setIsActive(rs.getBoolean("is_active"));
                blog.setTitle(rs.getString("title"));
                blog.setCreatedBy(dao.getUserById(rs.getInt("created_by")));
                blogs.add(blog);
            }
        } catch (Exception e) {
        }
        return blogs;
    }

    // Đếm tổng số blogs
    public int getTotalBlogsCount() {
        String sql = "SELECT COUNT(*) FROM blogs";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

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
    public Blogs getBlogById(int blogId) {
        String sql = "SELECT b.blog_id, b.content, b.created_date, b.image, b.is_active, b.title, b.created_by, u.full_name "
                + "FROM blogs b LEFT JOIN users u ON b.created_by = u.user_id "
                + "WHERE b.blog_id = ?";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();
            UserDAO dao = new UserDAO();
            if (rs.next()) {
                Blogs blog = new Blogs();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedDate(rs.getDate("created_date"));
                blog.setImage(rs.getString("image"));
                blog.setIsActive(rs.getBoolean("is_active"));
                blog.setTitle(rs.getString("title"));
                blog.setCreatedBy(dao.getUserById(rs.getInt("created_by")));
                return blog;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm blog mới
    public boolean insertBlog(Blogs blog) {
        String sql = "INSERT INTO blogs (content, created_date, image, is_active, title, created_by) VALUES (?, ?, ?, ?, ?, ?)";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, blog.getContent());
            ps.setDate(2, java.sql.Date.valueOf(blog.getCreatedDate().toString()));
            ps.setString(3, blog.getImage());
            ps.setBoolean(4, blog.getIsActive());
            ps.setString(5, blog.getTitle());
            ps.setInt(6, blog.getCreatedBy().getUserId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật blog
    public boolean updateBlog(Blogs blog) {
        String sql = "UPDATE blogs SET content = ?, image = ?, is_active = ?, title = ? WHERE blog_id = ?";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, blog.getContent());
            ps.setString(2, blog.getImage());
            ps.setBoolean(3, blog.getIsActive());
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

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, blogId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Tìm kiếm blogs theo title
    public List<Blogs> searchBlogs(String keyword, int page, int pageSize) {
        List<Blogs> blogs = new ArrayList<>();
        String sql = "SELECT b.blog_id, b.content, b.created_date, b.image, b.is_active, b.title, b.created_by, u.full_name "
                + "FROM blogs b LEFT JOIN users u ON b.created_by = u.user_id "
                + "WHERE b.title LIKE ? OR b.content LIKE ? "
                + "ORDER BY b.created_date DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            ps.setString(1, searchKeyword);
            ps.setString(2, searchKeyword);
            ps.setInt(3, (page - 1) * pageSize);
            ps.setInt(4, pageSize);
            UserDAO dao = new UserDAO();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blogs blog = new Blogs();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedDate(rs.getDate("created_date"));
                blog.setImage(rs.getString("image"));
                blog.setIsActive(rs.getBoolean("is_active"));
                blog.setTitle(rs.getString("title"));
                blog.setCreatedBy(dao.getUserById(rs.getInt("created_by")));
                blogs.add(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogs;
    }

    // Lấy recent blogs
    public List<Blogs> getRecentBlogs(int limit) {
        List<Blogs> blogs = new ArrayList<>();
        String sql = "SELECT TOP(?) b.blog_id, b.content, b.created_date, b.image, b.is_active, b.title, b.created_by, u.full_name "
                + "FROM blogs b LEFT JOIN users u ON b.created_by = u.user_id "
                + "WHERE b.is_active = 1 "
                + "ORDER BY b.created_date DESC";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            UserDAO dao = new UserDAO();
            while (rs.next()) {
                Blogs blog = new Blogs();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setContent(rs.getString("content"));
                blog.setCreatedDate(rs.getDate("created_date"));
                blog.setImage(rs.getString("image"));
                blog.setIsActive(rs.getBoolean("is_active"));
                blog.setTitle(rs.getString("title"));
                blog.setCreatedBy(dao.getUserById(rs.getInt("created_by")));
                blogs.add(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogs;
    }

    // Đếm số lượng blogs tìm kiếm được
    public int getSearchBlogsCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM blogs b "
                + "WHERE b.title LIKE ? OR b.content LIKE ?";

        try ( Connection conn = new DBContext().getConnection();   PreparedStatement ps = conn.prepareStatement(sql)) {

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
