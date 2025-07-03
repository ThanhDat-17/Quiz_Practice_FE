<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${empty quiz ? 'Add New Quiz' : 'Edit Quiz'} - Admin</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <!-- TinyMCE -->
    <script src="https://cdn.tiny.cloud/1/appkf01fglmbp3bywnorda4vkksajowuz4bz6plo46fmuyz2/tinymce/7/tinymce.min.js" referrerpolicy="origin"></script>
    
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
        .btn-secondary {
            border-radius: 25px;
            padding: 10px 25px;
        }
        .form-control, .form-select {
            border-radius: 10px;
            border: 1px solid #ddd;
            transition: all 0.3s ease;
        }
        .form-control:focus, .form-select:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .page-title {
            color: #333;
            margin-bottom: 30px;
            font-weight: 700;
        }
        .form-section {
            background: white;
            border-radius: 15px;
            padding: 25px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .section-title {
            color: #667eea;
            font-weight: 600;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #f0f0f0;
        }
        .required {
            color: #dc3545;
        }
        .form-text {
            color: #6c757d;
            font-size: 0.875rem;
        }
        .level-info, .type-info {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px;
            border-radius: 10px;
            margin-top: 10px;
        }
        .info-item {
            margin-bottom: 8px;
        }
        .info-item:last-child {
            margin-bottom: 0;
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
                            <i class="fas fa-${empty quiz ? 'plus' : 'edit'} text-primary"></i> 
                            ${empty quiz ? 'Add New Quiz' : 'Edit Quiz'}
                        </h2>
                        <a href="${pageContext.request.contextPath}/admin/quiz" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Back to List
                        </a>
                    </div>

                    <form action="${pageContext.request.contextPath}/admin/quiz" method="post" id="quizForm">
                        <input type="hidden" name="action" value="${empty quiz ? 'save' : 'update'}">
                        <c:if test="${not empty quiz}">
                            <input type="hidden" name="quizId" value="${quiz.quizId}">
                        </c:if>

                        <!-- Basic Information -->
                        <div class="form-section">
                            <h4 class="section-title">
                                <i class="fas fa-info-circle"></i> Basic Information
                            </h4>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="quizName" class="form-label">
                                        Quiz Name <span class="required">*</span>
                                    </label>
                                    <input type="text" class="form-control" id="quizName" name="quizName" 
                                           value="${quiz.quizName}" required>
                                    <div class="form-text">Enter a descriptive name for the quiz</div>
                                </div>
                                <div class="col-md-6">
                                    <label for="subjectId" class="form-label">
                                        Subject <span class="required">*</span>
                                    </label>
                                    <select class="form-select" id="subjectId" name="subjectId" required>
                                        <option value="">Select Subject</option>
                                        <c:forEach var="subject" items="${subjects}">
                                            <option value="${subject.subjectId}" 
                                                ${quiz.subjectId eq subject.subjectId ? 'selected' : ''}>
                                                ${subject.subjectName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <div class="form-text">Choose the subject category for this quiz</div>
                                </div>
                                <div class="col-12">
                                    <label for="description" class="form-label">Description</label>
                                    <textarea class="form-control" id="description" name="description" 
                                              rows="3" placeholder="Enter quiz description...">${quiz.description}</textarea>
                                    <div class="form-text">Provide a brief description of what this quiz covers</div>
                                </div>
                            </div>
                        </div>

                        <!-- Quiz Configuration -->
                        <div class="form-section">
                            <h4 class="section-title">
                                <i class="fas fa-cogs"></i> Quiz Configuration
                            </h4>
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label for="level" class="form-label">
                                        Difficulty Level <span class="required">*</span>
                                    </label>
                                    <select class="form-select" id="level" name="level" required>
                                        <option value="">Select Level</option>
                                        <option value="EASY" ${quiz.level eq 'EASY' ? 'selected' : ''}>Basic</option>
                                        <option value="MEDIUM" ${quiz.level eq 'MEDIUM' ? 'selected' : ''}>Intermediate</option>
                                        <option value="HARD" ${quiz.level eq 'HARD' ? 'selected' : ''}>Advanced</option>
                                    </select>
                                    <div class="level-info">
                                        <div class="info-item"><strong>Basic:</strong> Entry-level questions for beginners</div>
                                        <div class="info-item"><strong>Intermediate:</strong> Moderate difficulty for regular learners</div>
                                        <div class="info-item"><strong>Advanced:</strong> Challenging questions for experts</div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="type" class="form-label">
                                        Quiz Type <span class="required">*</span>
                                    </label>
                                    <select class="form-select" id="type" name="type" required>
                                        <option value="">Select Type</option>
                                        <option value="PRACTICE" ${quiz.type eq 'PRACTICE' ? 'selected' : ''}>Practice</option>
                                        <option value="MOCK_TEST" ${quiz.type eq 'MOCK_TEST' ? 'selected' : ''}>Exam</option>
                                        <option value="ASSIGNMENT" ${quiz.type eq 'ASSIGNMENT' ? 'selected' : ''}>Assignment</option>
                                    </select>
                                    <div class="type-info">
                                        <div class="info-item"><strong>Practice:</strong> Self-paced learning and practice</div>
                                        <div class="info-item"><strong>Exam:</strong> Formal assessment with strict timing</div>
                                        <div class="info-item"><strong>Assignment:</strong> Graded homework or coursework</div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Quiz Parameters -->
                        <div class="form-section">
                            <h4 class="section-title">
                                <i class="fas fa-clock"></i> Quiz Parameters
                            </h4>
                            <div class="row g-3">
                                                                <div class="col-md-6">
                                    <label for="duration" class="form-label">
                                        Duration (minutes) <span class="required">*</span>
                                    </label>
                                    <input type="number" class="form-control" id="duration" name="duration" 
                                           value="${quiz.duration}" min="1" max="300" required>
                                    <div class="form-text">Time limit for completing the quiz (1-300 minutes)</div>
                                </div>
                                <div class="col-md-6">
                                    <label for="passRate" class="form-label">
                                        Pass Rate (%) <span class="required">*</span>
                                    </label>
                                    <input type="number" class="form-control" id="passRate" name="passRate" 
                                           value="${quiz.passRate}" min="0" max="100" step="0.1" required>
                                    <div class="form-text">Minimum score percentage to pass (0-100%)</div>
                                </div>
                            </div>
                        </div>

                        <!-- Quiz Status -->
                        <div class="form-section">
                            <h4 class="section-title">
                                <i class="fas fa-toggle-on"></i> Quiz Status
                            </h4>
                            <div class="form-check form-switch">
                                <input class="form-check-input" type="checkbox" id="isActive" name="isActive" 
                                       ${empty quiz ? 'checked' : (quiz.active ? 'checked' : '')}>
                                <label class="form-check-label" for="isActive">
                                    <strong>Active Quiz</strong>
                                </label>
                                <div class="form-text">Only active quizzes are visible to students</div>
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <div class="text-end">
                            <a href="${pageContext.request.contextPath}/admin/quiz" class="btn btn-secondary me-2">
                                <i class="fas fa-times"></i> Cancel
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-save"></i> ${empty quiz ? 'Create Quiz' : 'Update Quiz'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // Initialize TinyMCE
        tinymce.init({
            selector: '#description',
            height: 400,
            menubar: false,
            plugins: [
                'advlist', 'autolink', 'lists', 'link', 'image', 'charmap', 'preview',
                'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
                'insertdatetime', 'media', 'table', 'help', 'wordcount'
            ],
            toolbar: 'undo redo | blocks | ' +
                'bold italic forecolor | alignleft aligncenter ' +
                'alignright alignjustify | bullist numlist outdent indent | ' +
                'removeformat | image media link | help',
            content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }',
            image_title: true,
            automatic_uploads: true,
            file_picker_types: 'image',
            file_picker_callback: function (cb, value, meta) {
                var input = document.createElement('input');
                input.setAttribute('type', 'file');
                input.setAttribute('accept', 'image/*');

                input.onchange = function () {
                    var file = this.files[0];
                    var reader = new FileReader();
                    reader.onload = function () {
                        var id = 'blobid' + (new Date()).getTime();
                        var blobCache = tinymce.activeEditor.editorUpload.blobCache;
                        var base64 = reader.result.split(',')[1];
                        var blobInfo = blobCache.create(id, file, base64);
                        blobCache.add(blobInfo);

                        cb(blobInfo.blobUri(), { title: file.name });
                    };
                    reader.readAsDataURL(file);
                };

                input.click();
            },
            media_live_embeds: true,
            media_url_resolver: function (data, resolve) {
                if (data.url.indexOf('youtube.com') !== -1 || data.url.indexOf('youtu.be') !== -1) {
                    var embedHtml = '<iframe width="560" height="315" src="' + 
                        data.url.replace('watch?v=', 'embed/').replace('youtu.be/', 'youtube.com/embed/') + 
                        '" frameborder="0" allowfullscreen></iframe>';
                    resolve({html: embedHtml});
                } else {
                    resolve({html: ''});
                }
            },
            setup: function (editor) {
                editor.on('init', function () {
                    console.log('TinyMCE initialized successfully');
                });
            }
        });
    </script>
    <script>
        // Form validation
        document.getElementById('quizForm').addEventListener('submit', function(e) {
            // Update TinyMCE content to textarea before validation
            tinymce.triggerSave();
            
            const duration = parseInt(document.getElementById('duration').value);
            const passRate = parseFloat(document.getElementById('passRate').value);
            
            let errors = [];
            
            if (duration < 1 || duration > 300) {
                errors.push('Duration must be between 1 and 300 minutes');
            }
            
            if (passRate < 0 || passRate > 100) {
                errors.push('Pass rate must be between 0 and 100%');
            }
            
            if (errors.length > 0) {
                e.preventDefault();
                alert('Please fix the following errors:\n\n' + errors.join('\n'));
                return false;
            }
            
            return true;
        });

        // Dynamic form helpers
        document.getElementById('level').addEventListener('change', function() {
            const level = this.value;
            const passRateInput = document.getElementById('passRate');
            
            // Suggest pass rates based on level
            if (level === 'EASY') {
                passRateInput.placeholder = 'Suggested: 60-70%';
            } else if (level === 'MEDIUM') {
                passRateInput.placeholder = 'Suggested: 70-80%';
            } else if (level === 'HARD') {
                passRateInput.placeholder = 'Suggested: 80-90%';
            }
        });

        document.getElementById('type').addEventListener('change', function() {
            const type = this.value;
            const durationInput = document.getElementById('duration');
            
            // Suggest duration based on type
            if (type === 'PRACTICE') {
                durationInput.placeholder = 'Suggested: 15-30 mins';
            } else if (type === 'MOCK_TEST') {
                durationInput.placeholder = 'Suggested: 60-120 mins';
            } else if (type === 'ASSIGNMENT') {
                durationInput.placeholder = 'Suggested: 30-90 mins';
            }
        });
    </script>
</body>
</html> 