package Controller;

import dao.QuestionDAO;
import dao.QuizDAO;
import model.Question;
import model.QuestionOption;
import model.Quiz;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/questions")
public class QuestionController extends HttpServlet {
    private QuestionDAO questionDAO;
    private QuizDAO quizDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        questionDAO = new QuestionDAO();
        quizDAO = new QuizDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                listQuestions(request, response);
                break;
            case "edit":
                editQuestion(request, response);
                break;
            case "delete":
                deleteQuestion(request, response);
                break;
            default:
                listQuestions(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        switch (action) {
            case "add":
                addQuestion(request, response);
                break;
            case "update":
                updateQuestion(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

    private void listQuestions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String quizIdParam = request.getParameter("quizId");
        if (quizIdParam == null || quizIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quiz ID is required");
            return;
        }

        try {
            int quizId = Integer.parseInt(quizIdParam);
            
            // Get quiz details
            Quiz quiz = quizDAO.getQuizById(quizId);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found");
                return;
            }

            // Get questions for this quiz
            List<Question> questions = questionDAO.getQuestionsByQuizId(quizId);

            request.setAttribute("quiz", quiz);
            request.setAttribute("questions", questions);
            request.setAttribute("questionCount", questions.size());

            request.getRequestDispatcher("/admin/question-management.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Quiz ID format");
        }
    }

    private void editQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String questionIdParam = request.getParameter("questionId");
        if (questionIdParam == null || questionIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Question ID is required");
            return;
        }

        try {
            int questionId = Integer.parseInt(questionIdParam);
            Question question = questionDAO.getQuestionById(questionId);
            
            if (question == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Question not found");
                return;
            }

            // Return question data as JSON for AJAX
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(question));
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Question ID format");
        }
    }

    private void addQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            int quizId = Integer.parseInt(request.getParameter("quizId"));
            String questionText = request.getParameter("questionText");
            String questionType = request.getParameter("questionType");
            double points = Double.parseDouble(request.getParameter("points"));
            
            // Create question
            Question question = new Question();
            question.setQuizId(quizId);
            question.setQuestionText(questionText);
            question.setQuestionType(questionType);
            question.setQuestionOrder(questionDAO.getNextQuestionOrder(quizId));
            question.setPoints(points);
            question.setActive(true);

            // Parse options from JSON
            String optionsJson = request.getParameter("options");
            if (optionsJson != null && !optionsJson.isEmpty()) {
                Type listType = new TypeToken<List<QuestionOption>>(){}.getType();
                List<QuestionOption> options = gson.fromJson(optionsJson, listType);
                question.setOptions(options);
            }

            boolean success = questionDAO.insertQuestion(question);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"success\": true, \"message\": \"Question added successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"success\": false, \"message\": \"Failed to add question\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }

    private void updateQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            int questionId = Integer.parseInt(request.getParameter("questionId"));
            int quizId = Integer.parseInt(request.getParameter("quizId"));
            String questionText = request.getParameter("questionText");
            String questionType = request.getParameter("questionType");
            double points = Double.parseDouble(request.getParameter("points"));
            int questionOrder = Integer.parseInt(request.getParameter("questionOrder"));

            // Create question object
            Question question = new Question();
            question.setQuestionId(questionId);
            question.setQuizId(quizId);
            question.setQuestionText(questionText);
            question.setQuestionType(questionType);
            question.setQuestionOrder(questionOrder);
            question.setPoints(points);
            question.setActive(true);

            // Parse options from JSON
            String optionsJson = request.getParameter("options");
            if (optionsJson != null && !optionsJson.isEmpty()) {
                Type listType = new TypeToken<List<QuestionOption>>(){}.getType();
                List<QuestionOption> options = gson.fromJson(optionsJson, listType);
                question.setOptions(options);
            }

            boolean success = questionDAO.updateQuestion(question);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"success\": true, \"message\": \"Question updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"success\": false, \"message\": \"Failed to update question\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }

    private void deleteQuestion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String questionIdParam = request.getParameter("questionId");
        if (questionIdParam == null || questionIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Question ID is required\"}");
            return;
        }

        try {
            int questionId = Integer.parseInt(questionIdParam);
            boolean success = questionDAO.deleteQuestion(questionId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"success\": true, \"message\": \"Question deleted successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"success\": false, \"message\": \"Failed to delete question\"}");
            }

        } catch (NumberFormatException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid Question ID format\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Error: " + e.getMessage() + "\"}");
        }
    }

    @Override
    public void destroy() {
        if (questionDAO != null) {
            questionDAO.close();
        }
        if (quizDAO != null) {
            quizDAO.close();
        }
    }
} 