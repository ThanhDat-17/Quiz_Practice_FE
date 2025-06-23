package Controller;

import Dal.DBContext;
import Dal.SubjectDimensionDAO;
import Dal.CategoryDAO;
import entity.Categories;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/addDimension")
public class AddSubjectDimensionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = DBContext.getInstance().getConnection();
            CategoryDAO catDao = new CategoryDAO(conn);

            List<Categories> categoryList = catDao.getAllCategories();
            request.setAttribute("categoryList", categoryList);

            request.getRequestDispatcher("add_dimension.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi hiển thị form thêm mới", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("category"));

            Connection conn = DBContext.getInstance().getConnection();
            SubjectDimensionDAO dao = new SubjectDimensionDAO(conn);

            dao.insertDimension(name, description, categoryId);
            response.sendRedirect("subjectDimension");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi thêm mới dimension", e);
        }
    }
}
