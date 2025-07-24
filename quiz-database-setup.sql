-- Quiz Management Database Setup Script
-- Run this script to create the quizzes table and sample data

-- Create quizzes table
CREATE TABLE quizzes (
    quiz_id INT IDENTITY(1,1) PRIMARY KEY,
    quiz_name NVARCHAR(255) NOT NULL,
    description NVARCHAR(1000),
    subject_id INT,
    level NVARCHAR(50) NOT NULL CHECK (level IN ('Basic', 'Intermediate', 'Advanced')),
    duration INT NOT NULL CHECK (duration > 0), -- Duration in minutes
    pass_rate DECIMAL(5,2) NOT NULL CHECK (pass_rate >= 0 AND pass_rate <= 100), -- Pass rate percentage
    type NVARCHAR(50) NOT NULL CHECK (type IN ('Practice', 'Exam', 'Assignment')),
    total_questions INT NOT NULL CHECK (total_questions > 0),
    is_active BIT NOT NULL DEFAULT 1,
    created_date DATETIME2 NOT NULL DEFAULT GETDATE(),
    updated_date DATETIME2 NOT NULL DEFAULT GETDATE(),
    created_by INT NOT NULL,
    
    -- Foreign key constraint (assuming subjects table exists)
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);

-- Create index for better performance
CREATE INDEX IX_quizzes_subject_id ON quizzes(subject_id);
CREATE INDEX IX_quizzes_level ON quizzes(level);
CREATE INDEX IX_quizzes_type ON quizzes(type);
CREATE INDEX IX_quizzes_is_active ON quizzes(is_active);

-- Insert sample quiz data
INSERT INTO quizzes (quiz_name, description, subject_id, level, duration, pass_rate, type, total_questions, is_active, created_by) VALUES 
-- Java Programming Quizzes
('Java Basics Introduction', 'Fundamental concepts of Java programming language including variables, data types, and basic syntax.', 1, 'Basic', 30, 60.0, 'Practice', 20, 1, 1),
('Java OOP Concepts', 'Object-oriented programming principles in Java: classes, objects, inheritance, polymorphism.', 1, 'Intermediate', 45, 70.0, 'Practice', 25, 1, 1),
('Java Advanced Topics', 'Advanced Java concepts including collections, threads, and exception handling.', 1, 'Advanced', 90, 80.0, 'Exam', 40, 1, 1),

-- HTML & CSS Quizzes
('HTML Fundamentals', 'Basic HTML tags, document structure, and semantic markup.', 2, 'Basic', 25, 65.0, 'Practice', 15, 1, 1),
('CSS Styling Essentials', 'CSS selectors, properties, layouts, and responsive design basics.', 2, 'Intermediate', 40, 70.0, 'Practice', 20, 1, 1),
('Advanced CSS & Layouts', 'Flexbox, Grid, animations, and modern CSS techniques.', 2, 'Advanced', 60, 75.0, 'Assignment', 30, 1, 1),

-- JavaScript Quizzes
('JavaScript Basics', 'Variables, functions, control structures, and DOM manipulation.', 3, 'Basic', 35, 60.0, 'Practice', 18, 1, 1),
('JavaScript ES6+ Features', 'Modern JavaScript: arrow functions, promises, async/await, modules.', 3, 'Intermediate', 50, 72.0, 'Practice', 22, 1, 1),
('JavaScript Advanced Patterns', 'Design patterns, performance optimization, and advanced concepts.', 3, 'Advanced', 75, 80.0, 'Exam', 35, 1, 1),

-- Python Quizzes
('Python Introduction', 'Python syntax, variables, data types, and basic programming concepts.', 4, 'Basic', 30, 65.0, 'Practice', 16, 1, 1),
('Python Data Structures', 'Lists, dictionaries, sets, tuples, and their applications.', 4, 'Intermediate', 45, 70.0, 'Practice', 24, 1, 1),
('Python Advanced Programming', 'Object-oriented Python, decorators, generators, and libraries.', 4, 'Advanced', 85, 78.0, 'Exam', 38, 1, 1),

-- React Development Quizzes
('React Fundamentals', 'Components, JSX, props, state, and basic React concepts.', 5, 'Basic', 40, 68.0, 'Practice', 20, 1, 1),
('React Hooks & State Management', 'useState, useEffect, custom hooks, and state management patterns.', 5, 'Intermediate', 55, 72.0, 'Practice', 26, 1, 1),
('React Advanced Concepts', 'Performance optimization, testing, advanced patterns, and ecosystem.', 5, 'Advanced', 80, 82.0, 'Exam', 32, 1, 1),

-- Additional Practice Quizzes
('Web Development Fundamentals', 'Basic concepts across HTML, CSS, and JavaScript.', 2, 'Basic', 45, 65.0, 'Assignment', 25, 1, 1),
('Full Stack Development Assessment', 'Comprehensive test covering frontend and backend concepts.', 1, 'Advanced', 120, 85.0, 'Exam', 50, 1, 1),

-- Some inactive quizzes for testing
('Deprecated Java Quiz', 'Old version of Java basics quiz - no longer used.', 1, 'Basic', 30, 60.0, 'Practice', 15, 0, 1),
('Legacy CSS Quiz', 'Outdated CSS quiz covering old browser compatibility.', 2, 'Intermediate', 35, 70.0, 'Practice', 18, 0, 1);

-- Update statistics (these would typically be calculated from actual quiz attempts)
-- For demonstration purposes, we'll add some sample values

PRINT 'Quiz management database setup completed successfully!';
PRINT 'Created quizzes table with sample data.';
PRINT 'Total quizzes inserted: ' + CAST(@@ROWCOUNT AS VARCHAR);

-- Verify the data
SELECT 
    'Summary Report' AS Report,
    COUNT(*) AS TotalQuizzes,
    SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS ActiveQuizzes,
    SUM(CASE WHEN is_active = 0 THEN 1 ELSE 0 END) AS InactiveQuizzes,
    COUNT(DISTINCT subject_id) AS SubjectsCovered,
    COUNT(DISTINCT level) AS DifficultyLevels,
    COUNT(DISTINCT type) AS QuizTypes
FROM quizzes;

-- Show quizzes by subject
SELECT 
    s.subject_name,
    COUNT(q.quiz_id) AS QuizCount,
    AVG(q.duration) AS AvgDuration,
    AVG(q.pass_rate) AS AvgPassRate
FROM quizzes q
JOIN subjects s ON q.subject_id = s.subject_id
GROUP BY s.subject_name
ORDER BY QuizCount DESC; 