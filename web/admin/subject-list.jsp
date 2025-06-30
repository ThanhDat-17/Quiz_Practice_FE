<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Subject Management - Admin</title>
    
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
        .table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            font-weight: 600;
        }
        .badge {
            border-radius: 15px;
            padding: 8px 15px;
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
        .subject-image {
            width: 60px;
            height: 45px;
            object-fit: cover;
            border-radius: 8px;
        }
        .filter-section {
            background: white;
            padding: 20px;
            border-radius: 15px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .column-controls {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
            margin-bottom: 15px;
        }
        .column-checkbox {
            display: flex;
            align-items: center;
            gap: 5px;
        }
        .search-controls {
            display: flex;
            gap: 10px;
            align-items: end;
            flex-wrap: wrap;
        }
        .search-controls .form-group {
            flex: 1;
            min-width: 200px;
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
                        <a class="nav-link active" href="${pageContext.request.contextPath}/admin/subject">
                            <i class="fas fa-book"></i> Subject Management
                        </a>
                        <a class="nav-link" href="#">
                            <i class="fas fa-users"></i> User Management
                        </a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/quiz">
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
                            <i class="fas fa-book text-primary"></i> Subject Management
                        </h2>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#subjectModal" onclick="openCreateModal()">
                            <i class="fas fa-plus"></i> Create New Subject
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

                    <!-- Filter and Search Section -->
                    <div class="filter-section">
                        <!-- Column Visibility Controls -->
                        <div class="column-controls">
                            <label><strong>Show Columns:</strong></label>
                            <div class="column-checkbox">
                                <input type="checkbox" id="showId" checked onchange="toggleColumn('id-column')">
                                <label for="showId">ID</label>
                            </div>
                            <div class="column-checkbox">
                                <input type="checkbox" id="showName" checked onchange="toggleColumn('name-column')">
                                <label for="showName">Name</label>
                            </div>
                            <div class="column-checkbox">
                                <input type="checkbox" id="showImage" checked onchange="toggleColumn('image-column')">
                                <label for="showImage">Image</label>
                            </div>
                            <div class="column-checkbox">
                                <input type="checkbox" id="showDescription" checked onchange="toggleColumn('description-column')">
                                <label for="showDescription">Description</label>
                            </div>
                            <div class="column-checkbox">
                                <input type="checkbox" id="showCategory" checked onchange="toggleColumn('category-column')">
                                <label for="showCategory">Category</label>
                            </div>
                            <div class="column-checkbox">
                                <input type="checkbox" id="showStatus" checked onchange="toggleColumn('status-column')">
                                <label for="showStatus">Status</label>
                            </div>
                        </div>

                        <!-- Search Controls -->
                        <form method="post" action="${pageContext.request.contextPath}/admin/subject">
                            <input type="hidden" name="action" value="search">
                            <div class="search-controls">
                                <div class="form-group">
                                    <label for="searchName">Subject Name:</label>
                                    <input type="text" class="form-control" id="searchName" name="searchName" 
                                           value="${searchName}" placeholder="Enter subject name">
                                </div>
                                <div class="form-group">
                                    <label for="categoryId">Category:</label>
                                    <select class="form-control" id="categoryId" name="categoryId">
                                        <option value="0">All Categories</option>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.categoryId}" 
                                                    ${selectedCategoryId eq category.categoryId ? 'selected' : ''}>
                                                ${category.categoryName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="pageSize">Items per page:</label>
                                    <input type="number" class="form-control" id="pageSize" name="pageSize" 
                                           value="${pageSize}" min="1" max="100" 
                                           onchange="this.form.submit()" placeholder="10">
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-search"></i> Search
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- Statistics -->
                    <div class="row mb-4">
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <h3 class="text-primary">${totalSubjects}</h3>
                                    <p class="text-muted">Total Subjects</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <h3 class="text-success">
                                        <c:set var="activeCount" value="0"/>
                                        <c:forEach var="subject" items="${subjects}">
                                            <c:if test="${subject.active}">
                                                <c:set var="activeCount" value="${activeCount + 1}"/>
                                            </c:if>
                                        </c:forEach>
                                        ${activeCount}
                                    </h3>
                                    <p class="text-muted">Active Subjects</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card text-center">
                                <div class="card-body">
                                    <h3 class="text-info">${totalPages}</h3>
                                    <p class="text-muted">Total Pages</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Subject Table -->
                    <div class="card">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover" id="subjectTable">
                                    <thead>
                                        <tr>
                                            <th class="id-column">ID</th>
                                            <th class="name-column">Name</th>
                                            <th class="image-column">Image</th>
                                            <th class="description-column">Description</th>
                                            <th class="category-column">Category</th>
                                            <th class="status-column">Status</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="subject" items="${subjects}" varStatus="status">
                                            <tr>
                                                <td class="id-column">${subject.subjectId}</td>
                                                <td class="name-column">${subject.subjectName}</td>
                                                <td class="image-column">
                                                    <c:if test="${not empty subject.subjectImage}">
                                                        <img src="${pageContext.request.contextPath}/assets/images/blog/uploaded/${subject.subjectImage}" 
                                                             class="subject-image" alt="Subject Image">
                                                    </c:if>
                                                    <c:if test="${empty subject.subjectImage}">
                                                        <img src="${pageContext.request.contextPath}/assets/images/blog/default/thum1.jpg" 
                                                             class="subject-image" alt="Default Image">
                                                    </c:if>
                                                </td>
                                                <td class="description-column">
                                                    <c:choose>
                                                        <c:when test="${not empty subject.description}">
                                                            <div class="text-truncate" style="max-width: 200px;" title="${subject.description}">
                                                                ${subject.description}
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted">No Description</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="category-column">
                                                    <c:choose>
                                                        <c:when test="${not empty subject.categoryName}">
                                                            ${subject.categoryName}
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted">No Category</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="status-column">
                                                    <c:choose>
                                                        <c:when test="${subject.active}">
                                                            <span class="badge bg-success">Active</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-danger">Inactive</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <button class="btn btn-sm btn-warning me-1" onclick="editSubject(${subject.subjectId})"
                                                            data-bs-toggle="modal" data-bs-target="#subjectModal">
                                                        <i class="fas fa-edit"></i> Edit
                                                    </button>
                                                    <a href="${pageContext.request.contextPath}/admin/subject?action=delete&id=${subject.subjectId}" 
                                                       class="btn btn-sm btn-danger"
                                                       onclick="return confirm('Are you sure you want to delete this subject?')">
                                                        <i class="fas fa-trash"></i> Delete
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Pagination -->
                            <c:if test="${totalPages > 1}">
                                <nav aria-label="Subject pagination">
                                    <ul class="pagination justify-content-center">
                                        <c:if test="${currentPage > 1}">
                                            <li class="page-item">
                                                <a class="page-link" href="?page=${currentPage - 1}&pageSize=${pageSize}&searchName=${searchName}&categoryId=${selectedCategoryId}">
                                                    <i class="fas fa-chevron-left"></i>
                                                </a>
                                            </li>
                                        </c:if>

                                        <c:forEach begin="1" end="${totalPages}" var="page">
                                            <li class="page-item ${page eq currentPage ? 'active' : ''}">
                                                <a class="page-link" href="?page=${page}&pageSize=${pageSize}&searchName=${searchName}&categoryId=${selectedCategoryId}">
                                                    ${page}
                                                </a>
                                            </li>
                                        </c:forEach>

                                        <c:if test="${currentPage < totalPages}">
                                            <li class="page-item">
                                                <a class="page-link" href="?page=${currentPage + 1}&pageSize=${pageSize}&searchName=${searchName}&categoryId=${selectedCategoryId}">
                                                    <i class="fas fa-chevron-right"></i>
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
        </div>
    </div>

    <!-- Subject Modal -->
    <div class="modal fade" id="subjectModal" tabindex="-1" aria-labelledby="subjectModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="subjectModalLabel">Create New Subject</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id="subjectForm" method="post" action="${pageContext.request.contextPath}/admin/subject" enctype="multipart/form-data">
                    <div class="modal-body">
                        <input type="hidden" id="action" name="action" value="save">
                        <input type="hidden" id="subjectId" name="subjectId">
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="subjectName" class="form-label">Subject Name <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="subjectName" name="subjectName" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="modalCategoryId" class="form-label">Category <span class="text-danger">*</span></label>
                                    <select class="form-control" id="modalCategoryId" name="categoryId" required>
                                        <option value="">Select Category</option>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.categoryId}">${category.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description" rows="3" 
                                      placeholder="Enter subject description"></textarea>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="subjectImage" class="form-label">Thumbnail Image URL</label>
                                    <input type="url" class="form-control" id="subjectImage" name="subjectImage" placeholder="URL">
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="imageFile" class="form-label">Or Upload Image</label>
                                    <input type="file" class="form-control" id="imageFile" name="imageFile" accept="image/*">
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="isActive" name="isActive" checked>
                                <label class="form-check-label" for="isActive">
                                    Active Status
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> Save Subject
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // Toggle column visibility
        function toggleColumn(columnClass) {
            const columns = document.querySelectorAll('.' + columnClass);
            const checkbox = event.target;
            
            columns.forEach(column => {
                column.style.display = checkbox.checked ? '' : 'none';
            });
        }

        // Open create modal
        function openCreateModal() {
            document.getElementById('subjectModalLabel').textContent = 'Create New Subject';
            document.getElementById('action').value = 'save';
            document.getElementById('subjectForm').reset();
            document.getElementById('subjectId').value = '';
            document.getElementById('isActive').checked = true;
        }

        // Edit subject
        function editSubject(subjectId) {
            fetch('${pageContext.request.contextPath}/admin/subject?action=edit&id=' + subjectId)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('subjectModalLabel').textContent = 'Edit Subject';
                    document.getElementById('action').value = 'update';
                    document.getElementById('subjectId').value = data.subjectId;
                    document.getElementById('subjectName').value = data.subjectName;
                    document.getElementById('subjectImage').value = data.subjectImage;
                    document.getElementById('description').value = data.description || '';
                    document.getElementById('modalCategoryId').value = data.categoryId;
                    document.getElementById('isActive').checked = data.isActive;
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error loading subject data');
                });
        }
    </script>
</body>
</html> 