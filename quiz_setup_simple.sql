-- QUIZ MANAGEMENT SYSTEM SETUP FOR SQL SERVER
-- Run this on your EduBlog database

USE EduBlog;

PRINT 'Starting Quiz System Setup...';

-- Remove all foreign key constraints first
DECLARE @sql NVARCHAR(MAX) = '';
SELECT @sql = @sql + 'ALTER TABLE ' + QUOTENAME(OBJECT_SCHEMA_NAME(parent_object_id)) + '.' + QUOTENAME(OBJECT_NAME(parent_object_id)) + ' DROP CONSTRAINT ' + QUOTENAME(name) + '; '
FROM sys.foreign_keys
WHERE OBJECT_NAME(referenced_object_id) IN ('quizzes', 'questions', 'question_options', 'quiz_attempts', 'user_answers');

IF @sql <> ''
    EXEC sp_executesql @sql;

-- Drop all quiz tables
IF OBJECT_ID('user_answers', 'U') IS NOT NULL DROP TABLE user_answers;
IF OBJECT_ID('quiz_attempts', 'U') IS NOT NULL DROP TABLE quiz_attempts;
IF OBJECT_ID('question_options', 'U') IS NOT NULL DROP TABLE question_options;
IF OBJECT_ID('questions', 'U') IS NOT NULL DROP TABLE questions;
IF OBJECT_ID('quizzes', 'U') IS NOT NULL DROP TABLE quizzes;

PRINT 'Removed existing quiz tables';

-- CREATE QUIZZES TABLE
CREATE TABLE quizzes (
    quiz_id INT IDENTITY(1,1) PRIMARY KEY,
    quiz_name NVARCHAR(255) NOT NULL,
    description NVARCHAR(MAX),
    subject_id INT NOT NULL,
    level NVARCHAR(20) NOT NULL,
    duration INT NOT NULL,
    pass_rate DECIMAL(5,2) NOT NULL,
    quiz_type NVARCHAR(20) NOT NULL,
    number_of_questions INT NOT NULL,
    is_active BIT DEFAULT 1,
    created_date DATETIME DEFAULT GETDATE(),
    updated_date DATETIME DEFAULT GETDATE(),
    created_by INT NOT NULL
);

-- CREATE QUESTIONS TABLE
CREATE TABLE questions (
    question_id INT IDENTITY(1,1) PRIMARY KEY,
    quiz_id INT NOT NULL,
    question_text NVARCHAR(MAX) NOT NULL,
    question_type NVARCHAR(20) NOT NULL,
    question_order INT NOT NULL,
    points DECIMAL(5,2) DEFAULT 1.0,
    is_active BIT DEFAULT 1,
    created_date DATETIME DEFAULT GETDATE(),
    updated_date DATETIME DEFAULT GETDATE()
);

-- CREATE QUESTION OPTIONS TABLE
CREATE TABLE question_options (
    option_id INT IDENTITY(1,1) PRIMARY KEY,
    question_id INT NOT NULL,
    option_text NVARCHAR(MAX) NOT NULL,
    is_correct BIT DEFAULT 0,
    option_order INT NOT NULL,
    created_date DATETIME DEFAULT GETDATE()
);

-- CREATE QUIZ ATTEMPTS TABLE
CREATE TABLE quiz_attempts (
    attempt_id INT IDENTITY(1,1) PRIMARY KEY,
    quiz_id INT NOT NULL,
    user_id INT NOT NULL,
    start_time DATETIME DEFAULT GETDATE(),
    end_time DATETIME,
    score DECIMAL(5,2),
    max_score DECIMAL(5,2),
    percentage DECIMAL(5,2),
    is_passed BIT,
    time_taken INT,
    status NVARCHAR(20) DEFAULT 'IN_PROGRESS',
    created_date DATETIME DEFAULT GETDATE()
);

-- CREATE USER ANSWERS TABLE
CREATE TABLE user_answers (
    answer_id INT IDENTITY(1,1) PRIMARY KEY,
    attempt_id INT NOT NULL,
    question_id INT NOT NULL,
    selected_option_id INT,
    answer_text NVARCHAR(MAX),
    is_correct BIT,
    points_earned DECIMAL(5,2) DEFAULT 0,
    created_date DATETIME DEFAULT GETDATE()
);

PRINT 'Created all quiz tables';

-- ADD FOREIGN KEYS
ALTER TABLE questions ADD CONSTRAINT FK_questions_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id) ON DELETE CASCADE;
ALTER TABLE question_options ADD CONSTRAINT FK_question_options_question FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE;
ALTER TABLE quiz_attempts ADD CONSTRAINT FK_quiz_attempts_quiz FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id);
ALTER TABLE user_answers ADD CONSTRAINT FK_user_answers_attempt FOREIGN KEY (attempt_id) REFERENCES quiz_attempts(attempt_id) ON DELETE CASCADE;
ALTER TABLE user_answers ADD CONSTRAINT FK_user_answers_question FOREIGN KEY (question_id) REFERENCES questions(question_id);
ALTER TABLE user_answers ADD CONSTRAINT FK_user_answers_option FOREIGN KEY (selected_option_id) REFERENCES question_options(option_id);

