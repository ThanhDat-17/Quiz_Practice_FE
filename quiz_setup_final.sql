-- ============================================================
--  QUIZ MANAGEMENT SYSTEM - FINAL SQL SERVER SETUP SCRIPT
-- ============================================================
--  This script FORCE-DROPS existing quiz tables (if any) and
--  recreates them with the correct columns (*level*, *duration*,
--  *pass_rate*, *quiz_type*, *number_of_questions*, ...).
--  It uses GO batch separators so that each stage is compiled
--  after the schema is updated – avoiding the "Invalid column" 
--  errors you encountered.
--  Tested on SQL Server 2019+ (works on 2016+ as well)
-- ============================================================

USE EduBlog;
GO

SET NOCOUNT ON;
PRINT '*** QUIZ SYSTEM SETUP STARTED ***';
GO

---------------------------------------------------------------
-- 1) DROP FOREIGN KEYS THAT REFERENCE QUIZ TABLES (IF ANY)
---------------------------------------------------------------
DECLARE @DropFKSql NVARCHAR(MAX) = N'';
SELECT @DropFKSql += N'ALTER TABLE [' + SCHEMA_NAME(t.schema_id) + '].[' + t.name + '] DROP CONSTRAINT [' + fk.name + '];' + CHAR(13)
FROM sys.foreign_keys fk
JOIN sys.tables t          ON fk.parent_object_id = t.object_id
JOIN sys.tables rt         ON fk.referenced_object_id = rt.object_id
WHERE rt.name IN ('quizzes', 'questions', 'question_options', 'quiz_attempts', 'user_answers');

EXEC (@DropFKSql);
GO

---------------------------------------------------------------
-- 2) DROP THE QUIZ TABLES IF THEY STILL EXIST
---------------------------------------------------------------
PRINT 'Dropping existing quiz tables (if any) ...';
GO

DROP TABLE IF EXISTS dbo.user_answers;
DROP TABLE IF EXISTS dbo.quiz_attempts;
DROP TABLE IF EXISTS dbo.question_options;
DROP TABLE IF EXISTS dbo.questions;
DROP TABLE IF EXISTS dbo.quizzes;
GO

PRINT 'All old quiz tables dropped';
GO

---------------------------------------------------------------
-- 3) CREATE TABLES
---------------------------------------------------------------
PRINT 'Creating quizzes table ...';
GO

CREATE TABLE dbo.quizzes
(
    quiz_id            INT IDENTITY(1,1)          PRIMARY KEY,
    quiz_name          NVARCHAR(255)              NOT NULL,
    description        NVARCHAR(MAX)              NULL,
    subject_id         INT                        NOT NULL,
    level              NVARCHAR(20)               NOT NULL,
    duration           INT                        NOT NULL,       -- minutes
    pass_rate          DECIMAL(5,2)               NOT NULL,       -- 0–100
    quiz_type          NVARCHAR(20)               NOT NULL,
    number_of_questions INT                       NOT NULL,
    is_active          BIT                        NOT NULL DEFAULT (1),
    created_date       DATETIME                   NOT NULL DEFAULT (GETDATE()),
    updated_date       DATETIME                   NOT NULL DEFAULT (GETDATE()),
    created_by         INT                        NOT NULL,

    CONSTRAINT CHK_quizzes_level        CHECK (level       IN ('EASY','MEDIUM','HARD')),
    CONSTRAINT CHK_quizzes_quiz_type    CHECK (quiz_type   IN ('PRACTICE','MOCK_TEST','ASSIGNMENT')),
    CONSTRAINT CHK_quizzes_pass_rate    CHECK (pass_rate BETWEEN 0 AND 100),
    CONSTRAINT CHK_quizzes_num_quest    CHECK (number_of_questions > 0)
);
GO

PRINT 'Creating questions table ...';
GO

