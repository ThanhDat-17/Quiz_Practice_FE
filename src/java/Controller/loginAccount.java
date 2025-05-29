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
// đăng nhập sai 5 lần sẽ phải đợi 1 phút //
        Integer loginAttempts = (Integer) session.getAttribute("loginAttempts");  // số lần đăng nhập sai liên tiếp
        Long lockTime = (Long) session.getAttribute("lockTime");   // thời điểm bắt đầu khoá
// thời gian hiên tại - tgian khoá < 60s, tbao khoá và chuyển về trang login
        if (lockTime != null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lockTime < 60 * 1000) {
                request.setAttribute("error", "You have entered incorrect credentials more than 5 times. Please try again after 1 minute.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {
 // hết tgian thì xoá 2 thuộc tính và bắt đầu đăng nhập lại
                session.removeAttribute("lockTime");
                session.removeAttribute("loginAttempts");
                loginAttempts = 0;
            }
        }
// lấy tt người dùng từ login, loại bỏ khoảng trắng
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        LoginDAO loginDAO = new LoginDAO();
        Users users = loginDAO.getUserByUserAndPass(username, password);
// nếu đăng nhập sai
        if (users == null) {
            if (loginAttempts == null) {
                loginAttempts = 1;
            } else {
                loginAttempts++;
            }

            session.setAttribute("loginAttempts", loginAttempts);

            if (loginAttempts >= 5) {
                session.setAttribute("lockTime", System.currentTimeMillis());
                request.setAttribute("error", "You have entered incorrect credentials more than 5 times. Please try again after 1 minute.");
            } else {
                request.setAttribute("error", "Incorrect email or password.");
            }
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
//nếu tk bị khoá
        if (!users.getIsActive()) {
            request.setAttribute("error", "Account is locked.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

// Đăng nhập thành công
        session.removeAttribute("loginAttempts");
        session.removeAttribute("lockTime");
        session.setAttribute("user", users);

        switch (users.getRoleId()) {
    case 1:
        response.sendRedirect("customer.jsp");
        break;
    case 2:
        response.sendRedirect("expert.jsp");
        break;
    case 3:
        response.sendRedirect("admin.jsp");
        break;
    case 4:
        response.sendRedirect("sale.jsp");
        break;
    case 5:
        response.sendRedirect("marketing screen.jsp"); 
        break;
}
    }



    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

