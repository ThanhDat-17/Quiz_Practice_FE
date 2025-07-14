package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import service.UserDAO;

@MultipartConfig(maxFileSize = 10 * 1024 * 1024) // 10MB
public class UploadServlet extends HttpServlet {

    private Cloudinary cloudinary;

    @Override
    public void init() throws ServletException {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dssebzgq0");
        config.put("api_key", "197385871824946");
        config.put("api_secret", "9a78oRn7Ayn3TKlYAKYq_cJutvo");
        cloudinary = new Cloudinary(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("avatar");
        if (filePart == null || filePart.getSize() == 0) {
            response.getWriter().println("Không có file nào được chọn.");
            return;
        }

        File tempFile = File.createTempFile("avatar_", ".tmp");
        filePart.write(tempFile.getAbsolutePath());

        try {
            Map uploadResult = cloudinary.uploader().upload(tempFile,
                    ObjectUtils.asMap("folder", "user_avatars"));

            String imageUrl = (String) uploadResult.get("secure_url");

            Users user = (Users) request.getSession().getAttribute("user");
            user.setProfileImage(imageUrl);
            new UserDAO().update(user);

            Users u = new UserDAO().getUserByUserAndPass(user.getEmail(), user.getPassword());
            request.getSession().setAttribute("user", u);
            response.sendRedirect("profile");
        } catch (IOException e) {
            response.getWriter().println("Upload failed: " + e.getMessage());
        } finally {
            tempFile.delete(); // cleanup
        }
    }
}