-- ADD FOREIGN KEYS TO EXISTING TABLES (if they exist)
IF OBJECT_ID('subjects', 'U') IS NOT NULL
    ALTER TABLE quizzes ADD CONSTRAINT FK_quizzes_subject FOREIGN KEY (subject_id) REFERENCES subjects(subject_id);

IF OBJECT_ID('users', 'U') IS NOT NULL
BEGIN
    ALTER TABLE quizzes ADD CONSTRAINT FK_quizzes_user FOREIGN KEY (created_by) REFERENCES users(user_id);
    ALTER TABLE quiz_attempts ADD CONSTRAINT FK_quiz_attempts_user FOREIGN KEY (user_id) REFERENCES users(user_id);
END

PRINT 'Added foreign key constraints';

-- CREATE INDEXES
CREATE INDEX IX_quizzes_subject_id ON quizzes(subject_id);
CREATE INDEX IX_quizzes_created_by ON quizzes(created_by);
CREATE INDEX IX_questions_quiz_id ON questions(quiz_id);
CREATE INDEX IX_question_options_question_id ON question_options(question_id);
CREATE INDEX IX_quiz_attempts_quiz_id ON quiz_attempts(quiz_id);
CREATE INDEX IX_quiz_attempts_user_id ON quiz_attempts(user_id);
CREATE INDEX IX_user_answers_attempt_id ON user_answers(attempt_id);

PRINT 'Created indexes';

-- INSERT SAMPLE DATA
IF OBJECT_ID('subjects', 'U') IS NOT NULL AND OBJECT_ID('users', 'U') IS NOT NULL
BEGIN
    IF EXISTS (SELECT 1 FROM subjects WHERE subject_id = 1) AND EXISTS (SELECT 1 FROM users WHERE user_id = 1)
    BEGIN
        INSERT INTO quizzes (quiz_name, description, subject_id, level, duration, pass_rate, quiz_type, number_of_questions, is_active, created_by) 
        VALUES 
        ('Java Basics Quiz', 'Test your understanding of Java fundamentals', 1, 'EASY', 30, 70.0, 'PRACTICE', 10, 1, 1),
        ('Web Development Quiz', 'HTML, CSS and JavaScript basics', 1, 'MEDIUM', 45, 75.0, 'ASSIGNMENT', 15, 1, 1),
        ('Advanced Programming', 'Complex programming concepts', 1, 'HARD', 60, 80.0, 'MOCK_TEST', 20, 1, 1);
        
        PRINT 'Inserted sample quizzes';
        
        -- Add sample questions for first quiz
        DECLARE @quiz_id INT = (SELECT TOP 1 quiz_id FROM quizzes ORDER BY quiz_id);
        IF @quiz_id IS NOT NULL
        BEGIN
            INSERT INTO questions (quiz_id, question_text, question_type, question_order, points) VALUES
            (@quiz_id, 'Which is NOT a primitive type in Java?', 'MULTIPLE_CHOICE', 1, 1.0),
            (@quiz_id, 'Java is platform-independent', 'TRUE_FALSE', 2, 1.0);
            
            DECLARE @q1 INT = (SELECT question_id FROM questions WHERE quiz_id = @quiz_id AND question_order = 1);
            DECLARE @q2 INT = (SELECT question_id FROM questions WHERE quiz_id = @quiz_id AND question_order = 2);
            
            INSERT INTO question_options (question_id, option_text, is_correct, option_order) VALUES
            (@q1, 'int', 0, 1),
            (@q1, 'String', 1, 2),
            (@q1, 'boolean', 0, 3),
            (@q1, 'char', 0, 4),
            (@q2, 'True', 1, 1),
            (@q2, 'False', 0, 2);
            
            PRINT 'Inserted sample questions and options';
        END
    END
    ELSE
        PRINT 'Missing data in subjects or users table - only tables created';
END
ELSE
    PRINT 'subjects or users table not found - only quiz tables created';

-- VERIFY SETUP
DECLARE @quizCount INT = (SELECT COUNT(*) FROM quizzes);
PRINT '';
PRINT '========================================';
PRINT 'QUIZ SYSTEM SETUP COMPLETED!';
PRINT '========================================';
PRINT 'Sample quizzes created: ' + CAST(@quizCount AS VARCHAR(10));
PRINT 'Access at: http://localhost:8080/your-app/admin/quiz';
PRINT '========================================'; 