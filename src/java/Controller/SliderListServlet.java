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
@WebServlet("/slider-list")
public class SliderListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
      
            Connection conn = DBContext.getInstance().getConnection();
            SliderDAO dao = new SliderDAO(conn);
            List<Slider> sliders = dao.getAllSliders();
            request.setAttribute("sliders", sliders);
            request.getRequestDispatcher("sliderList.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();  // In ra lỗi để debug
        throw new ServletException("Lỗi trong SliderListServlet: " + e.getMessage(), e);
        }
        


//SliderDAO dao = new SliderDAO();
      //  List<Slider> sliders = dao.getAllSliders();

     //   request.setAttribute("sliders", sliders);
   //     request.getRequestDispatcher("sliderList.jsp").forward(request, response);
    }
}