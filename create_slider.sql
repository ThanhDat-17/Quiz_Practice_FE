CREATE TABLE sliders (
    slider_id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    image NVARCHAR(255),
    backlink NVARCHAR(255),
    status NVARCHAR(20) NOT NULL, -- 'show' or 'hide'
    notes NVARCHAR(255),
    updated_at DATETIME DEFAULT GETDATE()
);