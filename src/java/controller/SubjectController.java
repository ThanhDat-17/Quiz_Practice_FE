package controller;

import dao.SubjectDAO;
import dao.CategoryDAO;
import model.Subject;
import model.Category;
import utils.FileUploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/subject")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class SubjectController extends HttpServlet {
    private SubjectDAO subjectDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        subjectDAO = new SubjectDAO();
        categoryDAO = new CategoryDAO();
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
                listSubjects(request, response);
                break;
            case "search":
                searchSubjects(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "delete":
                deleteSubject(request, response);
                break;
            default:
                listSubjects(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("save".equals(action)) {
            saveSubject(request, response);
        } else if ("update".equals(action)) {
            updateSubject(request, response);
        } else if ("search".equals(action)) {
            searchSubjects(request, response);
        }
    }

    private void listSubjects(HttpServletRequest request, HttpServletResponse response)
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

        String pageSizeParam = request.getParameter("pageSize");
        if (pageSizeParam != null && !pageSizeParam.isEmpty()) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
                if (pageSize < 1) pageSize = 10;
            } catch (NumberFormatException e) {
                pageSize = 10;
            }
        }

        List<Subject> subjects = subjectDAO.getAllSubjects(page, pageSize);
        List<Category> categories = categoryDAO.getAllCategories();
        int totalSubjects = subjectDAO.getTotalSubjectsCount();
        int totalPages = (int) Math.ceil((double) totalSubjects / pageSize);

        request.setAttribute("subjects", subjects);
        request.setAttribute("categories", categories);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalSubjects", totalSubjects);
        request.setAttribute("pageSize", pageSize);

        request.getRequestDispatcher("/admin/subject-list.jsp").forward(request, response);
    }

    private void searchSubjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String searchName = request.getParameter("searchName");
        String categoryIdParam = request.getParameter("categoryId");
        
        Integer categoryId = null;
        if (categoryIdParam != null && !categoryIdParam.isEmpty() && !"0".equals(categoryIdParam)) {
            try {
                categoryId = Integer.parseInt(categoryIdParam);
            } catch (NumberFormatException e) {
                categoryId = null;
            }
        }
        
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

        String pageSizeParam = request.getParameter("pageSize");
        if (pageSizeParam != null && !pageSizeParam.isEmpty()) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
                if (pageSize < 1) pageSize = 10;
            } catch (NumberFormatException e) {
                pageSize = 10;
            }
        }

        List<Subject> subjects = subjectDAO.searchSubjects(searchName, categoryId, page, pageSize);
        List<Category> categories = categoryDAO.getAllCategories();
        int totalSubjects = subjectDAO.getSearchSubjectsCount(searchName, categoryId);
        int totalPages = (int) Math.ceil((double) totalSubjects / pageSize);

        request.setAttribute("subjects", subjects);
        request.setAttribute("categories", categories);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalSubjects", totalSubjects);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("searchName", searchName);
        request.setAttribute("selectedCategoryId", categoryId);

        request.getRequestDispatcher("/admin/subject-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int subjectId = Integer.parseInt(request.getParameter("id"));
        Subject subject = subjectDAO.getSubjectById(subjectId);
        
        // Return JSON for AJAX request
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"subjectId\":").append(subject.getSubjectId()).append(",");
        json.append("\"subjectName\":\"").append(subject.getSubjectName()).append("\",");
        json.append("\"subjectImage\":\"").append(subject.getSubjectImage() != null ? subject.getSubjectImage() : "").append("\",");
        json.append("\"description\":\"").append(subject.getDescription() != null ? subject.getDescription() : "").append("\",");
        json.append("\"isActive\":").append(subject.isActive()).append(",");
        json.append("\"categoryId\":").append(subject.getCategoryId());
        json.append("}");
        
        response.getWriter().write(json.toString());
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("categories", categories);
        request.setAttribute("action", "add");
        request.getRequestDispatcher("/admin/subject-list.jsp").forward(request, response);
    }

    private void saveSubject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String subjectName = request.getParameter("subjectName");
            String subjectImage = request.getParameter("subjectImage");
            String description = request.getParameter("description");
            boolean isActive = request.getParameter("isActive") != null;
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            
            // Handle file upload if present
            Part filePart = request.getPart("imageFile");
            if (filePart != null && filePart.getSize() > 0) {
                try {
                    String uploadedFileName = FileUploadUtil.uploadBlogImage(filePart, getServletContext());
                    if (uploadedFileName != null && !uploadedFileName.isEmpty()) {
                        subjectImage = uploadedFileName; // Just store filename, not full path
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("message", "Error uploading image: " + e.getMessage());
                    request.getSession().setAttribute("messageType", "error");
                    response.sendRedirect(request.getContextPath() + "/admin/subject");
                    return;
                }
            }
            
            Subject subject = new Subject();
            subject.setSubjectName(subjectName);
            subject.setSubjectImage(subjectImage);
            subject.setDescription(description);
            subject.setActive(isActive);
            subject.setCategoryId(categoryId);

            if (subjectDAO.insertSubject(subject)) {
                request.getSession().setAttribute("message", "Subject has been added successfully!");
                request.getSession().setAttribute("messageType", "success");
            } else {
                request.getSession().setAttribute("message", "Error occurred while adding subject!");
                request.getSession().setAttribute("messageType", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Error processing request: " + e.getMessage());
            request.getSession().setAttribute("messageType", "error");
        }

        response.sendRedirect(request.getContextPath() + "/admin/subject");
    }

    private void updateSubject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            String subjectName = request.getParameter("subjectName");
            String subjectImage = request.getParameter("subjectImage");
            String description = request.getParameter("description");
            boolean isActive = request.getParameter("isActive") != null;
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            // Handle file upload if present
            Part filePart = request.getPart("imageFile");
            if (filePart != null && filePart.getSize() > 0) {
                try {
                    String uploadedFileName = FileUploadUtil.uploadBlogImage(filePart, getServletContext());
                    if (uploadedFileName != null && !uploadedFileName.isEmpty()) {
                        subjectImage = uploadedFileName; // Just store filename, not full path
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    request.getSession().setAttribute("message", "Error uploading image: " + e.getMessage());
                    request.getSession().setAttribute("messageType", "error");
                    response.sendRedirect(request.getContextPath() + "/admin/subject");
                    return;
                }
            }
            
            Subject subject = new Subject();
            subject.setSubjectId(subjectId);
            subject.setSubjectName(subjectName);
            subject.setSubjectImage(subjectImage);
            subject.setDescription(description);
            subject.setActive(isActive);
            subject.setCategoryId(categoryId);

            if (subjectDAO.updateSubject(subject)) {
                request.getSession().setAttribute("message", "Subject has been updated successfully!");
                request.getSession().setAttribute("messageType", "success");
            } else {
                request.getSession().setAttribute("message", "Error occurred while updating subject!");
                request.getSession().setAttribute("messageType", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Error processing request: " + e.getMessage());
            request.getSession().setAttribute("messageType", "error");
        }

        response.sendRedirect(request.getContextPath() + "/admin/subject");
    }

    private void deleteSubject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int subjectId = Integer.parseInt(request.getParameter("id"));

        if (subjectDAO.deleteSubject(subjectId)) {
            request.getSession().setAttribute("message", "Subject has been deleted successfully!");
            request.getSession().setAttribute("messageType", "success");
        } else {
            request.getSession().setAttribute("message", "Error occurred while deleting subject!");
            request.getSession().setAttribute("messageType", "error");
        }

        response.sendRedirect(request.getContextPath() + "/admin/subject");
    }
} 