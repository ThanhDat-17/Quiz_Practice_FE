package Controller;

import Dal.UserDAO;
import entity.ResetToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/reset-password")
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        UserDAO userDAO = new UserDAO();
        ResetToken resetToken = userDAO.findResetToken(token);

        if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
            request.setAttribute("error", "Password reset link has expired!");
            request.getRequestDispatcher("/confirm email.jsp").forward(request, response);
            return;
        }

        request.setAttribute("token", token);
        request.getRequestDispatcher("/reset password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        UserDAO userDAO = new UserDAO();
        ResetToken resetToken = userDAO.findResetToken(token);

        if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
            request.setAttribute("error", "Password reset link has expired!");
            request.getRequestDispatcher("/confirm email.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match!");
            request.setAttribute("token", token);
            request.getRequestDispatcher("/reset password.jsp").forward(request, response);
            return;
        }

        // Cập nhật mật khẩu mới
        userDAO.updatePassword(resetToken.getUserId().getUserId(), password);
        userDAO.deleteResetToken(token);

        request.setAttribute("message", "Password has been reset successfully! Please login.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
