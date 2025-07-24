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
}
