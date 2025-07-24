<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Question Management - ${quiz.quizName}</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        :root {
            --primary-color: #0d6efd;
            --success-color: #198754;
            --warning-color: #ffc107;
            --danger-color: #dc3545;
            --info-color: #0dcaf0;
            --light-color: #f8f9fa;
            --dark-color: #212529;
        }

        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .main-container {
            padding: 2rem 0;
        }

        .header-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
            border: none;
        }

        .header-content {
            padding: 2rem;
        }

        .quiz-info {
            background: linear-gradient(45deg, var(--primary-color), var(--info-color));
            color: white;
            border-radius: 10px;
            padding: 1.5rem;
            margin-bottom: 1rem;
        }

        .content-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            border: none;
            overflow: hidden;
        }

        .question-item {
            border: 1px solid #e9ecef;
            border-radius: 10px;
            margin-bottom: 1rem;
            transition: all 0.3s ease;
        }

        .question-item:hover {
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            transform: translateY(-2px);
        }

        .question-header {
            background: var(--light-color);
            padding: 1rem;
            border-radius: 10px 10px 0 0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .question-content {
            padding: 1rem;
        }

        .option-item {
            display: flex;
            align-items: center;
            padding: 0.5rem;
            margin: 0.25rem 0;
            border-radius: 5px;
            background: #f8f9fa;
        }

        .option-item.correct {
            background: #d1e7dd;
            border-left: 4px solid var(--success-color);
        }

        .btn-action {
            margin: 0 0.25rem;
            padding: 0.375rem 0.75rem;
            border-radius: 5px;
            transition: all 0.3s ease;
        }

        .btn-action:hover {
            transform: translateY(-1px);
        }

        .stats-row {
            background: var(--light-color);
            border-radius: 10px;
            padding: 1rem;
            margin-bottom: 1.5rem;
        }

        .stat-item {
            text-align: center;
        }

        .stat-number {
            font-size: 2rem;
            font-weight: bold;
            color: var(--primary-color);
        }

        .stat-label {
            color: #6c757d;
            font-size: 0.9rem;
        }

        .modal-header {
            background: linear-gradient(45deg, var(--primary-color), var(--info-color));
            color: white;
            border-radius: 15px 15px 0 0;
        }

        .form-floating > .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
        }

        .option-input-group {
            margin-bottom: 1rem;
        }

        .option-input {
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .option-input input[type="radio"] {
            margin-top: 0;
        }

        .btn-add-option {
            background: var(--success-color);
            border: none;
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            transition: all 0.3s ease;
        }

        .btn-remove-option {
            background: var(--danger-color);
            border: none;
            color: white;
            padding: 0.25rem 0.5rem;
            border-radius: 3px;
        }

        .empty-state {
            text-align: center;
            padding: 3rem;
            color: #6c757d;
        }

        .empty-state i {
            font-size: 4rem;
            margin-bottom: 1rem;
            opacity: 0.5;
        }

        @media (max-width: 768px) {
            .main-container {
                padding: 1rem 0;
            }
            
            .header-content {
                padding: 1rem;
            }
            
            .btn-action {
                padding: 0.25rem 0.5rem;
                font-size: 0.8rem;
            }
        }
    </style>
</head>
<body>
    <div class="main-container">
        <div class="container">
            <!-- Header Section -->
            <div class="header-card">
                <div class="header-content">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <div>
                            <h1 class="h3 mb-0">
                                <i class="fas fa-question-circle text-primary"></i>
                                Question Management
                            </h1>
                            <p class="text-muted mb-0">Manage questions for quiz</p>
                        </div>
                        <div>
                            <a href="${pageContext.request.contextPath}/admin/quiz" 
                               class="btn btn-outline-secondary">
                                <i class="fas fa-arrow-left"></i> Back to Quiz List
                            </a>
                        </div>
                    </div>

                    <!-- Quiz Information -->
                    <div class="quiz-info">
                        <div class="row">
                            <div class="col-md-8">
                                <h4 class="mb-2">
                                    <i class="fas fa-clipboard-question"></i>
                                    ${quiz.quizName}
                                </h4>
                                <p class="mb-1">
                                    <strong>Subject:</strong> ${quiz.subjectName} | 
                                    <strong>Level:</strong> ${quiz.level} | 
                                    <strong>Type:</strong> ${quiz.type}
                                </p>
                                <p class="mb-0">
                                    <strong>Duration:</strong> ${quiz.duration} minutes | 
                                    <strong>Pass Rate:</strong> <fmt:formatNumber value="${quiz.passRate}" maxFractionDigits="0"/>%
                                </p>
                            </div>
                            <div class="col-md-4 text-end">
                                <div class="stat-item">
                                    <div class="stat-number text-white">${questionCount}</div>
                                    <div class="stat-label text-white-50">Total Questions</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Action Buttons -->
                    <div class="d-flex justify-content-between align-items-center">
                        <div class="stats-row flex-grow-1 me-3">
                            <div class="row text-center">
                                <div class="col-md-4">
                                    <div class="stat-item">
                                        <div class="stat-number">${questionCount}</div>
                                        <div class="stat-label">Questions</div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="stat-item">
                                        <div class="stat-number">${quiz.duration}</div>
                                        <div class="stat-label">Minutes</div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="stat-item">
                                        <div class="stat-number">
                                            <fmt:formatNumber value="${quiz.passRate}" maxFractionDigits="0"/>%
                                        </div>
                                        <div class="stat-label">Pass Rate</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <button type="button" class="btn btn-primary btn-lg" 
                                    data-bs-toggle="modal" data-bs-target="#addQuestionModal">
                                <i class="fas fa-plus"></i> Add Question
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Questions List -->
            <div class="content-card">
                <div class="card-body p-4">
                    <c:choose>
                        <c:when test="${not empty questions}">
                            <div class="questions-container">
                                <c:forEach var="question" items="${questions}" varStatus="status">
                                    <div class="question-item" data-question-id="${question.questionId}">
                                        <div class="question-header">
                                            <div>
                                                <span class="badge bg-primary me-2">Question ${status.index + 1}</span>
                                                <span class="badge bg-info">${question.questionType}</span>
                                                <span class="badge bg-warning">${question.points} points</span>
                                            </div>
                                            <div class="question-actions">
                                                <button type="button" class="btn btn-warning btn-action btn-sm" 
                                                        onclick="editQuestion(${question.questionId})"
                                                        data-bs-toggle="tooltip" title="Edit Question">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <button type="button" class="btn btn-danger btn-action btn-sm" 
                                                        onclick="deleteQuestion(${question.questionId})"
                                                        data-bs-toggle="tooltip" title="Delete Question">
                                                    <i class="fas fa-trash"></i>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="question-content">
                                            <div class="question-text mb-3">
                                                <strong>Q: </strong>${question.questionText}
                                            </div>
                                            <div class="options-list">
                                                <c:forEach var="option" items="${question.options}" varStatus="optStatus">
                                                    <div class="option-item ${option.correct ? 'correct' : ''}">
                                                        <span class="option-letter me-2">
                                                            ${optStatus.index == 0 ? 'A' : optStatus.index == 1 ? 'B' : 
                                                              optStatus.index == 2 ? 'C' : optStatus.index == 3 ? 'D' : 
                                                              optStatus.index == 4 ? 'E' : optStatus.index + 1}.
                                                        </span>
                                                        <span class="option-text flex-grow-1">${option.optionText}</span>
                                                        <c:if test="${option.correct}">
                                                            <span class="badge bg-success ms-2">
                                                                <i class="fas fa-check"></i> Correct
                                                            </span>
                                                        </c:if>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <i class="fas fa-question-circle"></i>
                                <h4>No Questions Yet</h4>
                                <p>Start by adding your first question to this quiz.</p>
                                <button type="button" class="btn btn-primary" 
                                        data-bs-toggle="modal" data-bs-target="#addQuestionModal">
                                    <i class="fas fa-plus"></i> Add First Question
                                </button>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <!-- Add Question Modal -->
    <div class="modal fade" id="addQuestionModal" tabindex="-1" aria-labelledby="addQuestionModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addQuestionModalLabel">
                        <i class="fas fa-plus"></i> Add New Question
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="addQuestionForm">
                        <input type="hidden" name="quizId" value="${quiz.quizId}">
                        <input type="hidden" name="action" value="add">
                        
                        <div class="row g-3">
                            <div class="col-md-8">
                                <div class="form-floating">
                                    <textarea class="form-control" id="questionText" name="questionText" 
                                            placeholder="Enter your question" style="height: 100px" required></textarea>
                                    <label for="questionText">Question Text *</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="row g-2">
                                    <div class="col-12">
                                        <div class="form-floating">
                                            <select class="form-select" id="questionType" name="questionType" required>
                                                <option value="">Select Type</option>
                                                <option value="Multiple Choice">Multiple Choice</option>
                                                <option value="True/False">True/False</option>
                                            </select>
                                            <label for="questionType">Question Type *</label>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-floating">
                                            <input type="number" class="form-control" id="points" name="points" 
                                                   placeholder="Points" min="0.5" step="0.5" value="1" required>
                                            <label for="points">Points *</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <hr class="my-4">

                        <div class="options-section">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h6 class="mb-0">Answer Options</h6>
                                <button type="button" class="btn btn-add-option" onclick="addOption()">
                                    <i class="fas fa-plus"></i> Add Option
                                </button>
                            </div>
                            
                            <div id="optionsContainer">
                                <!-- Options will be dynamically added here -->
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" onclick="saveQuestion()">
                        <i class="fas fa-save"></i> Save Question
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Question Modal -->
    <div class="modal fade" id="editQuestionModal" tabindex="-1" aria-labelledby="editQuestionModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editQuestionModalLabel">
                        <i class="fas fa-edit"></i> Edit Question
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="editQuestionForm">
                        <input type="hidden" name="questionId" id="editQuestionId">
                        <input type="hidden" name="quizId" value="${quiz.quizId}">
                        <input type="hidden" name="action" value="update">
                        
                        <div class="row g-3">
                            <div class="col-md-8">
                                <div class="form-floating">
                                    <textarea class="form-control" id="editQuestionText" name="questionText" 
                                            placeholder="Enter your question" style="height: 100px" required></textarea>
                                    <label for="editQuestionText">Question Text *</label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="row g-2">
                                    <div class="col-12">
                                        <div class="form-floating">
                                            <select class="form-select" id="editQuestionType" name="questionType" required>
                                                <option value="">Select Type</option>
                                                <option value="Multiple Choice">Multiple Choice</option>
                                                <option value="True/False">True/False</option>
                                            </select>
                                            <label for="editQuestionType">Question Type *</label>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-floating">
                                            <input type="number" class="form-control" id="editPoints" name="points" 
                                                   placeholder="Points" min="0.5" step="0.5" required>
                                            <label for="editPoints">Points *</label>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-floating">
                                            <input type="number" class="form-control" id="editQuestionOrder" name="questionOrder" 
                                                   placeholder="Order" min="1" required>
                                            <label for="editQuestionOrder">Order *</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <hr class="my-4">

                        <div class="options-section">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <h6 class="mb-0">Answer Options</h6>
                                <button type="button" class="btn btn-add-option" onclick="addEditOption()">
                                    <i class="fas fa-plus"></i> Add Option
                                </button>
                            </div>
                            
                            <div id="editOptionsContainer">
                                <!-- Options will be dynamically loaded here -->
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-primary" onclick="updateQuestion()">
                        <i class="fas fa-save"></i> Update Question
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        let optionCounter = 0;
        let editOptionCounter = 0;

        // Initialize tooltips
        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
        var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl)
        });

        // Initialize with 4 options when modal opens
        document.getElementById('addQuestionModal').addEventListener('shown.bs.modal', function () {
            if (document.getElementById('optionsContainer').children.length === 0) {
                for (let i = 0; i < 4; i++) {
                    addOption();
                }
            }
        });

        function addOption() {
            optionCounter++;
            const container = document.getElementById('optionsContainer');
            const optionDiv = document.createElement('div');
            optionDiv.className = 'option-input-group';
            optionDiv.innerHTML = `
                <div class="option-input">
                    <input type="radio" name="correctOption" value="${optionCounter}" class="form-check-input">
                    <input type="text" class="form-control" placeholder="Option text" name="option_${optionCounter}" required>
                    <button type="button" class="btn btn-remove-option" onclick="removeOption(this)">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            `;
            container.appendChild(optionDiv);
        }

        function removeOption(button) {
            const container = document.getElementById('optionsContainer');
            if (container.children.length > 2) {
                button.closest('.option-input-group').remove();
            } else {
                alert('A question must have at least 2 options.');
            }
        }

        function addEditOption() {
            editOptionCounter++;
            const container = document.getElementById('editOptionsContainer');
            const optionDiv = document.createElement('div');
            optionDiv.className = 'option-input-group';
            optionDiv.innerHTML = `
                <div class="option-input">
                    <input type="radio" name="editCorrectOption" value="${editOptionCounter}" class="form-check-input">
                    <input type="text" class="form-control" placeholder="Option text" name="edit_option_${editOptionCounter}" required>
                    <button type="button" class="btn btn-remove-option" onclick="removeEditOption(this)">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
            `;
            container.appendChild(optionDiv);
        }

        function removeEditOption(button) {
            const container = document.getElementById('editOptionsContainer');
            if (container.children.length > 2) {
                button.closest('.option-input-group').remove();
            } else {
                alert('A question must have at least 2 options.');
            }
        }

        function saveQuestion() {
            const form = document.getElementById('addQuestionForm');
            const formData = new FormData();
            
            // Add basic form data
            formData.append('quizId', form.quizId.value);
            formData.append('action', form.action.value);
            formData.append('questionText', form.questionText.value);
            formData.append('questionType', form.questionType.value);
            formData.append('points', form.points.value);
            
            // Collect options
            const options = [];
            const optionsContainer = document.getElementById('optionsContainer');
            const optionGroups = optionsContainer.querySelectorAll('.option-input-group');
            const correctOption = document.querySelector('input[name="correctOption"]:checked');
            
            if (!correctOption) {
                alert('Please select the correct answer.');
                return;
            }
            
            optionGroups.forEach((group, index) => {
                const textInput = group.querySelector('input[type="text"]');
                const radioInput = group.querySelector('input[type="radio"]');
                
                if (textInput.value.trim()) {
                    options.push({
                        optionText: textInput.value.trim(),
                        isCorrect: radioInput.checked,
                        optionOrder: index + 1
                    });
                }
            });
            
            if (options.length < 2) {
                alert('Please provide at least 2 options.');
                return;
            }
            
            formData.append('options', JSON.stringify(options));
            
            // Submit form
            fetch('${pageContext.request.contextPath}/admin/questions', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Question added successfully!');
                    location.reload();
                } else {
                    alert('Error: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while saving the question.');
            });
        }

        function editQuestion(questionId) {
            fetch('${pageContext.request.contextPath}/admin/questions?action=edit&questionId=' + questionId)
            .then(response => response.json())
            .then(data => {
                // Populate edit form
                document.getElementById('editQuestionId').value = data.questionId;
                document.getElementById('editQuestionText').value = data.questionText;
                document.getElementById('editQuestionType').value = data.questionType;
                document.getElementById('editPoints').value = data.points;
                document.getElementById('editQuestionOrder').value = data.questionOrder;
                
                // Clear and populate options
                const container = document.getElementById('editOptionsContainer');
                container.innerHTML = '';
                editOptionCounter = 0;
                
                data.options.forEach((option, index) => {
                    addEditOption();
                    const lastGroup = container.lastElementChild;
                    const textInput = lastGroup.querySelector('input[type="text"]');
                    const radioInput = lastGroup.querySelector('input[type="radio"]');
                    
                    textInput.value = option.optionText;
                    if (option.correct) {
                        radioInput.checked = true;
                    }
                });
                
                // Show modal
                new bootstrap.Modal(document.getElementById('editQuestionModal')).show();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error loading question data.');
            });
        }

        function updateQuestion() {
            const form = document.getElementById('editQuestionForm');
            const formData = new FormData();
            
            // Add basic form data
            formData.append('questionId', form.questionId.value);
            formData.append('quizId', form.quizId.value);
            formData.append('action', form.action.value);
            formData.append('questionText', form.questionText.value);
            formData.append('questionType', form.questionType.value);
            formData.append('points', form.points.value);
            formData.append('questionOrder', form.questionOrder.value);
            
            // Collect options
            const options = [];
            const optionsContainer = document.getElementById('editOptionsContainer');
            const optionGroups = optionsContainer.querySelectorAll('.option-input-group');
            const correctOption = document.querySelector('input[name="editCorrectOption"]:checked');
            
            if (!correctOption) {
                alert('Please select the correct answer.');
                return;
            }
            
            optionGroups.forEach((group, index) => {
                const textInput = group.querySelector('input[type="text"]');
                const radioInput = group.querySelector('input[type="radio"]');
                
                if (textInput.value.trim()) {
                    options.push({
                        optionText: textInput.value.trim(),
                        isCorrect: radioInput.checked,
                        optionOrder: index + 1
                    });
                }
            });
            
            if (options.length < 2) {
                alert('Please provide at least 2 options.');
                return;
            }
            
            formData.append('options', JSON.stringify(options));
            
            // Submit form
            fetch('${pageContext.request.contextPath}/admin/questions', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Question updated successfully!');
                    location.reload();
                } else {
                    alert('Error: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while updating the question.');
            });
        }

        function deleteQuestion(questionId) {
            if (confirm('Are you sure you want to delete this question?')) {
                fetch('${pageContext.request.contextPath}/admin/questions?action=delete&questionId=' + questionId, {
                    method: 'GET'
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert('Question deleted successfully!');
                        location.reload();
                    } else {
                        alert('Error: ' + data.message);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('An error occurred while deleting the question.');
                });
            }
        }

        // Clear form when add modal is closed
        document.getElementById('addQuestionModal').addEventListener('hidden.bs.modal', function () {
            document.getElementById('addQuestionForm').reset();
            document.getElementById('optionsContainer').innerHTML = '';
            optionCounter = 0;
        });
    </script>
</body>
</html> 