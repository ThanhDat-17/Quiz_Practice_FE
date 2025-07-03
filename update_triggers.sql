
-- =================================================================
--  DATABASE TRIGGERS FOR AUTOMATIC QUESTION COUNT
-- =================================================================
--  This script creates triggers to automatically maintain the
--  `number_of_questions` column in the `quizzes` table.
--  This ensures data consistency and removes the need for
--  manual updates from the application layer.
-- =================================================================

USE EduBlog;
GO

-- Drop existing triggers if they exist to prevent errors on re-run
DROP TRIGGER IF EXISTS dbo.trg_questions_insert_update;
GO

DROP TRIGGER IF EXISTS dbo.trg_questions_delete;
GO

PRINT 'Creating trigger for INSERT/UPDATE on questions table...';
GO

-- =================================================================
--  Trigger: trg_questions_insert_update
--  Fires on: INSERT, UPDATE on the `questions` table.
--  Purpose:
--  - When a new question is INSERTED, it increments the count
--    for the corresponding quiz_id.
--  - When a question's quiz_id is UPDATED (moved to another quiz),
--    it decrements the count from the old quiz and increments
--    the count for the new quiz.
-- =================================================================
CREATE TRIGGER trg_questions_insert_update
ON dbo.questions
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Variables to hold quiz IDs from both inserted and deleted pseudo-tables
    DECLARE @quizId_inserted INT;
    DECLARE @quizId_deleted INT;

    -- Check if it's an insert or update action
    SELECT @quizId_inserted = quiz_id FROM inserted;
    
    IF EXISTS (SELECT 1 FROM deleted)
    BEGIN
        SELECT @quizId_deleted = quiz_id FROM deleted;
    END

    -- On INSERT: Increment the question count for the new quiz
    IF @quizId_deleted IS NULL
    BEGIN
        UPDATE dbo.quizzes
        SET number_of_questions = ISNULL(number_of_questions, 0) + 1
        WHERE quiz_id = @quizId_inserted;
    END
    -- On UPDATE: If quiz_id has changed, adjust counts for both old and new quizzes
    ELSE IF @quizId_inserted <> @quizId_deleted
    BEGIN
        -- Decrement from the old quiz
        UPDATE dbo.quizzes
        SET number_of_questions = ISNULL(number_of_questions, 0) - 1
        WHERE quiz_id = @quizId_deleted;

        -- Increment for the new quiz
        UPDATE dbo.quizzes
        SET number_of_questions = ISNULL(number_of_questions, 0) + 1
        WHERE quiz_id = @quizId_inserted;
    END
END;
GO

PRINT 'Trigger for INSERT/UPDATE created successfully.';
GO

PRINT 'Creating trigger for DELETE on questions table...';
GO

-- =================================================================
--  Trigger: trg_questions_delete
--  Fires on: DELETE on the `questions` table.
--  Purpose:
--  - When a question is DELETED, it decrements the count
--    for the corresponding quiz_id.
-- =================================================================
CREATE TRIGGER trg_questions_delete
ON dbo.questions
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @quizId INT;
    SELECT @quizId = quiz_id FROM deleted;

    UPDATE dbo.quizzes
    SET number_of_questions = ISNULL(number_of_questions, 0) - 1
    WHERE quiz_id = @quizId;
END;
GO

PRINT 'Trigger for DELETE created successfully.';
GO 