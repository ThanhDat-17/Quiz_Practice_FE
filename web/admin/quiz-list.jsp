<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quiz Management - Admin</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    
    <style>
        .sidebar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
        }
        .sidebar .nav-link {
            color: white;
            padding: 15px 20px;
            border-radius: 8px;
            margin: 5px 10px;
            transition: all 0.3s ease;
        }
        .sidebar .nav-link:hover, .sidebar .nav-link.active {
            background: rgba(255,255,255,0.2);
            color: white;
            transform: translateX(5px);
        }
        .content-wrapper {
            background-color: #f8f9fa;
            min-height: 100vh;
        }
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            border-radius: 25px;
            padding: 10px 25px;
            transition: all 0.3s ease;
        }
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
        }
        .btn-success {
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            border: none;
            border-radius: 20px;
            padding: 5px 15px;
        }
        .btn-warning {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            border: none;
            border-radius: 20px;
            padding: 5px 15px;
        }
        .btn-danger {
            background: linear-gradient(135deg, #ff6b6b 0%, #ee5a52 100%);
            border: none;
            border-radius: 20px;
            padding: 5px 15px;
        }
        .table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            font-weight: 600;
            text-align: center;
        }
        .table td {
            vertical-align: middle;
            text-align: center;
        }
        .badge {
            border-radius: 15px;
            padding: 8px 15px;
            font-size: 0.75rem;
        }
        .alert {
            border-radius: 15px;
            border: none;
        }
        .page-title {
            color: #333;
            margin-bottom: 30px;
            font-weight: 700;
        }
        .search-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .form-control, .form-select {
            border-radius: 10px;
            border: 1px solid #ddd;
        }
        .stats-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px;
        }
        .level-badge {
            font-size: 0.7rem;
            padding: 4px 8px;
        }
        .quiz-actions {
            white-space: nowrap;
        }
        .quiz-actions .btn {
            margin: 0 2px;
            padding: 4px 8px;
            font-size: 0.8rem;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-2 sidebar">
                <div class="p-3">
                    <h4 class="text-white text-center mb-4">
                        <i class="fas fa-cogs"></i> Admin Panel
                    </h4>
                    <nav class="nav flex-column">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/blog">
                            <i class="fas fa-blog"></i> Blog Management
                        </a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/subject">
                            <i class="fas fa-book"></i> Subject Management
                        </a>
                        <a class="nav-link" href="#">
                            <i class="fas fa-users"></i> User Management
                        </a>
                        <a class="nav-link active" href="${pageContext.request.contextPath}/admin/quiz">
                            <i class="fas fa-question-circle"></i> Quiz Management
                        </a>
                        <a class="nav-link" href="#">
                            <i class="fas fa-chart-bar"></i> Reports
                        </a>
                    </nav>
                </div>
            </div>

            <!-- Main Content -->
            <div class="col-md-10 content-wrapper">
                <div class="p-4">
                    <!-- Header -->
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2 class="page-title">
                            <i class="fas fa-question-circle text-primary"></i> Quiz Management
                        </h2>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addQuizModal">
                            <i class="fas fa-plus"></i> Create New Quiz
                        </button>
                    </div>

                    <!-- Alert Messages -->
                    <c:if test="${not empty sessionScope.message}">
                        <div class="alert alert-${sessionScope.messageType} alert-dismissible fade show" role="alert">
                            <i class="fas fa-${sessionScope.messageType eq 'success' ? 'check-circle' : 'exclamation-triangle'}"></i>
                            ${sessionScope.message}
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                        <c:remove var="message" scope="session"/>
                        <c:remove var="messageType" scope="session"/>
                    </c:if>

                    <!-- Statistics -->
                    <div class="row mb-4">
                        <div class="col-md-3">
                            <div class="card stats-card text-center">
                                <div class="card-body">
                                    <h3>${totalQuizzes}</h3>
                                    <p class="mb-0">Total Quizzes</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card text-center">
                                <div class="card-body">
                                    <h3 class="text-success">
                                        <c:set var="activeCount" value="0"/>
                                        <c:forEach var="quiz" items="${quizzes}">
                                            <c:if test="${quiz.active}">
                                                <c:set var="activeCount" value="${activeCount + 1}"/>
                                            </c:if>
                                        </c:forEach>
                                        ${activeCount}
                                    </h3>
                                    <p class="text-muted mb-0">Active Quizzes</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card text-center">
                                <div class="card-body">
                                    <h3 class="text-warning">${totalQuizzes - activeCount}</h3>
                                    <p class="text-muted mb-0">Inactive Quizzes</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="card text-center">
                                <div class="card-body">
                                    <h3 class="text-info">${totalPages}</h3>
                                    <p class="text-muted mb-0">Total Pages</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Search and Filter -->
                    <div class="card search-card mb-4">
                        <div class="card-body">
                            <form method="post" action="${pageContext.request.contextPath}/admin/quiz">
                                <input type="hidden" name="action" value="search">
                                <div class="row g-3">
                                    <div class="col-md-4">
                                        <label class="form-label">Search Quiz Name</label>
                                        <input type="text" class="form-control" name="search" 
                                               value="${searchKeyword}" placeholder="Enter quiz name...">
                                    </div>
                                    <div class="col-md-3">
                                        <label class="form-label">Subject</label>
                                        <select class="form-select" name="subjectFilter">
                                            <option value="all">All Subjects</option>
                                            <c:forEach var="subject" items="${subjects}">
                                                <option value="${subject.subjectId}" 
                                                    ${subjectFilter eq subject.subjectId ? 'selected' : ''}>
                                                    ${subject.subjectName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <label class="form-label">Quiz Type</label>
                                        <select class="form-select" name="typeFilter">
                                            <option value="all">All Types</option>
                                            <option value="Practice" ${typeFilter eq 'Practice' ? 'selected' : ''}>Practice</option>
                                            <option value="Exam" ${typeFilter eq 'Exam' ? 'selected' : ''}>Exam</option>
                                            <option value="Assignment" ${typeFilter eq 'Assignment' ? 'selected' : ''}>Assignment</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <label class="form-label">&nbsp;</label>
                                        <div class="d-flex gap-2">
                                            <button type="submit" class="btn btn-light flex-fill">
                                                <i class="fas fa-search"></i>
                                            </button>
                                            <a href="${pageContext.request.contextPath}/admin/quiz" class="btn btn-outline-light">
                                                <i class="fas fa-times"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Quiz Table -->
                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Subject</th>
                                            <th>Level</th>
                                            <th>Questions</th>
                                            <th>Duration</th>
                                            <th>Pass Rate</th>
                                            <th>Type</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${not empty quizzes}">
                                                <c:forEach var="quiz" items="${quizzes}">
                                                    <tr>
                                                        <td><strong>#${quiz.quizId}</strong></td>
                                                        <td class="text-start">
                                                            <div class="fw-bold">${quiz.quizName}</div>
                                                            <c:if test="${not empty quiz.description}">
                                                                <small class="text-muted">${quiz.description}</small>
                                                            </c:if>
                                                        </td>
                                                        <td>
                                                            <span class="badge bg-info">${quiz.subjectName}</span>
                                                        </td>
                                                        <td>
                                                            <span class="badge level-badge
                                                                ${quiz.level eq 'Basic' ? 'bg-success' : 
                                                                  quiz.level eq 'Intermediate' ? 'bg-warning' : 'bg-danger'}">
                                                                ${quiz.level}
                                                            </span>
                                                        </td>
                                                        <td><strong>${quiz.totalQuestions}</strong></td>
                                                        <td>
                                                            <span class="badge bg-secondary">${quiz.duration} mins</span>
                                                        </td>
                                                        <td>
                                                            <span class="badge bg-primary">
                                                                <fmt:formatNumber value="${quiz.passRate}" maxFractionDigits="0"/>%
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <span class="badge 
                                                                ${quiz.type eq 'Practice' ? 'bg-success' : 
                                                                  quiz.type eq 'Exam' ? 'bg-danger' : 'bg-warning'}">
                                                                ${quiz.type}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <span class="badge ${quiz.active ? 'bg-success' : 'bg-secondary'}">
                                                                ${quiz.active ? 'Active' : 'Inactive'}
                                                            </span>
                                                        </td>
                                                        <td class="quiz-actions">
                                                            <button type="button" class="btn btn-warning btn-sm" 
                                                                    onclick="editQuiz(${quiz.quizId})" 
                                                                    data-bs-toggle="tooltip" title="Edit Quiz">
                                                                <i class="fas fa-edit"></i>
                                                            </button>
                                                            <button type="button" class="btn btn-info btn-sm" 
                                                                    onclick="manageQuestions(${quiz.quizId})" 
                                                                    data-bs-toggle="tooltip" title="Manage Questions">
                                                                <i class="fas fa-list-ul"></i>
                                                            </button>
                                                            <button type="button" class="btn btn-danger btn-sm" 
                                                                    onclick="deleteQuiz(${quiz.quizId}, '${quiz.quizName}')" 
                                                                    data-bs-toggle="tooltip" title="Delete Quiz">
                                                                <i class="fas fa-trash"></i>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td colspan="10" class="text-center py-4">
                                                        <div class="text-muted">
                                                            <i class="fas fa-question-circle fa-3x mb-3"></i>
                                                            <h4>No quizzes found</h4>
                                                            <p>There are no quizzes available. Create your first quiz!</p>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Pagination -->
                            <c:if test="${totalPages > 1}">
                                <nav aria-label="Quiz pagination" class="mt-4">
                                    <ul class="pagination justify-content-center">
                                        <c:if test="${currentPage > 1}">
                                            <li class="page-item">
                                                <a class="page-link" href="?page=${currentPage - 1}">Previous</a>
                                            </li>
                                        </c:if>
                                        
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <li class="page-item ${currentPage eq i ? 'active' : ''}">
                                                <a class="page-link" href="?page=${i}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        
                                        <c:if test="${currentPage < totalPages}">
                                            <li class="page-item">
                                                <a class="page-link" href="?page=${currentPage + 1}">Next</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Add Quiz Modal -->
    <div class="modal fade" id="addQuizModal" tabindex="-1" aria-labelledby="addQuizModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addQuizModalLabel">
                        <i class="fas fa-plus text-primary"></i> Create New Quiz
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="${pageContext.request.contextPath}/admin/quiz" method="post">
                    <input type="hidden" name="action" value="save">
                    <div class="modal-body">
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label for="quizName" class="form-label">Quiz Name *</label>
                                <input type="text" class="form-control" id="quizName" name="quizName" required>
                            </div>
                            <div class="col-md-6">
                                <label for="subjectId" class="form-label">Subject *</label>
                                <select class="form-select" id="subjectId" name="subjectId" required>
                                    <option value="">Select Subject</option>
                                    <c:forEach var="subject" items="${subjects}">
                                        <option value="${subject.subjectId}">${subject.subjectName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="level" class="form-label">Level *</label>
                                <select class="form-select" id="level" name="level" required>
                                    <option value="">Select Level</option>
                                    <option value="Basic">Basic</option>
                                    <option value="Intermediate">Intermediate</option>
                                    <option value="Advanced">Advanced</option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="type" class="form-label">Type *</label>
                                <select class="form-select" id="type" name="type" required>
                                    <option value="">Select Type</option>
                                    <option value="Practice">Practice</option>
                                    <option value="Exam">Exam</option>
                                    <option value="Assignment">Assignment</option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <label for="duration" class="form-label">Duration (minutes) *</label>
                                <input type="number" class="form-control" id="duration" name="duration" min="1" required>
                            </div>
                            <div class="col-md-6">
                                <label for="passRate" class="form-label">Pass Rate (%) *</label>
                                <input type="number" class="form-control" id="passRate" name="passRate" min="0" max="100" required>
                            </div>
                            <div class="col-12">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                            </div>
                            <div class="col-12">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="isActive" name="isActive" checked>
                                    <label class="form-check-label" for="isActive">
                                        Active Quiz
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Create Quiz</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // Initialize tooltips
        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
        var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl)
        });

        function editQuiz(quizId) {
            window.location.href = '${pageContext.request.contextPath}/admin/quiz?action=edit&id=' + quizId;
        }

        function manageQuestions(quizId) {
            window.location.href = '${pageContext.request.contextPath}/admin/questions?quizId=' + quizId;
        }

        function deleteQuiz(quizId, quizName) {
            if (confirm('Are you sure you want to delete quiz "' + quizName + '"?')) {
                window.location.href = '${pageContext.request.contextPath}/admin/quiz?action=delete&id=' + quizId;
            }
        }
    </script>
</body>
</html> 