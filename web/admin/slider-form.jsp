<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${slider != null ? 'Edit Slider' : 'Add New Slider'} - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .sidebar { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; box-shadow: 2px 0 5px rgba(0,0,0,0.1); }
        .sidebar .nav-link { color: white; padding: 15px 20px; border-radius: 8px; margin: 5px 10px; transition: all 0.3s ease; }
        .sidebar .nav-link:hover, .sidebar .nav-link.active { background: rgba(255,255,255,0.2); color: white; transform: translateX(5px); }
        .content-wrapper { background-color: #f8f9fa; min-height: 100vh; }
        .card { border: none; border-radius: 15px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none; border-radius: 25px; padding: 10px 25px; transition: all 0.3s ease; }
        .btn-primary:hover { transform: translateY(-2px); box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4); }
        .page-title { color: #333; margin-bottom: 30px; font-weight: 700; }
        .form-label { font-weight: 600; }
        .form-section { background: #fff; border-radius: 15px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); padding: 32px; }
        .img-preview { width: 120px; height: 80px; object-fit: cover; border-radius: 8px; border: 1px solid #eee; }
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
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="page-title">
                        <i class="fas fa-sliders-h text-primary"></i> ${slider != null ? 'Edit Slider' : 'Add New Slider'}
                    </h2>
                    <a href="${pageContext.request.contextPath}/admin/slider-list" class="btn btn-outline-secondary">
                        <i class="fas fa-arrow-left"></i> Back to List
                    </a>
                </div>
                <div class="form-section">
                    <form method="post" action="${pageContext.request.contextPath}/admin/slider-form" enctype="multipart/form-data">
                        <c:if test="${slider != null}">
                            <input type="hidden" name="slider_id" value="${slider.slider_id}" />
                            <input type="hidden" name="oldImage" value="${slider.image}" />
                        </c:if>
                        <div class="mb-3">
                            <label class="form-label">Title</label>
                            <input type="text" class="form-control" name="title" value="${slider.title}" required maxlength="255" />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Image</label>
                            <input type="file" class="form-control" name="imageFile" accept="image/*" />
                            <c:if test="${slider != null && slider.image != null}">
                                <img src="${pageContext.request.contextPath}/${slider.image}" class="img-preview mt-2" alt="Current Image" />
                            </c:if>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Backlink</label>
                            <input type="url" class="form-control" name="backlink" value="${slider.backlink}" maxlength="255" />
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Status</label>
                            <select class="form-select" name="status" required>
                                <option value="show" ${slider.status eq 'show' ? 'selected' : ''}>Show</option>
                                <option value="hide" ${slider.status eq 'hide' ? 'selected' : ''}>Hide</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Notes</label>
                            <textarea class="form-control" name="notes" maxlength="255">${slider.notes}</textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> ${slider != null ? 'Update Slider' : 'Add Slider'}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 