package Dal;

import entity.Slider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SliderDAO {
    private final Connection conn;

    public SliderDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Slider> getAllSliders() throws SQLException {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM sliders";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Slider s = new Slider(
                    rs.getInt("slider_id"),
                    rs.getString("title"),
                    rs.getString("image"),
                    rs.getString("backlink"),
                    rs.getString("status"),
                    rs.getString("notes")
                );
                sliders.add(s);
            }
        }
        return sliders;
    }

    public Slider getSliderById(int id) throws SQLException {
        String sql = "SELECT * FROM sliders WHERE slider_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Slider(
                        rs.getInt("slider_id"),
                        rs.getString("title"),
                        rs.getString("image"),
                        rs.getString("backlink"),
                        rs.getString("status"),
                        rs.getString("notes")
                    );
                }
            }
        }
        return null;
    }

    public void updateSlider(Slider s) throws SQLException {
        String sql = "UPDATE sliders SET title=?, image=?, backlink=?, status=?, notes=?, updated_at=GETDATE() WHERE slider_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getTitle());
            ps.setString(2, s.getImage());
            ps.setString(3, s.getBacklink());
            ps.setString(4, s.getStatus());
            ps.setString(5, s.getNotes());
            ps.setInt(6, s.getSlider_id());
            ps.executeUpdate();
        }
    }

    // New method: Get filtered, searched, and paginated sliders
    public List<Slider> getSliders(String status, String search, int offset, int limit) throws SQLException {
        List<Slider> sliders = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM sliders WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (status != null && !status.equalsIgnoreCase("all")) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR backlink LIKE ?)");
            params.add("%" + search + "%");
            params.add("%" + search + "%");
        }
        sql.append(" ORDER BY slider_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add(offset);
        params.add(limit);

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Slider s = new Slider(
                        rs.getInt("slider_id"),
                        rs.getString("title"),
                        rs.getString("image"),
                        rs.getString("backlink"),
                        rs.getString("status"),
                        rs.getString("notes")
                    );
                    sliders.add(s);
                }
            }
        }
        return sliders;
    }

    // New method: Count total sliders for pagination (with filter/search)
    public int countSliders(String status, String search) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM sliders WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (status != null && !status.equalsIgnoreCase("all")) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (title LIKE ? OR backlink LIKE ?)");
            params.add("%" + search + "%");
            params.add("%" + search + "%");
        }
        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // New method: Update slider status only (show/hide)
    public void updateSliderStatus(int sliderId, String status) throws SQLException {
        String sql = "UPDATE sliders SET status = ?, updated_at = GETDATE() WHERE slider_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, sliderId);
            ps.executeUpdate();
        }
    }

    // Insert a new slider
    public void insertSlider(Slider s) throws SQLException {
        String sql = "INSERT INTO sliders (title, image, backlink, status, notes, updated_at) VALUES (?, ?, ?, ?, ?, GETDATE())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getTitle());
            ps.setString(2, s.getImage());
            ps.setString(3, s.getBacklink());
            ps.setString(4, s.getStatus());
            ps.setString(5, s.getNotes());
            ps.executeUpdate();
        }
    }

    // Delete a slider by id
    public void deleteSlider(int sliderId) throws SQLException {
        String sql = "DELETE FROM sliders WHERE slider_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sliderId);
            ps.executeUpdate();
        }
    }
}