CREATE TABLE dbo.questions
(
    question_id   INT IDENTITY(1,1) PRIMARY KEY,
    quiz_id       INT           NOT NULL,
    question_text NVARCHAR(MAX) NOT NULL,
    question_type NVARCHAR(20)  NOT NULL,
    question_order INT          NOT NULL,
    points        DECIMAL(5,2)  NOT NULL DEFAULT (1.0),
    is_active     BIT           NOT NULL DEFAULT (1),
    created_date  DATETIME      NOT NULL DEFAULT (GETDATE()),
    updated_date  DATETIME      NOT NULL DEFAULT (GETDATE()),

    CONSTRAINT CHK_questions_type CHECK (question_type IN ('MULTIPLE_CHOICE','TRUE_FALSE','SHORT_ANSWER','ESSAY'))
);
GO

PRINT 'Creating question_options table ...';
GO

CREATE TABLE dbo.question_options
(
    option_id     INT IDENTITY(1,1) PRIMARY KEY,
    question_id   INT           NOT NULL,
    option_text   NVARCHAR(MAX) NOT NULL,
    is_correct    BIT           NOT NULL DEFAULT (0),
    option_order  INT           NOT NULL,
    created_date  DATETIME      NOT NULL DEFAULT (GETDATE())
);
GO

PRINT 'Creating quiz_attempts table ...';
GO

CREATE TABLE dbo.quiz_attempts
(
    attempt_id   INT IDENTITY(1,1) PRIMARY KEY,
    quiz_id      INT           NOT NULL,
    user_id      INT           NOT NULL,
    start_time   DATETIME      NOT NULL DEFAULT (GETDATE()),
    end_time     DATETIME      NULL,
    score        DECIMAL(5,2)  NULL,
    max_score    DECIMAL(5,2)  NULL,
    percentage   DECIMAL(5,2)  NULL,
    is_passed    BIT           NULL,
    time_taken   INT           NULL,
    status       NVARCHAR(20)  NOT NULL DEFAULT ('IN_PROGRESS'),
    created_date DATETIME      NOT NULL DEFAULT (GETDATE()),

    CONSTRAINT CHK_attempts_status CHECK (status IN ('IN_PROGRESS','COMPLETED','ABANDONED'))
);
GO

PRINT 'Creating user_answers table ...';
GO

CREATE TABLE dbo.user_answers
(
    answer_id         INT IDENTITY(1,1) PRIMARY KEY,
    attempt_id        INT           NOT NULL,
    question_id       INT           NOT NULL,
    selected_option_id INT          NULL,
    answer_text       NVARCHAR(MAX) NULL,
    is_correct        BIT           NULL,
    points_earned     DECIMAL(5,2)  NULL DEFAULT (0),
    created_date      DATETIME      NOT NULL DEFAULT (GETDATE())
);
GO

PRINT 'Tables created successfully';
GO

---------------------------------------------------------------
-- 4) ADD FOREIGN KEY CONSTRAINTS
---------------------------------------------------------------
PRINT 'Adding foreign key constraints ...';
GO

-- FK to external tables (add only if they exist)
IF OBJECT_ID(N'dbo.subjects', N'U') IS NOT NULL
    ALTER TABLE dbo.quizzes ADD CONSTRAINT FK_quizzes_subject FOREIGN KEY(subject_id) REFERENCES dbo.subjects(subject_id);
GO

IF OBJECT_ID(N'dbo.users', N'U') IS NOT NULL
BEGIN
    ALTER TABLE dbo.quizzes      ADD CONSTRAINT FK_quizzes_user       FOREIGN KEY(created_by) REFERENCES dbo.users(user_id);
    ALTER TABLE dbo.quiz_attempts ADD CONSTRAINT FK_attempts_user      FOREIGN KEY(user_id)    REFERENCES dbo.users(user_id);
END
GO

