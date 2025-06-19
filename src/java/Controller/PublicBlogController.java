package Controller;

import dao.BlogDAO;
import model.Blog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet({"/blog-list", "/blog-detail"})
public class PublicBlogController extends HttpServlet {
    private BlogDAO blogDAO;

    @Override
    public void init() throws ServletException {
        blogDAO = new BlogDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        if ("/blog-list".equals(path)) {
            handleBlogList(request, response);
        } else if ("/blog-detail".equals(path)) {
            handleBlogDetail(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        if ("/blog-list".equals(path)) {
            handleBlogSearch(request, response);
        }
    }

    private void handleBlogList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int page = 1;
        int pageSize = 12; // 12 blogs per page cho grid layout
        
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<Blog> blogs = blogDAO.getAllBlogs(page, pageSize);
        int totalBlogs = blogDAO.getTotalBlogsCount();
        int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

        // Get recent blogs for sidebar
        List<Blog> recentBlogs = blogDAO.getRecentBlogs(3);

        request.setAttribute("blogs", blogs);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalBlogs", totalBlogs);
        request.setAttribute("recentBlogs", recentBlogs);

        request.getRequestDispatcher("/blog-list.jsp").forward(request, response);
    }

    private void handleBlogDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String blogIdParam = request.getParameter("id");
        if (blogIdParam == null || blogIdParam.isEmpty()) {
            response.sendRedirect("blog-list");
            return;
        }

        try {
            int blogId = Integer.parseInt(blogIdParam);
            Blog blog = blogDAO.getBlogById(blogId);
            
            if (blog == null) {
                response.sendRedirect("blog-list");
                return;
            }

            // Get recent blogs for sidebar
            List<Blog> recentBlogs = blogDAO.getRecentBlogs(3);

            request.setAttribute("blog", blog);
            request.setAttribute("recentBlogs", recentBlogs);

            request.getRequestDispatcher("/blog-detail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("blog-list");
        }
    }

    private void handleBlogSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String searchKeyword = request.getParameter("search");
        if (searchKeyword == null) searchKeyword = "";
        
        int page = 1;
        int pageSize = 12;
        
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<Blog> blogs;
        int totalBlogs;
        
        if (searchKeyword.trim().isEmpty()) {
            blogs = blogDAO.getAllBlogs(page, pageSize);
            totalBlogs = blogDAO.getTotalBlogsCount();
        } else {
            blogs = blogDAO.searchBlogs(searchKeyword, page, pageSize);
            totalBlogs = blogDAO.getSearchBlogsCount(searchKeyword);
        }
        
        int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

        // Get recent blogs for sidebar
        List<Blog> recentBlogs = blogDAO.getRecentBlogs(3);

        request.setAttribute("blogs", blogs);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalBlogs", totalBlogs);
        request.setAttribute("recentBlogs", recentBlogs);
        request.setAttribute("searchKeyword", searchKeyword);

        request.getRequestDispatcher("/blog-list.jsp").forward(request, response);
    }
} 