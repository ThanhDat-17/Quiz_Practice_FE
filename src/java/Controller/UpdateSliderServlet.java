package Controller;

import Dal.DBContext;
import Dal.SliderDAO;
import entity.Slider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;

@WebServlet("/update-slider")
@MultipartConfig
public class UpdateSliderServlet extends HttpServlet {

    // ⬅️ GET: Hiển thị form sửa slider
   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        int id = Integer.parseInt(request.getParameter("id")); // Lấy ID từ URL
        Connection conn = DBContext.getInstance().getConnection();
        SliderDAO dao = new SliderDAO(conn);
        Slider slider = dao.getSliderById(id); // Chỉ lấy 1 slider

        if (slider == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Slider không tồn tại");
            return;
        }

        request.setAttribute("slider", slider);
        request.getRequestDispatcher("sliderDetail.jsp").forward(request, response); // ✅ đúng trang form edit
    } catch (Exception e) {
        e.printStackTrace();
        throw new ServletException("Lỗi khi hiển thị form sửa slider", e);
    }
}



    // ⬅️ POST: Xử lý cập nhật slider
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("slider_id"));
            String title = request.getParameter("title");
            String backlink = request.getParameter("backlink");
            String status = request.getParameter("status");
            String notes = request.getParameter("notes");

            // Xử lý ảnh
            String imageUrl = handleImageUpload(request, "imageFile", request.getParameter("oldImage"));

            // Tạo slider cập nhật
            Slider updated = new Slider(id, title, imageUrl, backlink, status, notes);

            // Cập nhật DB
            Connection conn = DBContext.getInstance().getConnection();
            SliderDAO dao = new SliderDAO(conn);
            dao.updateSlider(updated);

            response.sendRedirect("slider-list");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi cập nhật slider", e);
        }
    }

    // 👇 Tách riêng xử lý upload ảnh cho gọn
    private String handleImageUpload(HttpServletRequest request, String partName, String oldImage)
            throws IOException, ServletException {

        Part imagePart = request.getPart(partName);
        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

        if (fileName != null && !fileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("/uploads");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String filePath = uploadPath + File.separator + fileName;
            imagePart.write(filePath);

            return "uploads/" + fileName;
        } else {
            return oldImage; // Nếu không upload mới → giữ nguyên
        }
    }
}
