/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;


import Dal.SliderDAO;
import entity.Slider;
import Dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;


/**
 *
 * @author Asus
 */
@WebServlet("/admin/slider-list")
public class SliderListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = DBContext.getInstance().getConnection();
            SliderDAO dao = new SliderDAO(conn);

            String action = request.getParameter("action");
            String idParam = request.getParameter("id");
            String status = request.getParameter("status");
            if (status == null) status = "all";
            String search = request.getParameter("search");
            if (search == null) search = "";
            String pageParam = request.getParameter("page");
            int page = 1;
            int pageSize = 10;
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (Exception ignored) {}
            int offset = (page - 1) * pageSize;

            // Handle show/hide actions
            if (("show".equals(action) || "hide".equals(action)) && idParam != null) {
                int sliderId = Integer.parseInt(idParam);
                String newStatus = "show".equals(action) ? "show" : "hide";
                dao.updateSliderStatus(sliderId, newStatus);
                // Redirect to preserve filters and pagination
                String redirectUrl = String.format("%s/admin/slider-list?page=%d&status=%s&search=%s", request.getContextPath(), page, status, search);
                response.sendRedirect(redirectUrl);
                return;
            }

            // Get filtered, searched, paginated sliders
            int totalSliders = dao.countSliders(status, search);
            int totalPages = (int) Math.ceil((double) totalSliders / pageSize);
            if (page > totalPages && totalPages > 0) page = totalPages;
            List<Slider> sliders = dao.getSliders(status, search, offset, pageSize);

            // Set attributes for JSP
            request.setAttribute("sliders", sliders);
            request.setAttribute("status", status);
            request.setAttribute("search", search);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalSliders", totalSliders);

            request.getRequestDispatcher("/admin/sliderList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error in SliderListServlet: " + e.getMessage(), e);
        }
    }
}