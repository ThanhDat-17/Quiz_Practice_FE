package Controller;

import dao.QuizDAO;
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

    @Override
    public void init() throws ServletException {
        quizDAO = new QuizDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            showAddForm(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else if ("delete".equals(action)) {
            deleteQuiz(request, response);
        } else {
            listQuizzes(request, response);
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
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<Quiz> quizzes = quizDAO.getAllQuizzes(page, pageSize);
        int totalQuizzes = quizDAO.getTotalQuizzesCount();
        int totalPages = (int) Math.ceil((double) totalQuizzes / pageSize);

        // Get subjects for filter dropdown
        List<Subject> subjects = quizDAO.getAllSubjects();

        request.setAttribute("quizzes", quizzes);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalQuizzes", totalQuizzes);
        request.setAttribute("subjects", subjects);

        request.getRequestDispatcher("/admin/quiz-list.jsp").forward(request, response);
    }

    private void searchQuizzes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String searchKeyword = request.getParameter("search");
        String subjectFilter = request.getParameter("subjectFilter");
        String typeFilter = request.getParameter("typeFilter");
        
        if (searchKeyword == null) searchKeyword = "";
        
        int page = 1;
        int pageSize = 10;
        
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<Quiz> quizzes;
        int totalQuizzes;
        
        if (searchKeyword.trim().isEmpty() && 
            (subjectFilter == null || "all".equals(subjectFilter)) && 
            (typeFilter == null || "all".equals(typeFilter))) {
            quizzes = quizDAO.getAllQuizzes(page, pageSize);
            totalQuizzes = quizDAO.getTotalQuizzesCount();
        } else {
            quizzes = quizDAO.searchQuizzes(searchKeyword, subjectFilter, typeFilter, page, pageSize);
            totalQuizzes = quizDAO.getSearchQuizzesCount(searchKeyword, subjectFilter, typeFilter);
        }
        
        int totalPages = (int) Math.ceil((double) totalQuizzes / pageSize);

        // Get subjects for filter dropdown
        List<Subject> subjects = quizDAO.getAllSubjects();

        request.setAttribute("quizzes", quizzes);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalQuizzes", totalQuizzes);
        request.setAttribute("subjects", subjects);
        request.setAttribute("searchKeyword", searchKeyword);
        request.setAttribute("subjectFilter", subjectFilter);
        request.setAttribute("typeFilter", typeFilter);

        request.getRequestDispatcher("/admin/quiz-list.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get subjects for dropdown
        List<Subject> subjects = quizDAO.getAllSubjects();
        request.setAttribute("subjects", subjects);
        
        request.getRequestDispatcher("/admin/quiz-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int quizId = Integer.parseInt(request.getParameter("id"));
        Quiz quiz = quizDAO.getQuizById(quizId);
        
        // Get subjects for dropdown
        List<Subject> subjects = quizDAO.getAllSubjects();
        
        request.setAttribute("quiz", quiz);
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/admin/quiz-form.jsp").forward(request, response);
    }

    private void saveQuiz(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Quiz quiz = new Quiz();
            quiz.setQuizName(request.getParameter("quizName"));
            quiz.setDescription(request.getParameter("description"));
            quiz.setSubjectId(Integer.parseInt(request.getParameter("subjectId")));
            quiz.setLevel(request.getParameter("level"));
            quiz.setDuration(Integer.parseInt(request.getParameter("duration")));
            quiz.setPassRate(Double.parseDouble(request.getParameter("passRate")));
            quiz.setType(request.getParameter("type"));
            quiz.setTotalQuestions(0); // Will be auto-calculated when questions are added
            quiz.setActive("on".equals(request.getParameter("isActive")));
            quiz.setCreatedBy(1); // TODO: Get from session
            
            HttpSession session = request.getSession();
            
            if (quizDAO.insertQuiz(quiz)) {
                session.setAttribute("message", "Quiz created successfully!");
                session.setAttribute("messageType", "success");
            } else {
                session.setAttribute("message", "Failed to create quiz. Please try again.");
                session.setAttribute("messageType", "danger");
            }
            
        } catch (Exception e) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Error: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/quiz");
    }

    private void updateQuiz(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Quiz quiz = new Quiz();
            quiz.setQuizId(Integer.parseInt(request.getParameter("quizId")));
            quiz.setQuizName(request.getParameter("quizName"));
            quiz.setDescription(request.getParameter("description"));
            quiz.setSubjectId(Integer.parseInt(request.getParameter("subjectId")));
            quiz.setLevel(request.getParameter("level"));
            quiz.setDuration(Integer.parseInt(request.getParameter("duration")));
            quiz.setPassRate(Double.parseDouble(request.getParameter("passRate")));
            quiz.setType(request.getParameter("type"));
            quiz.setTotalQuestions(0); // Questions count will be auto-calculated, this field will be ignored in update
            quiz.setActive("on".equals(request.getParameter("isActive")));
            
            HttpSession session = request.getSession();
            
            if (quizDAO.updateQuiz(quiz)) {
                session.setAttribute("message", "Quiz updated successfully!");
                session.setAttribute("messageType", "success");
            } else {
                session.setAttribute("message", "Failed to update quiz. Please try again.");
                session.setAttribute("messageType", "danger");
            }
            
        } catch (Exception e) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Error: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/quiz");
    }

    private void deleteQuiz(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            int quizId = Integer.parseInt(request.getParameter("id"));
            
            HttpSession session = request.getSession();
            
            if (quizDAO.deleteQuiz(quizId)) {
                session.setAttribute("message", "Quiz deleted successfully!");
                session.setAttribute("messageType", "success");
            } else {
                session.setAttribute("message", "Failed to delete quiz. Please try again.");
                session.setAttribute("messageType", "danger");
            }
            
        } catch (Exception e) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Error: " + e.getMessage());
            session.setAttribute("messageType", "danger");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/quiz");
    }

    @Override
    public void destroy() {
        if (quizDAO != null) {
            quizDAO.close();
        }
    }
} 