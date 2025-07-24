# Quiz Management System

## 🎯 Tổng quan
Hệ thống quản lý quiz dành cho admin với đầy đủ tính năng CRUD, search, filter và pagination.

## 🚀 Tính năng chính

### ✅ Quản lý Quiz
- **Xem danh sách quiz** với pagination
- **Tìm kiếm** theo tên quiz
- **Lọc** theo subject và quiz type
- **Thêm quiz mới** qua modal
- **Chỉnh sửa quiz** với form validation
- **Xóa quiz** với xác nhận
- **Thống kê** tổng quan

### ✅ Thông tin Quiz
- **ID**: Mã định danh duy nhất
- **Name**: Tên quiz
- **Subject**: Môn học (link với bảng subjects)
- **Level**: Basic, Intermediate, Advanced
- **Questions**: Số lượng câu hỏi
- **Duration**: Thời gian làm bài (phút)
- **Pass Rate**: Tỷ lệ đậu (%)
- **Type**: Practice, Exam, Assignment
- **Status**: Active/Inactive

## 📋 Hướng dẫn cài đặt

### 1. Database Setup
```sql
-- Chạy script này để tạo bảng và dữ liệu mẫu
sqlcmd -S your_server -d your_database -i quiz-database-setup.sql
```

### 2. File Structure
```
src/java/
├── model/Quiz.java              # Model Quiz
├── dao/QuizDAO.java            # Data Access Object
└── Controller/QuizController.java  # Servlet Controller

web/admin/
├── quiz-list.jsp               # Trang danh sách quiz
└── quiz-form.jsp              # Trang thêm/sửa quiz
```

### 3. Dependencies
Đảm bảo có các dependency sau trong project:
- Jakarta Servlet API
- SQL Server JDBC Driver
- Bootstrap 5.1.3
- Font Awesome 6.0.0

## 🔧 Cấu hình

### Database Connection
Cập nhật file `DBContext.java` với thông tin database:
```java
// Đảm bảo connection string đúng
String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=YourDB;user=username;password=password";
```

### URL Mapping
Quiz management sử dụng servlet mapping:
- **URL**: `/admin/quiz`
- **Controller**: `QuizController.java`

## 📱 Cách sử dụng

### Truy cập Quiz Management
1. Đăng nhập admin panel
2. Click **Quiz Management** trong sidebar
3. URL: `http://localhost:8080/yourapp/admin/quiz`

### Thêm Quiz mới
1. Click nút **"Create New Quiz"**
2. Điền thông tin trong modal:
   - Quiz Name (bắt buộc)
   - Subject (chọn từ dropdown)
   - Level (Basic/Intermediate/Advanced)
   - Type (Practice/Exam/Assignment)
   - Duration (1-300 phút)
   - Total Questions (1-100)
   - Pass Rate (0-100%)
   - Description (tùy chọn)
   - Status (Active/Inactive)
3. Click **"Create Quiz"**

### Tìm kiếm và Lọc
- **Search**: Nhập tên quiz
- **Subject Filter**: Chọn môn học
- **Type Filter**: Chọn loại quiz
- Click **Search** hoặc **Clear** để reset

### Chỉnh sửa Quiz
1. Click icon **Edit** (màu vàng) trong cột Actions
2. Form sẽ load với dữ liệu hiện tại
3. Chỉnh sửa thông tin cần thiết
4. Click **"Update Quiz"**

### Xóa Quiz
1. Click icon **Delete** (màu đỏ) trong cột Actions
2. Xác nhận trong dialog
3. Quiz sẽ bị xóa khỏi database

## 🎨 UI/UX Features

### Responsive Design
- **Mobile-friendly** với Bootstrap responsive grid
- **Sidebar navigation** thu gọn trên mobile
- **Tables** với horizontal scroll trên màn hình nhỏ

### Visual Enhancements
- **Gradient backgrounds** cho sidebar và buttons
- **Badge styling** cho status, level, type
- **Hover effects** và smooth transitions
- **Icon integration** với Font Awesome
- **Color coding**:
  - 🟢 Green: Active, Basic, Practice
  - 🟡 Yellow: Intermediate, Assignment
  - 🔴 Red: Advanced, Exam
  - ⚪ Gray: Inactive

### Interactive Elements
- **Modal forms** cho thêm quiz
- **Tooltips** cho action buttons
- **Form validation** với JavaScript
- **Loading states** và feedback messages

## 🔒 Security & Validation

### Server-side Validation
- Required field validation
- Numeric range validation
- SQL injection protection
- XSS protection

### Client-side Validation
- Real-time form validation
- Input range checking
- User-friendly error messages

## 📊 Database Schema

```sql
CREATE TABLE quizzes (
    quiz_id INT IDENTITY(1,1) PRIMARY KEY,
    quiz_name NVARCHAR(255) NOT NULL,
    description NVARCHAR(1000),
    subject_id INT,
    level NVARCHAR(50) CHECK (level IN ('Basic', 'Intermediate', 'Advanced')),
    duration INT CHECK (duration > 0),
    pass_rate DECIMAL(5,2) CHECK (pass_rate >= 0 AND pass_rate <= 100),
    type NVARCHAR(50) CHECK (type IN ('Practice', 'Exam', 'Assignment')),
    total_questions INT CHECK (total_questions > 0),
    is_active BIT DEFAULT 1,
    created_date DATETIME2 DEFAULT GETDATE(),
    updated_date DATETIME2 DEFAULT GETDATE(),
    created_by INT NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);
```

## 🚨 Troubleshooting

### Common Issues

1. **"Cannot find symbol" errors**
   - Đảm bảo jakarta servlet dependency có trong classpath
   - Check import statements

2. **Database connection failed**
   - Verify connection string trong DBContext.java
   - Check SQL Server service running
   - Verify database credentials

3. **404 Not Found**
   - Check servlet mapping trong web.xml hoặc annotation
   - Verify URL path: `/admin/quiz`

4. **Modal không hiển thị**
   - Check Bootstrap JS import
   - Verify jQuery dependency (nếu cần)

5. **CSS không load**
   - Check CDN links
   - Verify internet connection

### Performance Tips
- **Database indexes** đã được tạo cho các trường thường dùng
- **Pagination** giới hạn 10 records per page
- **Lazy loading** cho subjects dropdown

## 📞 Support
Nếu gặp vấn đề, hãy check:
1. Server logs trong Tomcat/console
2. Browser developer tools
3. Database connection logs
4. JSP compilation errors

## 🎯 Future Enhancements
- Question management integration
- Quiz attempt tracking
- Result analytics
- Export functionality
- Bulk operations
- Advanced search filters 