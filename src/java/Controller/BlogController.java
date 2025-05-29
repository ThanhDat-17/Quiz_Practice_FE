package controller;

import dao.BlogDAO;
import model.Blog;
import utils.FileUploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/admin/blog")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class BlogController extends HttpServlet {
    private BlogDAO blogDAO;

    @Override
    public void init() throws ServletException {
        blogDAO = new BlogDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listBlogs(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "delete":
                deleteBlog(request, response);
                break;
            default:
                listBlogs(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("save".equals(action)) {
            saveBlog(request, response);
        } else if ("update".equals(action)) {
            updateBlog(request, response);
        }
    }

    private void listBlogs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int page = 1;
        int pageSize = 10;
        
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<Blog> blogs = blogDAO.getAllBlogs(page, pageSize);
        int totalBlogs = blogDAO.getTotalBlogsCount();
        int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);

        request.setAttribute("blogs", blogs);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalBlogs", totalBlogs);

        request.getRequestDispatcher("/admin/blog-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int blogId = Integer.parseInt(request.getParameter("id"));
        Blog blog = blogDAO.getBlogById(blogId);
        
        request.setAttribute("blog", blog);
        request.getRequestDispatcher("/admin/blog-form.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/admin/blog-form.jsp").forward(request, response);
    }

    private void saveBlog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String imageUrl = request.getParameter("image");
        boolean isActive = request.getParameter("isActive") != null;
        
        // Handle file upload if present
        Part filePart = request.getPart("imageFile");
        if (filePart != null && filePart.getSize() > 0) {
            imageUrl = FileUploadUtil.uploadBlogImage(filePart, getServletContext());
        }
        
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setImage(imageUrl);
        blog.setActive(isActive);
        blog.setCreatedDate(LocalDate.now());
        blog.setCreatedBy(1); // Default user ID - có thể thay đổi khi có authentication

        if (blogDAO.insertBlog(blog)) {
            request.getSession().setAttribute("message", "Blog đã được thêm thành công!");
            request.getSession().setAttribute("messageType", "success");
        } else {
            request.getSession().setAttribute("message", "Có lỗi xảy ra khi thêm blog!");
            request.getSession().setAttribute("messageType", "error");
        }

        response.sendRedirect(request.getContextPath() + "/admin/blog");
    }

    private void updateBlog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String imageUrl = request.getParameter("image");
        boolean isActive = request.getParameter("isActive") != null;

        // Handle file upload if present
        Part filePart = request.getPart("imageFile");
        if (filePart != null && filePart.getSize() > 0) {
            imageUrl = FileUploadUtil.uploadBlogImage(filePart, getServletContext());
        }
        
        Blog blog = new Blog();
        blog.setBlogId(blogId);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setImage(imageUrl);
        blog.setActive(isActive);

        if (blogDAO.updateBlog(blog)) {
            request.getSession().setAttribute("message", "Blog đã được cập nhật thành công!");
            request.getSession().setAttribute("messageType", "success");
        } else {
            request.getSession().setAttribute("message", "Có lỗi xảy ra khi cập nhật blog!");
            request.getSession().setAttribute("messageType", "error");
        }

        response.sendRedirect(request.getContextPath() + "/admin/blog");
    }

    private void deleteBlog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int blogId = Integer.parseInt(request.getParameter("id"));

        if (blogDAO.deleteBlog(blogId)) {
            request.getSession().setAttribute("message", "Blog đã được xóa thành công!");
            request.getSession().setAttribute("messageType", "success");
        } else {
            request.getSession().setAttribute("message", "Có lỗi xảy ra khi xóa blog!");
            request.getSession().setAttribute("messageType", "error");
        }

        response.sendRedirect(request.getContextPath() + "/admin/blog");
    }
} 