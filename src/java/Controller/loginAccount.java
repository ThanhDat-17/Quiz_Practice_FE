/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dal.LoginDAO;
import entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.validation;

/**
 *
 * @author Asus
 */
@WebServlet(name = "loginAccount", urlPatterns = {"/loginAccount"})
public class loginAccount extends HttpServlet {

    @Override
    //Hiển thị trang đăng nhập (login.jsp) kèm theo thông báo lỗi (nếu có)//
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("error") != null) {
            String error = (String) session.getAttribute("error");
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession();

    // Lấy thông tin người dùng từ form, loại bỏ khoảng trắng
    String username = request.getParameter("username").trim();
    String password = request.getParameter("password").trim();

    // Kiểm tra định dạng email
    if (!validation.isValidEmail(username)) {
        request.setAttribute("error", "Incorrect email format (missing @ or .).");
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    LoginDAO loginDAO = new LoginDAO();
    Users userByEmail = loginDAO.getUserByEmail(username);

    if (userByEmail == null) {
        // Email không tồn tại trong database
        request.setAttribute("error", "Incorrect or unregistered account.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    // Kiểm tra mật khẩu
    if (!userByEmail.getPassword().equals(password)) {
        request.setAttribute("error", "Password is incorrect.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    // nếu tk bị khoá
    if (!userByEmail.getIsActive()) {
        request.setAttribute("error", "Account locked.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    // Đăng nhập thành công
    session.setAttribute("user", userByEmail);

    switch (userByEmail.getRoleId()) {
        case 1:
            response.sendRedirect("customer.jsp");
            break;
        case 2:
            response.sendRedirect("expert.jsp");
            break;
        case 3:
            response.sendRedirect("subjectDimension");
            break;
        case 4:
            response.sendRedirect("sale.jsp");
            break;
        case 5:
            response.sendRedirect("marketing screen.jsp");
            break;
        default:
            response.sendRedirect("login.jsp");
            break;
    }
}
}