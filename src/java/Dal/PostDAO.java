/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dal;


import Dal.DBContext;
import entity.Media;
import entity.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    private Connection conn;

    public PostDAO() {
        conn = DBContext.getInstance().getConnection();
    }

    // Lấy danh sách tất cả bài viết
    public List<Post> getAllPosts() {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT * FROM post";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Post p = new Post();
                p.setPostId(rs.getInt("post_id"));
                p.setTitle(rs.getString("title"));
                p.setBrief(rs.getString("brief"));
                p.setDescription(rs.getString("description"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setFeatured(rs.getBoolean("featured"));
                p.setStatus(rs.getString("status"));
                p.setCreatedBy(rs.getInt("created_by"));
                p.setCreatedAt(rs.getTimestamp("created_at"));
                p.setUpdatedAt(rs.getTimestamp("updated_at"));

                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy bài viết theo ID
    public Post getPostById(int postId) {
        String sql = "SELECT * FROM post WHERE post_id = ?";
        Post p = null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new Post();
                p.setPostId(rs.getInt("post_id"));
                p.setTitle(rs.getString("title"));
                p.setBrief(rs.getString("brief"));
                p.setDescription(rs.getString("description"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setFeatured(rs.getBoolean("featured"));
                p.setStatus(rs.getString("status"));
                p.setCreatedBy(rs.getInt("created_by"));
                p.setCreatedAt(rs.getTimestamp("created_at"));
                p.setUpdatedAt(rs.getTimestamp("updated_at"));
                p.setMediaList(getMediaByPostId(postId)); // lấy danh sách media luôn
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

    // Lấy danh sách media theo post_id
    public List<Media> getMediaByPostId(int postId) {
        List<Media> list = new ArrayList<>();
        String sql = "SELECT * FROM media WHERE post_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Media m = new Media();
                m.setMediaId(rs.getInt("media_id"));
                m.setPostId(postId);
                m.setMediaUrl(rs.getString("media_url"));
                m.setMediaType(rs.getString("media_type"));
                m.setCaption(rs.getString("caption"));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm bài viết mới (trả về id của post vừa thêm)
    public int addPost(Post p) {
        String sql = "INSERT INTO post (title, brief, description, category_id, featured, status, created_by, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";
        ResultSet rs = null;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getBrief());
            ps.setString(3, p.getDescription());
            ps.setInt(4, p.getCategoryId());
            ps.setBoolean(5, p.isFeatured());
            ps.setString(6, p.getStatus());
            ps.setInt(7, p.getCreatedBy());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // trả về ID vừa insert
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // lỗi
    }

    // Thêm media
    public void addMedia(Media m) {
        String sql = "INSERT INTO media (post_id, media_url, media_type, caption) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getPostId());
            ps.setString(2, m.getMediaUrl());
            ps.setString(3, m.getMediaType());
            ps.setString(4, m.getCaption());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật bài viết
    public void updatePost(Post p) {
        String sql = "UPDATE post SET title = ?, brief = ?, description = ?, category_id = ?, featured = ?, status = ?, updated_at = GETDATE() WHERE post_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getBrief());
            ps.setString(3, p.getDescription());
            ps.setInt(4, p.getCategoryId());
            ps.setBoolean(5, p.isFeatured());
            ps.setString(6, p.getStatus());
            ps.setInt(7, p.getPostId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa toàn bộ media theo post (dùng trước khi update)
    public void deleteMediaByPostId(int postId) {
        String sql = "DELETE FROM media WHERE post_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

