package Controller;

import Dal.DBContext;
import Dal.SubjectDimensionDAO;
import Dal.CategoryDAO;
import entity.Categories;
import entity.SubjectDimension;
import entity.Categories;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/editDimension")
public class EditSubjectDimensionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int dimensionId = Integer.parseInt(request.getParameter("id"));
            Connection conn = DBContext.getInstance().getConnection();

            SubjectDimensionDAO dao = new SubjectDimensionDAO(conn);
            CategoryDAO catDao = new CategoryDAO(conn);

            SubjectDimension dimension = dao.getDimensionById(dimensionId);
            List<Categories> categoryList = catDao.getAllCategories();

            request.setAttribute("dimension", dimension);
            request.setAttribute("categoryList", categoryList);
            request.getRequestDispatcher("edit_dimension.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi hiển thị form edit dimension", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int categoryId = Integer.parseInt(request.getParameter("category"));

            Connection conn = DBContext.getInstance().getConnection();
            SubjectDimensionDAO dao = new SubjectDimensionDAO(conn);

            dao.updateDimension(id, name, description, categoryId);
            response.sendRedirect("subjectDimension");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi cập nhật dimension", e);
        }
    }
}
