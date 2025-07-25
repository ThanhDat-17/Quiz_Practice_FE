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
                        <a class="nav-link${pageContext.request.servletPath eq '/admin/blog' ? ' active' : ''}" href="${pageContext.request.contextPath}/admin/blog">
                            <i class="fas fa-blog"></i> Blog Management
                        </a>
                        <a class="nav-link${pageContext.request.servletPath eq '/admin/subject' ? ' active' : ''}" href="${pageContext.request.contextPath}/admin/subject">
                            <i class="fas fa-book"></i> Subject Management
                        </a>
                        <a class="nav-link" href="#">
                            <i class="fas fa-users"></i> User Management
                        </a>
                        <a class="nav-link${pageContext.request.servletPath eq '/admin/quiz' ? ' active' : ''}" href="${pageContext.request.contextPath}/admin/quiz">
                            <i class="fas fa-question-circle"></i> Quiz Management
                        </a>
                        <a class="nav-link${pageContext.request.servletPath eq '/admin/slider-list' ? ' active' : ''}" href="${pageContext.request.contextPath}/admin/slider-list">
                            <i class="fas fa-sliders-h"></i> Slider Management
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
                        <a href="${pageContext.request.contextPath}/admin/quiz?action=add" class="btn btn-primary">
                            <i class="fas fa-plus"></i> Create New Quiz
                        </a>
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
                                            <option value="PRACTICE" ${typeFilter eq 'PRACTICE' ? 'selected' : ''}>Practice</option>
                                            <option value="MOCK_TEST" ${typeFilter eq 'MOCK_TEST' ? 'selected' : ''}>Exam</option>
                                            <option value="ASSIGNMENT" ${typeFilter eq 'ASSIGNMENT' ? 'selected' : ''}>Assignment</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2 d-flex align-items-end">
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
                                                                <small class="text-muted">${quiz.shortDescription}</small>
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
                                                        <td><strong>${quiz.numberOfQuestions}</strong></td>
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
                                                        <td>
                                                            <div class="quiz-actions">
                                                                <a href="${pageContext.request.contextPath}/admin/quiz?action=edit&id=${quiz.quizId}" 
                                                                   class="btn btn-sm btn-info" title="Edit Quiz">
                                                                    <i class="fas fa-edit"></i>
                                                                </a>
                                                                <a href="${pageContext.request.contextPath}/admin/questions?quizId=${quiz.quizId}" 
                                                                   class="btn btn-sm btn-success" title="Manage Questions">
                                                                    <i class="fas fa-list-ul"></i>
                                                                </a>
                                                                <button type="button" class="btn btn-sm btn-danger" title="Delete Quiz"
                                                                        onclick="confirmDelete(${quiz.quizId})">
                                                                    <i class="fas fa-trash"></i>
                                                                </button>
                                                            </div>
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
                                <nav aria-label="Page navigation">
                                    <ul class="pagination justify-content-center">
                                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/admin/quiz?page=${currentPage - 1}&search=${searchKeyword}&subjectFilter=${subjectFilter}&typeFilter=${typeFilter}">Previous</a>
                                        </li>
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                                <a class="page-link" href="${pageContext.request.contextPath}/admin/quiz?page=${i}&search=${searchKeyword}&subjectFilter=${subjectFilter}&typeFilter=${typeFilter}">${i}</a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/admin/quiz?page=${currentPage + 1}&search=${searchKeyword}&subjectFilter=${subjectFilter}&typeFilter=${typeFilter}">Next</a>
                                        </li>
                                    </ul>
                                </nav>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="deleteModalLabel">Confirm Deletion</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            Are you sure you want to delete this quiz? This action cannot be undone.
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <a id="deleteConfirmBtn" href="#" class="btn btn-danger">Delete</a>
          </div>
        </div>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        function confirmDelete(quizId) {
            var deleteUrl = '${pageContext.request.contextPath}/admin/quiz?action=delete&id=' + quizId;
            document.getElementById('deleteConfirmBtn').setAttribute('href', deleteUrl);
            var deleteModal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));
            deleteModal.show();
        }
    </script>
</body>
</html> 