/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Categories;
import entity.Subjects;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import service.CategoryDAO;
import service.CourseDAO;

/**
 *
 * @author The Shuyy
 */
public class SubjectDetail extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String id = request.getParameter("id");
            String thumbnail = request.getParameter("thumbnail");
            String title = request.getParameter("title");
            String price = request.getParameter("price");

            String image = request.getParameter("image");
            String categoryID = request.getParameter("categoryID");
            String searchQuery = request.getParameter("search");  // Nhận giá trị tìm kiếm

            // Lấy pageSize từ request, mặc định là 6 nếu không có giá trị
            String pageNum = request.getParameter("page");
            int page = 1;
            if (pageNum != null) {
                page = Integer.parseInt(pageNum);
            }

            // Lấy pageSize từ request, mặc định là 6 nếu không có giá trị hoặc giá trị không hợp lệ (null hoặc 0)
            String pageSizeParam = request.getParameter("pageSize");
            int pageSize = 6;  // Giá trị mặc định là 6

            if (pageSizeParam != null) {
                try {
                    pageSize = Integer.parseInt(pageSizeParam);  // Lấy giá trị pageSize từ request
                    if (pageSize <= 0) {
                        pageSize = 6;  // Nếu người dùng nhập 0 hoặc giá trị không hợp lệ, sử dụng giá trị mặc định là 6
                    }
                } catch (NumberFormatException e) {
                    pageSize = 6;  // Nếu không thể chuyển đổi sang số, sử dụng giá trị mặc định là 6
                }
            }

            // Lưu lại các giá trị vào request để hiển thị lại trong JSP
            request.setAttribute("thumbnail", thumbnail);
            request.setAttribute("title", title);
            request.setAttribute("price", price);
            request.setAttribute("image", image);

            // Tiếp tục xử lý các dữ liệu môn học và danh mục
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Categories> categories = categoryDAO.getAll();
            CourseDAO courseDAO = new CourseDAO();
            Subjects subject = courseDAO.getSubjectById(Integer.parseInt(id));
            request.setAttribute("subject", subject);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("detail.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
