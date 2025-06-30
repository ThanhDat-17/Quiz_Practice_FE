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
    <!-- SweetAlert2 -->
    <link href="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.19/dist/sweetalert2.min.css" rel="stylesheet">
    
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
            transition: all 0.3s ease;
        }
        .card:hover {
            box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
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
            background: linear-gradient(135deg, #20bf6b 0%, #26d0ce 100%);
            border: none;
            border-radius: 25px;
            padding: 8px 20px;
        }
        .btn-warning {
            background: linear-gradient(135deg, #f7b731 0%, #fd79a8 100%);
            border: none;
            border-radius: 25px;
            padding: 8px 20px;
        }
        .btn-danger {
            background: linear-gradient(135deg, #ee5a52 0%, #f093fb 100%);
            border: none;
            border-radius: 25px;
            padding: 8px 20px;
        }
        .table {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            font-weight: 600;
            text-align: center;
            vertical-align: middle;
        }
        .table td {
            vertical-align: middle;
            text-align: center;
            border: none;
            border-bottom: 1px solid #e9ecef;
        }
        .table tbody tr:hover {
            background-color: #f8f9fa;
            transform: scale(1.01);
            transition: all 0.3s ease;
        }
        .badge {
            border-radius: 15px;
            padding: 8px 15px;
            font-size: 0.75rem;
            font-weight: 600;
        }
        .badge-success {
            background: linear-gradient(135deg, #20bf6b 0%, #26d0ce 100%);
        }
        .badge-danger {
            background: linear-gradient(135deg, #ee5a52 0%, #f093fb 100%);
        }
        .badge-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .badge-warning {
            background: linear-gradient(135deg, #f7b731 0%, #fd79a8 100%);
        }
        .badge-info {
            background: linear-gradient(135deg, #3742fa 0%, #2f3542 100%);
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
        .filter-section {
            background: white;
            padding: 25px;
            border-radius: 15px;
            margin-bottom: 25px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .search-controls {
            display: flex;
            gap: 15px;
            align-items: end;
            flex-wrap: wrap;
        }
        .search-controls .form-group {
            flex: 1;
            min-width: 200px;
        }
        .search-controls .btn {
            height: fit-content;
        }
        .pagination .page-link {
            border-radius: 50px;
            margin: 0 3px;
            border: none;
            color: #667eea;
        }
        .pagination .page-item.active .page-link {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
        }
        .stats-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px;
            padding: 20px;
            margin-bottom: 20px;
        }
        .level-easy { background: linear-gradient(135deg, #20bf6b 0%, #26d0ce 100%) !important; }
        .level-medium { background: linear-gradient(135deg, #f7b731 0%, #fd79a8 100%) !important; }
        .level-hard { background: linear-gradient(135deg, #ee5a52 0%, #f093fb 100%) !important; }
        .type-practice { background: linear-gradient(135deg, #3742fa 0%, #2f3542 100%) !important; }
        .type-mock-test { background: linear-gradient(135deg, #f7b731 0%, #fd79a8 100%) !important; }
        .type-assignment { background: linear-gradient(135deg, #ee5a52 0%, #f093fb 100%) !important; }
        .modal-content {
            border-radius: 15px;
            border: none;
        }
        .modal-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 15px 15px 0 0;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .action-buttons {
            display: flex;
            gap: 5px;
            justify-content: center;
        }
        .action-buttons .btn {
            padding: 5px 10px;
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
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#quizModal" onclick="openCreateModal()">
                            <i class="fas fa-plus"></i> Create New Quiz
                        </button>
                    </div>

                    <!-- Statistics Cards -->
                    <div class="row mb-4">
                        <div class="col-md-3">
                            <div class="stats-card">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h5>Total Quizzes</h5>
                                        <h3>${totalQuizzes}</h3>
                                    </div>
                                    <i class="fas fa-question-circle fa-2x"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="stats-card">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h5>Current Page</h5>
                                        <h3>${currentPage}</h3>
                                    </div>
                                    <i class="fas fa-file-alt fa-2x"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="stats-card">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h5>Page Size</h5>
                                        <h3>${pageSize}</h3>
                                    </div>
                                    <i class="fas fa-list fa-2x"></i>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="stats-card">
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <h5>Total Pages</h5>
                                        <h3>${totalPages}</h3>
                                    </div>
                                    <i class="fas fa-pager fa-2x"></i>
                                </div>
                            </div>
                        </div>
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

                    <!-- Filter and Search Section -->
                    <div class="filter-section">
                        <h5 class="mb-3"><i class="fas fa-filter"></i> Search & Filter</h5>
                        <form method="post" action="${pageContext.request.contextPath}/admin/quiz">
                            <input type="hidden" name="action" value="search">
                            <div class="search-controls">
                                <div class="form-group">
                                    <label for="searchName">Quiz Name:</label>
                                    <input type="text" class="form-control" id="searchName" name="searchName" 
                                           value="${searchName}" placeholder="Enter quiz name">
                                </div>
                                <div class="form-group">
                                    <label for="subjectId">Subject:</label>
                                    <select class="form-select" id="subjectId" name="subjectId">
                                        <option value="0">All Subjects</option>
                                        <c:forEach var="subject" items="${subjects}">
                                            <option value="${subject.subjectId}" 
                                                    ${selectedSubjectId eq subject.subjectId ? 'selected' : ''}>
                                                ${subject.subjectName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="quizType">Quiz Type:</label>
                                    <select class="form-select" id="quizType" name="quizType">
                                        <option value="ALL">All Types</option>
                                        <option value="PRACTICE" ${selectedQuizType eq 'PRACTICE' ? 'selected' : ''}>Practice</option>
                                        <option value="MOCK_TEST" ${selectedQuizType eq 'MOCK_TEST' ? 'selected' : ''}>Mock Test</option>
                                        <option value="ASSIGNMENT" ${selectedQuizType eq 'ASSIGNMENT' ? 'selected' : ''}>Assignment</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="pageSize">Page Size:</label>
                                    <select class="form-select" id="pageSize" name="pageSize">
                                        <option value="5" ${pageSize eq 5 ? 'selected' : ''}>5</option>
                                        <option value="10" ${pageSize eq 10 ? 'selected' : ''}>10</option>
                                        <option value="25" ${pageSize eq 25 ? 'selected' : ''}>25</option>
                                        <option value="50" ${pageSize eq 50 ? 'selected' : ''}>50</option>
                                    </select>
                                </div>
                                <div>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-search"></i> Search
                                    </button>
                                    <a href="${pageContext.request.contextPath}/admin/quiz" class="btn btn-secondary">
                                        <i class="fas fa-undo"></i> Reset
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- Quiz Table -->
                    <div class="card">
                        <div class="card-body p-0">
                            <div class="table-responsive">
                                <table class="table table-hover mb-0">
                                    <thead>
                                        <tr>
                                            <th width="5%">ID</th>
                                            <th width="20%">Quiz Name</th>
                                            <th width="15%">Subject</th>
                                            <th width="8%">Level</th>
                                            <th width="8%">Questions</th>
                                            <th width="8%">Duration</th>
                                            <th width="8%">Pass Rate</th>
                                            <th width="10%">Type</th>
                                            <th width="8%">Status</th>
                                            <th width="10%">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${empty quizzes}">
                                                <tr>
                                                    <td colspan="10" class="text-center py-4">
                                                        <i class="fas fa-inbox fa-3x text-muted mb-3"></i>
                                                        <h5 class="text-muted">No quizzes found</h5>
                                                        <p class="text-muted">Try adjusting your search criteria or create a new quiz.</p>
                                                    </td>
                                                </tr>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach var="quiz" items="${quizzes}">
                                                    <tr>
                                                        <td><strong>#${quiz.quizId}</strong></td>
                                                        <td class="text-start">
                                                            <div>
                                                                <strong>${quiz.quizName}</strong>
                                                                <c:if test="${not empty quiz.description}">
                                                                    <small class="d-block text-muted">${quiz.description.length() > 50 ? quiz.description.substring(0, 50).concat('...') : quiz.description}</small>
                                                                </c:if>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <span class="badge badge-primary">${quiz.subjectName}</span>
                                                        </td>
                                                        <td>
                                                            <span class="badge level-${quiz.level.toLowerCase()}">${quiz.levelDisplay}</span>
                                                        </td>
                                                        <td>
                                                            <span class="badge badge-info">${quiz.numberOfQuestions}</span>
                                                        </td>
                                                        <td>
                                                            <span class="badge badge-warning">${quiz.durationDisplay}</span>
                                                        </td>
                                                        <td>
                                                            <span class="badge badge-success">${quiz.passRate}%</span>
                                                        </td>
                                                        <td>
                                                            <span class="badge type-${quiz.quizType.toLowerCase().replace('_', '-')}">${quiz.quizTypeDisplay}</span>
                                                        </td>
                                                        <td>
                                                            <span class="badge ${quiz.active ? 'badge-success' : 'badge-danger'}">
                                                                ${quiz.statusDisplay}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <div class="action-buttons">
                                                                <button type="button" class="btn btn-warning btn-sm" 
                                                                        onclick="editQuiz(${quiz.quizId})"
                                                                        title="Edit Quiz">
                                                                    <i class="fas fa-edit"></i>
                                                                </button>
                                                                <button type="button" class="btn btn-danger btn-sm" 
                                                                        onclick="deleteQuiz(${quiz.quizId}, '${quiz.quizName}')"
                                                                        title="Delete Quiz">
                                                                    <i class="fas fa-trash"></i>
                                                                </button>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Pagination -->
                    <c:if test="${totalPages > 1}">
                        <nav aria-label="Quiz pagination" class="mt-4">
                            <ul class="pagination justify-content-center">
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=1&pageSize=${pageSize}&searchName=${searchName}&subjectId=${selectedSubjectId}&quizType=${selectedQuizType}&action=search">
                                            <i class="fas fa-angle-double-left"></i>
                                        </a>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentPage-1}&pageSize=${pageSize}&searchName=${searchName}&subjectId=${selectedSubjectId}&quizType=${selectedQuizType}&action=search">
                                            <i class="fas fa-angle-left"></i>
                                        </a>
                                    </li>
                                </c:if>
                                
                                <c:forEach var="i" begin="${currentPage > 3 ? currentPage - 2 : 1}" 
                                           end="${currentPage + 2 < totalPages ? currentPage + 2 : totalPages}">
                                    <li class="page-item ${i eq currentPage ? 'active' : ''}">
                                        <a class="page-link" href="?page=${i}&pageSize=${pageSize}&searchName=${searchName}&subjectId=${selectedSubjectId}&quizType=${selectedQuizType}&action=search">${i}</a>
                                    </li>
                                </c:forEach>
                                
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${currentPage+1}&pageSize=${pageSize}&searchName=${searchName}&subjectId=${selectedSubjectId}&quizType=${selectedQuizType}&action=search">
                                            <i class="fas fa-angle-right"></i>
                                        </a>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${totalPages}&pageSize=${pageSize}&searchName=${searchName}&subjectId=${selectedSubjectId}&quizType=${selectedQuizType}&action=search">
                                            <i class="fas fa-angle-double-right"></i>
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <!-- Quiz Modal -->
    <div class="modal fade" id="quizModal" tabindex="-1" aria-labelledby="quizModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="quizModalLabel">
                        <i class="fas fa-question-circle"></i> Create New Quiz
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id="quizForm" method="post" action="${pageContext.request.contextPath}/admin/quiz">
                    <div class="modal-body">
                        <input type="hidden" name="action" id="formAction" value="save">
                        <input type="hidden" name="quizId" id="quizId">
                        
                        <div class="row">
                            <div class="col-md-12 mb-3">
                                <label for="quizName" class="form-label">Quiz Name <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="quizName" name="quizName" required>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-12 mb-3">
                                <label for="description" class="form-label">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3" placeholder="Optional description for the quiz"></textarea>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="modalSubjectId" class="form-label">Subject <span class="text-danger">*</span></label>
                                <select class="form-select" id="modalSubjectId" name="subjectId" required>
                                    <option value="">Select Subject</option>
                                    <c:forEach var="subject" items="${subjects}">
                                        <option value="${subject.subjectId}">${subject.subjectName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="level" class="form-label">Level <span class="text-danger">*</span></label>
                                <select class="form-select" id="level" name="level" required>
                                    <option value="">Select Level</option>
                                    <option value="EASY">Easy</option>
                                    <option value="MEDIUM">Medium</option>
                                    <option value="HARD">Hard</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="duration" class="form-label">Duration (minutes) <span class="text-danger">*</span></label>
                                <input type="number" class="form-control" id="duration" name="duration" min="1" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="passRate" class="form-label">Pass Rate (%) <span class="text-danger">*</span></label>
                                <input type="number" class="form-control" id="passRate" name="passRate" min="0" max="100" step="0.1" required>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="modalQuizType" class="form-label">Quiz Type <span class="text-danger">*</span></label>
                                <select class="form-select" id="modalQuizType" name="quizType" required>
                                    <option value="">Select Type</option>
                                    <option value="PRACTICE">Practice</option>
                                    <option value="MOCK_TEST">Mock Test</option>
                                    <option value="ASSIGNMENT">Assignment</option>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="numberOfQuestions" class="form-label">Number of Questions <span class="text-danger">*</span></label>
                                <input type="number" class="form-control" id="numberOfQuestions" name="numberOfQuestions" min="1" required>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-12 mb-3">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="isActive" name="isActive" value="true" checked>
                                    <label class="form-check-label" for="isActive">
                                        Active Status
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                            <i class="fas fa-times"></i> Cancel
                        </button>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> Save Quiz
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.4.19/dist/sweetalert2.min.js"></script>
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script>
        function openCreateModal() {
            document.getElementById('quizModalLabel').innerHTML = '<i class="fas fa-plus"></i> Create New Quiz';
            document.getElementById('formAction').value = 'save';
            document.getElementById('quizForm').reset();
            document.getElementById('quizId').value = '';
            document.getElementById('isActive').checked = true;
        }

        function editQuiz(quizId) {
            fetch('${pageContext.request.contextPath}/admin/quiz?action=edit&id=' + quizId)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('quizModalLabel').innerHTML = '<i class="fas fa-edit"></i> Edit Quiz';
                    document.getElementById('formAction').value = 'update';
                    document.getElementById('quizId').value = data.quizId;
                    document.getElementById('quizName').value = data.quizName;
                    document.getElementById('description').value = data.description;
                    document.getElementById('modalSubjectId').value = data.subjectId;
                    document.getElementById('level').value = data.level;
                    document.getElementById('duration').value = data.duration;
                    document.getElementById('passRate').value = data.passRate;
                    document.getElementById('modalQuizType').value = data.quizType;
                    document.getElementById('numberOfQuestions').value = data.numberOfQuestions;
                    document.getElementById('isActive').checked = data.isActive;
                    
                    new bootstrap.Modal(document.getElementById('quizModal')).show();
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        title: 'Error!',
                        text: 'Failed to load quiz data.',
                        icon: 'error',
                        confirmButtonColor: '#667eea'
                    });
                });
        }

        function deleteQuiz(quizId, quizName) {
            Swal.fire({
                title: 'Are you sure?',
                text: `You want to delete the quiz "${quizName}"? This action cannot be undone.`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#ee5a52',
                cancelButtonColor: '#6c757d',
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'Cancel'
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = '${pageContext.request.contextPath}/admin/quiz?action=delete&id=' + quizId;
                }
            });
        }

        // Form validation
        document.getElementById('quizForm').addEventListener('submit', function(e) {
            const duration = parseInt(document.getElementById('duration').value);
            const passRate = parseFloat(document.getElementById('passRate').value);
            const numberOfQuestions = parseInt(document.getElementById('numberOfQuestions').value);

            if (duration < 1) {
                e.preventDefault();
                Swal.fire({
                    title: 'Invalid Duration!',
                    text: 'Duration must be at least 1 minute.',
                    icon: 'error',
                    confirmButtonColor: '#667eea'
                });
                return;
            }

            if (passRate < 0 || passRate > 100) {
                e.preventDefault();
                Swal.fire({
                    title: 'Invalid Pass Rate!',
                    text: 'Pass rate must be between 0 and 100.',
                    icon: 'error',
                    confirmButtonColor: '#667eea'
                });
                return;
            }

            if (numberOfQuestions < 1) {
                e.preventDefault();
                Swal.fire({
                    title: 'Invalid Number of Questions!',
                    text: 'Number of questions must be at least 1.',
                    icon: 'error',
                    confirmButtonColor: '#667eea'
                });
                return;
            }
        });

        // Auto-submit form when page size changes
        document.getElementById('pageSize').addEventListener('change', function() {
            this.form.submit();
        });
    </script>
</body>
</html> 