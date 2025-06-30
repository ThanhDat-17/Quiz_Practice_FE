package Controller; 

import Dal.DBContext;
import Dal.SubjectDimensionDAO;
import entity.SubjectDimension;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/subjectDimension")
public class SubjectDimensionServlet extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        Connection conn = DBContext.getInstance().getConnection();
        SubjectDimensionDAO dao = new SubjectDimensionDAO(conn);
        List<SubjectDimension> list = dao.getAllDimensions();

        request.setAttribute("subjectDimensionList", list);
        request.getRequestDispatcher("Sublect_demination.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();  // In ra lỗi để debug
        throw new ServletException("Lỗi trong SubjectDimensionServlet: " + e.getMessage(), e);
    }
}

}