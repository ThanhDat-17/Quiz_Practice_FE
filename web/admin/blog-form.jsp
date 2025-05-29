<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${empty blog ? 'Add New Blog' : 'Edit Blog'} - Admin</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <!-- Summernote CSS -->
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs5.min.css" rel="stylesheet">
    
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
        .form-label {
            font-weight: 600;
            color: #333;
            margin-bottom: 8px;
        }
        .form-control {
            border-radius: 10px;
            border: 2px solid #e9ecef;
            transition: all 0.3s ease;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .page-title {
            color: #333;
            margin-bottom: 30px;
            font-weight: 700;
        }
        .image-preview {
            max-width: 300px;
            max-height: 200px;
            border-radius: 10px;
            border: 2px dashed #ddd;
            padding: 10px;
            text-align: center;
        }
        .image-preview img {
            max-width: 100%;
            max-height: 100%;
            border-radius: 8px;
        }
        .image-upload-area {
            border: 2px dashed #667eea;
            border-radius: 10px;
            padding: 30px;
            text-align: center;
            background: rgba(102, 126, 234, 0.05);
            transition: all 0.3s ease;
            cursor: pointer;
        }
        .image-upload-area:hover {
            background: rgba(102, 126, 234, 0.1);
            border-color: #5a6fd8;
        }
        .form-check-input:checked {
            background-color: #667eea;
            border-color: #667eea;
        }
        .note-editor {
            border-radius: 10px;
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
                        <a class="nav-link active" href="${pageContext.request.contextPath}/admin/blog">
                            <i class="fas fa-blog"></i> Blog Management
                        </a>
                        <a class="nav-link" href="#">
                            <i class="fas fa-users"></i> User Management
                        </a>
                        <a class="nav-link" href="#">
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
                            <i class="fas fa-${empty blog ? 'plus' : 'edit'} text-primary"></i> 
                            ${empty blog ? 'Add New Blog' : 'Edit Blog'}
                        </h2>
                        <a href="${pageContext.request.contextPath}/admin/blog" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Back to List
                        </a>
                    </div>

                    <!-- Form -->
                    <div class="card">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin/blog" method="post" id="blogForm" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="${empty blog ? 'save' : 'update'}">
                                <c:if test="${not empty blog}">
                                    <input type="hidden" name="blogId" value="${blog.blogId}">
                                </c:if>

                                <div class="row">
                                    <!-- Left Column -->
                                    <div class="col-md-8">
                                        <!-- Title -->
                                        <div class="mb-4">
                                            <label for="title" class="form-label">
                                                <i class="fas fa-heading text-primary"></i> Blog Title *
                                            </label>
                                            <input type="text" class="form-control form-control-lg" id="title" name="title" 
                                                   value="${blog.title}" required placeholder="Enter blog title...">
                                        </div>

                                        <!-- Content -->
                                        <div class="mb-4">
                                            <label for="content" class="form-label">
                                                <i class="fas fa-align-left text-primary"></i> Blog Content *
                                            </label>
                                            <textarea class="form-control" id="content" name="content" rows="15" required>${blog.content}</textarea>
                                        </div>
                                    </div>

                                    <!-- Right Column -->
                                    <div class="col-md-4">
                                        <!-- Image Upload -->
                                        <div class="mb-4">
                                            <label for="imageFile" class="form-label">
                                                <i class="fas fa-image text-primary"></i> Featured Image
                                            </label>
                                            
                                            <div class="image-upload-area" onclick="document.getElementById('imageFile').click()">
                                                <div class="image-preview" id="imagePreview">
                                                    <c:choose>
                                                        <c:when test="${not empty blog.image}">
                                                            <img src="${pageContext.request.contextPath}/${blog.image}" 
                                                                 alt="Current Image" id="previewImg">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div id="uploadPlaceholder">
                                                                <i class="fas fa-cloud-upload-alt fa-3x text-muted mb-3"></i>
                                                                <p class="text-muted">Click to upload image</p>
                                                                <small class="text-muted">Supported: JPG, PNG, GIF</small>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            
                                            <input type="file" id="imageFile" name="imageFile" accept="image/*" class="form-control mt-2">
                                            <input type="hidden" name="image" id="image" value="${blog.image}">
                                            
                                            <div class="mt-2">
                                                <small class="text-muted">
                                                    <i class="fas fa-info-circle"></i> 
                                                    Or enter image URL manually:
                                                </small>
                                                <input type="text" class="form-control mt-1" id="imageUrl" 
                                                       placeholder="https://example.com/image.jpg" value="${blog.image}">
                                            </div>
                                        </div>

                                        <!-- Status -->
                                        <div class="mb-4">
                                            <label class="form-label">
                                                <i class="fas fa-toggle-on text-primary"></i> Status
                                            </label>
                                            <div class="form-check form-switch">
                                                <input class="form-check-input" type="checkbox" id="isActive" name="isActive" 
                                                       ${blog.active || empty blog ? 'checked' : ''}>
                                                <label class="form-check-label" for="isActive">
                                                    <span class="badge bg-success" id="statusBadge">
                                                        <i class="fas fa-check-circle"></i> Active
                                                    </span>
                                                </label>
                                            </div>
                                            <small class="text-muted">
                                                <i class="fas fa-info-circle"></i> 
                                                Active blogs will be visible to users
                                            </small>
                                        </div>

                                        <!-- Created Date (for edit) -->
                                        <c:if test="${not empty blog}">
                                            <div class="mb-4">
                                                <label class="form-label">
                                                    <i class="fas fa-calendar text-primary"></i> Created Date
                                                </label>
                                                <input type="text" class="form-control" readonly
                                                       value="<fmt:formatDate value='${blog.createdDateAsDate}' pattern='dd/MM/yyyy'/>">
                                            </div>
                                        </c:if>

                                        <!-- Save Button -->
                                        <div class="d-grid gap-2">
                                            <button type="submit" class="btn btn-primary btn-lg">
                                                <i class="fas fa-save"></i> 
                                                ${empty blog ? 'Create Blog' : 'Update Blog'}
                                            </button>
                                            <button type="button" class="btn btn-outline-secondary" 
                                                    onclick="resetForm()">
                                                <i class="fas fa-undo"></i> Reset
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs5.min.js"></script>
    
    <script>
        $(document).ready(function() {
            // Initialize Summernote
            $('#content').summernote({
                height: 400,
                toolbar: [
                    ['style', ['style']],
                    ['font', ['bold', 'underline', 'clear']],
                    ['fontname', ['fontname']],
                    ['color', ['color']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['table', ['table']],
                    ['insert', ['link', 'picture', 'video']],
                    ['view', ['fullscreen', 'codeview', 'help']]
                ],
                placeholder: 'Write your blog content here...'
            });

            // Handle status switch
            $('#isActive').change(function() {
                const badge = $('#statusBadge');
                if (this.checked) {
                    badge.removeClass('bg-danger').addClass('bg-success');
                    badge.html('<i class="fas fa-check-circle"></i> Active');
                } else {
                    badge.removeClass('bg-success').addClass('bg-danger');
                    badge.html('<i class="fas fa-times-circle"></i> Inactive');
                }
            });

            // Handle image upload
            $('#imageFile').change(function(event) {
                const file = event.target.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        $('#imagePreview').html('<img src="' + e.target.result + '" id="previewImg" alt="Preview">');
                    };
                    reader.readAsDataURL(file);
                }
            });

            // Handle image URL input
            $('#imageUrl').on('input', function() {
                const url = $(this).val();
                $('#image').val(url);
                if (url) {
                    $('#imagePreview').html('<img src="' + url + '" id="previewImg" alt="Preview">');
                } else {
                    $('#imagePreview').html($('#uploadPlaceholder').clone());
                }
            });

            // Form validation
            $('#blogForm').on('submit', function(e) {
                const title = $('#title').val().trim();
                const content = $('#content').summernote('code').trim();
                
                if (!title) {
                    alert('Please enter a blog title');
                    e.preventDefault();
                    return false;
                }
                
                if (!content || content === '<p><br></p>') {
                    alert('Please enter blog content');
                    e.preventDefault();
                    return false;
                }
                
                return true;
            });
        });

        function resetForm() {
            if (confirm('Are you sure you want to reset the form? All changes will be lost.')) {
                $('#blogForm')[0].reset();
                $('#content').summernote('reset');
                $('#imagePreview').html($('#uploadPlaceholder').clone());
                $('#isActive').prop('checked', true).trigger('change');
            }
        }
    </script>
</body>
</html> 