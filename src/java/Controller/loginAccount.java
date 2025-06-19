/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.LoginDAO;
import model.Users;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
@WebServlet(name = "loginAccount", urlPatterns = {"/loginAccount"})
public class loginAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("error") != null) {
            String error = (String) session.getAttribute("error");
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }
        request.getRequestDispatcher("index.html").forward(request, response);
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            
            // Kiểm tra số lần đăng nhập sai và thời điểm bị khóa
            Integer loginAttempts = (Integer) session.getAttribute("loginAttempts");
            Long lockTime = (Long) session.getAttribute("lockTime");
            
            // Nếu đang bị khóa đăng nhập
            if (lockTime != null) {
                long currentTime = System.currentTimeMillis();
                long timePassed = currentTime - lockTime;
                
                if (timePassed < 60 * 1000) { // chưa đủ 1 phút
                    request.setAttribute("error", "You have entered incorrect credentials more than 5 times. Please try again after 1 minute..");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                } else {
                    // Đã qua 1 phút: reset lại số lần thử và thời gian khóa
                    session.removeAttribute("lockTime");
                    session.removeAttribute("loginAttempts");
                    loginAttempts = 0;
                }
            }
            
            // Xử lý đăng nhập
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password").trim();
            LoginDAO loginDAO = new LoginDAO();
            Users users = loginDAO.getUserByUserAndPass(username, password);
            
            if (users == null) {
                if (loginAttempts == null) {
                    loginAttempts = 1;
                } else {
                    loginAttempts++;
                }
                
                session.setAttribute("loginAttempts", loginAttempts);
                
                if (loginAttempts >= 5) {
                    session.setAttribute("lockTime", System.currentTimeMillis());
                    request.setAttribute("error", "You have entered incorrect credentials more than 5 times. Please try again after 1 minute..");
                } else {
                    request.setAttribute("error", "Incorrect email or password.");
                }
                
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Đăng nhập thành công => xóa dữ liệu tạm trong session
                session.removeAttribute("loginAttempts");
                session.removeAttribute("lockTime");
                session.setAttribute("user", users);
                response.sendRedirect("courses.jsp");
            }   } catch (ClassNotFoundException ex) {
            Logger.getLogger(loginAccount.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(loginAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
}


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