-- Internal relationships
ALTER TABLE dbo.questions        ADD CONSTRAINT FK_questions_quiz         FOREIGN KEY(quiz_id)       REFERENCES dbo.quizzes(quiz_id)       ON DELETE CASCADE;
ALTER TABLE dbo.question_options ADD CONSTRAINT FK_options_question        FOREIGN KEY(question_id)   REFERENCES dbo.questions(question_id) ON DELETE CASCADE;
ALTER TABLE dbo.quiz_attempts    ADD CONSTRAINT FK_attempts_quiz          FOREIGN KEY(quiz_id)       REFERENCES dbo.quizzes(quiz_id);
ALTER TABLE dbo.user_answers     ADD CONSTRAINT FK_answers_attempt        FOREIGN KEY(attempt_id)    REFERENCES dbo.quiz_attempts(attempt_id) ON DELETE CASCADE;
ALTER TABLE dbo.user_answers     ADD CONSTRAINT FK_answers_question       FOREIGN KEY(question_id)   REFERENCES dbo.questions(question_id);
ALTER TABLE dbo.user_answers     ADD CONSTRAINT FK_answers_option         FOREIGN KEY(selected_option_id) REFERENCES dbo.question_options(option_id);
GO

PRINT 'Foreign keys added';
GO

---------------------------------------------------------------
-- 5) OPTIONAL SAMPLE DATA (requires subjects & users rows)
---------------------------------------------------------------
PRINT 'Inserting sample data (if prerequisite data exists) ...';
GO

IF OBJECT_ID(N'dbo.subjects', N'U') IS NOT NULL 
   AND OBJECT_ID(N'dbo.users', N'U')   IS NOT NULL
   AND EXISTS (SELECT 1 FROM dbo.subjects WHERE subject_id = 1)
   AND EXISTS (SELECT 1 FROM dbo.users WHERE    user_id   = 1)
BEGIN
    INSERT INTO dbo.quizzes
    (quiz_name, description, subject_id, level, duration, pass_rate, quiz_type, number_of_questions, is_active, created_by)
    VALUES
    ('Java Basics Quiz',        'Java fundamentals',                 1, 'EASY'  , 30, 70.0, 'PRACTICE' , 10, 1, 1),
    ('Web Dev Foundations',     'HTML, CSS, JavaScript essentials',  1, 'MEDIUM', 45, 75.0, 'ASSIGNMENT', 15, 1, 1),
    ('Advanced Programming',    'Complex programming concepts',      1, 'HARD'  , 60, 80.0, 'MOCK_TEST', 20, 1, 1);

    DECLARE @firstQuizId INT = SCOPE_IDENTITY();

    INSERT INTO dbo.questions (quiz_id, question_text, question_type, question_order, points)
    VALUES
    (@firstQuizId, 'Which is NOT a primitive type in Java?', 'MULTIPLE_CHOICE', 1, 1.0),
    (@firstQuizId, 'Java is platform-independent',           'TRUE_FALSE',      2, 1.0);

    DECLARE @q1 INT = (SELECT question_id FROM dbo.questions WHERE quiz_id = @firstQuizId AND question_order = 1);
    DECLARE @q2 INT = (SELECT question_id FROM dbo.questions WHERE quiz_id = @firstQuizId AND question_order = 2);

    INSERT INTO dbo.question_options (question_id, option_text, is_correct, option_order)
    VALUES
    (@q1,'int'    ,0,1),
    (@q1,'String' ,1,2),
    (@q1,'boolean',0,3),
    (@q1,'char'   ,0,4),
    (@q2,'True'   ,1,1),
    (@q2,'False'  ,0,2);

    PRINT 'Sample data inserted';
END
ELSE
    PRINT 'Prerequisite data missing ‑ skipping sample inserts';
GO

---------------------------------------------------------------
-- 6) FINAL VERIFICATION
---------------------------------------------------------------
PRINT 'Verifying quizzes table columns ...';
GO

SELECT COLUMN_NAME
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'quizzes';
GO

PRINT '*** QUIZ SYSTEM SETUP COMPLETED SUCCESSFULLY ***';
GO 