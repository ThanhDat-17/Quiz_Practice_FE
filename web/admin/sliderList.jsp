<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Slider Management - Admin</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .sidebar { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; box-shadow: 2px 0 5px rgba(0,0,0,0.1); }
        .sidebar .nav-link { color: white; padding: 15px 20px; border-radius: 8px; margin: 5px 10px; transition: all 0.3s ease; }
        .sidebar .nav-link:hover, .sidebar .nav-link.active { background: rgba(255,255,255,0.2); color: white; transform: translateX(5px); }
        .content-wrapper { background-color: #f8f9fa; min-height: 100vh; }
        .card { border: none; border-radius: 15px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none; border-radius: 25px; padding: 10px 25px; transition: all 0.3s ease; }
        .btn-primary:hover { transform: translateY(-2px); box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4); }
        .table th { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border: none; font-weight: 600; }
        .badge { border-radius: 15px; padding: 8px 15px; }
        .alert { border-radius: 15px; border: none; }
        .page-title { color: #333; margin-bottom: 30px; font-weight: 700; }
        .slider-image { width: 80px; height: 60px; object-fit: cover; border-radius: 8px; }
        .content-preview { max-width: 300px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; }
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
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/quiz">
                        <i class="fas fa-question-circle"></i> Quiz Management
                    </a>
                    <a class="nav-link active" href="${pageContext.request.contextPath}/admin/slider-list">
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
                        <i class="fas fa-sliders-h text-primary"></i> Slider Management
                    </h2>
                    <a href="${pageContext.request.contextPath}/admin/slider-form" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Add New Slider
                    </a>
                </div>
                <!-- Search/Filter Form -->
                <form class="row g-3 mb-4" method="get" action="${pageContext.request.contextPath}/admin/slider-list">
                    <div class="col-md-4">
                        <input type="text" class="form-control" name="search" placeholder="Search by title or backlink..." value="${search}">
                    </div>
                    <div class="col-md-3">
                        <select class="form-select" name="status">
                            <option value="all" ${status eq 'all' ? 'selected' : ''}>All Statuses</option>
                            <option value="show" ${status eq 'show' ? 'selected' : ''}>Show</option>
                            <option value="hide" ${status eq 'hide' ? 'selected' : ''}>Hide</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100"><i class="fas fa-search"></i> Filter</button>
                    </div>
                </form>
                <!-- Slider Table -->
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Image</th>
                                    <th>Title</th>
                                    <th>Backlink</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="slider" items="${sliders}">
                                    <tr>
                                        <td><strong>#${slider.slider_id}</strong></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty slider.image}">
                                                    <img src="${pageContext.request.contextPath}/${slider.image}" alt="Slider Image" class="slider-image">
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="slider-image bg-light d-flex align-items-center justify-content-center">
                                                        <i class="fas fa-image text-muted"></i>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><strong>${slider.title}</strong></td>
                                        <td><a href="${slider.backlink}" target="_blank">${slider.backlink}</a></td>
                                        <td>
                                            <span class="badge ${slider.status eq 'show' ? 'bg-success' : 'bg-secondary'}">
                                                ${slider.status eq 'show' ? 'Show' : 'Hide'}
                                            </span>
                                        </td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/admin/slider-form?id=${slider.slider_id}" class="btn btn-sm btn-outline-primary" title="Edit">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <c:choose>
                                                    <c:when test="${slider.status eq 'show'}">
                                                        <a href="${pageContext.request.contextPath}/admin/slider-list?action=hide&id=${slider.slider_id}&status=${status}&search=${search}&page=${currentPage}" class="btn btn-sm btn-outline-warning" title="Hide">
                                                            <i class="fas fa-eye-slash"></i>
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="${pageContext.request.contextPath}/admin/slider-list?action=show&id=${slider.slider_id}&status=${status}&search=${search}&page=${currentPage}" class="btn btn-sm btn-outline-success" title="Show">
                                                            <i class="fas fa-eye"></i>
                                                        </a>
                                                    </c:otherwise>
                                                </c:choose>
                                                <button type="button" class="btn btn-sm btn-outline-danger" title="Delete" onclick="confirmDelete(${slider.slider_id})">
                                                    <i class="fas fa-trash"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- Pagination -->
                        <c:if test="${totalPages > 1}">
                            <nav aria-label="Slider pagination">
                                <ul class="pagination justify-content-center">
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/admin/slider-list?page=${currentPage - 1}&status=${status}&search=${search}">
                                            <i class="fas fa-chevron-left"></i> Previous
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/admin/slider-list?page=${i}&status=${status}&search=${search}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/admin/slider-list?page=${currentPage + 1}&status=${status}&search=${search}">
                                            Next <i class="fas fa-chevron-right"></i>
                                        </a>
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
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Delete Confirmation Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirm Delete</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to delete this slider? This action cannot be undone.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <form id="deleteForm" method="post" action="">
                    <input type="hidden" name="action" value="delete" />
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
function confirmDelete(sliderId) {
    var form = document.getElementById('deleteForm');
    form.action = '${pageContext.request.contextPath}/admin/slider-form?id=' + sliderId;
    var modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    modal.show();
}
</script>
</body>
</html> 