package Controller;

import dao.QuizDAO;
import dao.SubjectDAO;
import model.Quiz;
import model.Subject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/quiz")
public class QuizController extends HttpServlet {
    private QuizDAO quizDAO;
    private SubjectDAO subjectDAO;

    @Override
    public void init() throws ServletException {
        quizDAO = new QuizDAO();
        subjectDAO = new SubjectDAO();
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
                listQuizzes(request, response);
                break;
            case "search":
                searchQuizzes(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "delete":
                deleteQuiz(request, response);
                break;
            default:
                listQuizzes(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("save".equals(action)) {
            saveQuiz(request, response);
        } else if ("update".equals(action)) {
            updateQuiz(request, response);
        } else if ("search".equals(action)) {
            searchQuizzes(request, response);
        }
    }

    private void listQuizzes(HttpServletRequest request, HttpServletResponse response)
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

        List<Quiz> quizzes = quizDAO.getAllQuizzes(page, pageSize);
        List<Subject> subjects = subjectDAO.getAllSubjects(1, 1000);
        int totalQuizzes = quizDAO.getTotalQuizzesCount();
        int totalPages = (int) Math.ceil((double) totalQuizzes / pageSize);

        request.setAttribute("quizzes", quizzes);
        request.setAttribute("subjects", subjects);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalQuizzes", totalQuizzes);
        request.setAttribute("pageSize", pageSize);

        request.getRequestDispatcher("/admin/quiz-list.jsp").forward(request, response);
    }

    private void searchQuizzes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String searchName = request.getParameter("searchName");
        String subjectIdParam = request.getParameter("subjectId");
        String quizType = request.getParameter("quizType");
        
        Integer subjectId = null;
        if (subjectIdParam != null && !subjectIdParam.isEmpty() && !"0".equals(subjectIdParam)) {
            try {
                subjectId = Integer.parseInt(subjectIdParam);
            } catch (NumberFormatException e) {
                subjectId = null;
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

        List<Quiz> quizzes = quizDAO.searchQuizzes(searchName, subjectId, quizType, page, pageSize);
        List<Subject> subjects = subjectDAO.getAllSubjects(1, 1000);
        int totalQuizzes = quizDAO.getSearchQuizzesCount(searchName, subjectId, quizType);
        int totalPages = (int) Math.ceil((double) totalQuizzes / pageSize);

        request.setAttribute("quizzes", quizzes);
        request.setAttribute("subjects", subjects);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalQuizzes", totalQuizzes);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("searchName", searchName);
        request.setAttribute("selectedSubjectId", subjectId);
        request.setAttribute("selectedQuizType", quizType);

        request.getRequestDispatcher("/admin/quiz-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int quizId = Integer.parseInt(request.getParameter("id"));
        Quiz quiz = quizDAO.getQuizById(quizId);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"quizId\":").append(quiz.getQuizId()).append(",");
        json.append("\"quizName\":\"").append(escapeJson(quiz.getQuizName())).append("\",");
        json.append("\"description\":\"").append(escapeJson(quiz.getDescription() != null ? quiz.getDescription() : "")).append("\",");
        json.append("\"subjectId\":").append(quiz.getSubjectId()).append(",");
        json.append("\"level\":\"").append(quiz.getLevel()).append("\",");
        json.append("\"duration\":").append(quiz.getDuration()).append(",");
        json.append("\"passRate\":").append(quiz.getPassRate()).append(",");
        json.append("\"quizType\":\"").append(quiz.getQuizType()).append("\",");
        json.append("\"numberOfQuestions\":").append(quiz.getNumberOfQuestions()).append(",");
        json.append("\"isActive\":").append(quiz.isActive());
        json.append("}");
        
        response.getWriter().write(json.toString());
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Subject> subjects = subjectDAO.getAllSubjects(1, 1000);
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/admin/quiz-form.jsp").forward(request, response);
    }

    private void saveQuiz(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            String quizName = request.getParameter("quizName");
            String description = request.getParameter("description");
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            String level = request.getParameter("level");
            int duration = Integer.parseInt(request.getParameter("duration"));
            double passRate = Double.parseDouble(request.getParameter("passRate"));
            String quizType = request.getParameter("quizType");
            int numberOfQuestions = Integer.parseInt(request.getParameter("numberOfQuestions"));
            boolean isActive = "true".equals(request.getParameter("isActive"));
            
            Integer currentUserId = (Integer) session.getAttribute("userId");
            if (currentUserId == null) {
                currentUserId = 1;
            }

            Quiz quiz = new Quiz(quizName, description, subjectId, level, duration, 
                               passRate, quizType, numberOfQuestions, isActive, currentUserId);

            boolean success = quizDAO.insertQuiz(quiz);
            
            if (success) {
                session.setAttribute("message", "Quiz created successfully!");
                session.setAttribute("messageType", "success");
            } else {
                session.setAttribute("message", "Failed to create quiz. Please try again.");
                session.setAttribute("messageType", "danger");
            }
            
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Invalid input format. Please check your data.");
            session.setAttribute("messageType", "danger");
        } catch (Exception e) {
            session.setAttribute("message", "An error occurred: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/quiz");
    }

    private void updateQuiz(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            int quizId = Integer.parseInt(request.getParameter("quizId"));
            String quizName = request.getParameter("quizName");
            String description = request.getParameter("description");
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            String level = request.getParameter("level");
            int duration = Integer.parseInt(request.getParameter("duration"));
            double passRate = Double.parseDouble(request.getParameter("passRate"));
            String quizType = request.getParameter("quizType");
            int numberOfQuestions = Integer.parseInt(request.getParameter("numberOfQuestions"));
            boolean isActive = "true".equals(request.getParameter("isActive"));

            Quiz quiz = new Quiz();
            quiz.setQuizId(quizId);
            quiz.setQuizName(quizName);
            quiz.setDescription(description);
            quiz.setSubjectId(subjectId);
            quiz.setLevel(level);
            quiz.setDuration(duration);
            quiz.setPassRate(passRate);
            quiz.setQuizType(quizType);
            quiz.setNumberOfQuestions(numberOfQuestions);
            quiz.setActive(isActive);

            boolean success = quizDAO.updateQuiz(quiz);
            
            if (success) {
                session.setAttribute("message", "Quiz updated successfully!");
                session.setAttribute("messageType", "success");
            } else {
                session.setAttribute("message", "Failed to update quiz. Please try again.");
                session.setAttribute("messageType", "danger");
            }
            
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Invalid input format. Please check your data.");
            session.setAttribute("messageType", "danger");
        } catch (Exception e) {
            session.setAttribute("message", "An error occurred: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/quiz");
    }

    private void deleteQuiz(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            int quizId = Integer.parseInt(request.getParameter("id"));
            boolean success = quizDAO.deleteQuiz(quizId);
            
            if (success) {
                session.setAttribute("message", "Quiz deleted successfully!");
                session.setAttribute("messageType", "success");
            } else {
                session.setAttribute("message", "Failed to delete quiz. Please try again.");
                session.setAttribute("messageType", "danger");
            }
            
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Invalid quiz ID.");
            session.setAttribute("messageType", "danger");
        } catch (Exception e) {
            session.setAttribute("message", "An error occurred: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/quiz");
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "\\\"")
                   .replace("\\", "\\\\")
                   .replace("\r", "\\r")
                   .replace("\n", "\\n")
                   .replace("\t", "\\t");
    }

    @Override
    public void destroy() {
        if (quizDAO != null) {
            quizDAO.close();
        }
        if (subjectDAO != null) {
            subjectDAO.close();
        }
    }
} 