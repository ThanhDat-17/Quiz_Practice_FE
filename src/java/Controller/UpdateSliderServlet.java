package Controller;

import Dal.DBContext;
import Dal.SliderDAO;
import entity.Slider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/slider-form")
@MultipartConfig
public class UpdateSliderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            Slider slider = null;
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Connection conn = DBContext.getInstance().getConnection();
                SliderDAO dao = new SliderDAO(conn);
                slider = dao.getSliderById(id);
                if (slider == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Slider not found");
                    return;
                }
            }
            request.setAttribute("slider", slider);
            request.getRequestDispatcher("/admin/slider-form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error displaying slider form", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String idParam = request.getParameter("slider_id");
            String action = request.getParameter("action");
            String title = request.getParameter("title");
            String backlink = request.getParameter("backlink");
            String status = request.getParameter("status");
            String notes = request.getParameter("notes");
            String oldImage = request.getParameter("oldImage");
            String imageUrl = handleImageUpload(request, "imageFile", oldImage);

            Connection conn = DBContext.getInstance().getConnection();
            SliderDAO dao = new SliderDAO(conn);

            if ("delete".equals(action) && idParam != null && !idParam.isEmpty()) {
                int id = Integer.parseInt(request.getParameter("id") != null ? request.getParameter("id") : idParam);
                dao.deleteSlider(id);
                response.sendRedirect(request.getContextPath() + "/admin/slider-list");
                return;
            }

            if (idParam != null && !idParam.isEmpty()) {
                // Update
                int id = Integer.parseInt(idParam);
                Slider updated = new Slider(id, title, imageUrl, backlink, status, notes);
                dao.updateSlider(updated);
            } else {
                // Insert
                Slider created = new Slider(0, title, imageUrl, backlink, status, notes);
                dao.insertSlider(created);
            }
            response.sendRedirect(request.getContextPath() + "/admin/slider-list");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error saving slider", e);
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
