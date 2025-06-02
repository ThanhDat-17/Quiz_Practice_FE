package Controller;

import Dal.UserDAO;
import entity.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import utils.EmailUtil;
import utils.validation;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("confirm email.jsp").forward(request, response);
    }
    
    
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String email = request.getParameter("email");
    UserDAO userDAO = new UserDAO();
    Users user = userDAO.findUserByEmail(email);

    // Kiểm tra định dạng email
        if (!validation.isValidEmail(email)) {
            request.setAttribute("error", "Incorrect email format (missing @ or .).");
            request.getRequestDispatcher("confirm email.jsp").forward(request, response);
            return;
        }
        
    // Nếu không tìm thấy email
    if (user == null) {
        request.setAttribute("error", "Email does not exist!");
        request.getRequestDispatcher("confirm email.jsp").forward(request, response);
        return;
    }


    // Tạo token và thời gian hết hạn (1 giờ)
    String token = UUID.randomUUID().toString();
    Date expiryDate = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));

    // Lưu token vào DB
    userDAO.saveResetToken(user.getUserId(), token, expiryDate);

    // Gửi email với link reset
    String resetLink = "http://localhost:9999" + request.getContextPath() + "/reset-password?token=" + token;
    String emailContent = "<h2>Reset Password</h2>"
            + "<p>Click the link below to reset your password:</p>"
            + "<a href='" + resetLink + "'>Reset Password</a>"
            + "<p>This link will expire in 1 hour.</p>";

    try {
        EmailUtil.sendEmail(email, "Reset Password Request", emailContent);
        request.setAttribute("message", "Password reset link has been sent to your email!");
    } catch (Exception e) {
        request.setAttribute("error", "Error sending email: " + e.getMessage());
        e.printStackTrace();
    }

    request.getRequestDispatcher("/confirm email.jsp").forward(request, response);
}

}
