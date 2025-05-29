# Blog Management System

A comprehensive blog management system built with Jakarta Servlet, JSP, and SQL Server.

## 📋 Features

- **Blog Management**: Create, read, update, and delete blog posts
- **Rich Text Editor**: WYSIWYG editor using Summernote
- **Image Upload**: Support for featured images
- **Responsive Design**: Modern Bootstrap 5 UI
- **Admin Dashboard**: Complete administrative interface
- **Search Functionality**: Search blogs by title and content
- **Pagination**: Efficient data pagination
- **Status Management**: Active/Inactive blog status

## 🛠️ Technology Stack

- **Backend**: Jakarta Servlet (Java 17)
- **Frontend**: JSP, Bootstrap 5, jQuery
- **Database**: SQL Server
- **Server**: Apache Tomcat 10
- **Rich Editor**: Summernote
- **Icons**: Font Awesome 6

## 📁 Project Structure

```
blog/
├── src/
│   └── java/
│       ├── controller/
│       │   └── BlogController.java
│       ├── dao/
│       │   └── BlogDAO.java
│       ├── model/
│       │   └── Blog.java
│       └── utils/
│           └── DBContext.java
├── web/
│   ├── admin/
│   │   ├── blog-list.jsp
│   │   └── blog-form.jsp
│   ├── blog-list.jsp
│   ├── blog-detail.jsp
│   └── assets/
└── lib/
    ├── jakarta.servlet.jsp.jstl-api-3.0.0.jar
    ├── jakarta.servlet.jsp.jstl-3.0.1.jar
    └── mssql-jdbc-12.8.1.jre8.jar
```

## 🚀 Setup Instructions

### Prerequisites

- Java 17 or higher
- Apache Tomcat 10
- SQL Server
- NetBeans IDE (recommended)

### Database Setup

1. Create a database named `EduBlog` in SQL Server
2. Run the database schema script to create tables
3. (Optional) Run the sample data script for test data

```sql
-- Create database
CREATE DATABASE EduBlog;

-- Run the provided database schema
-- Then run sample-data.sql for test data
```

### Database Configuration

Update the database connection settings in `src/java/utils/DBContext.java`:

```java
private static final String serverName = "localhost";
private static final String dbName = "EduBlog";
private static final String portNumber = "1433";
private static final String userID = "sa";
private static final String password = "123";
```

### Application Deployment

1. Clone the repository
2. Open the project in NetBeans
3. Add required JAR files to the lib folder:
   - jakarta.servlet.jsp.jstl-api-3.0.0.jar
   - jakarta.servlet.jsp.jstl-3.0.1.jar
   - mssql-jdbc-12.8.1.jre8.jar
4. Build and deploy to Tomcat 10

## 📝 Usage

### Admin Interface

Access the admin interface at: `http://localhost:8080/blog/admin/blog`

**Features:**
- View all blogs in a data table
- Add new blogs with rich text editor
- Edit existing blogs
- Delete blogs with confirmation
- Toggle blog status (Active/Inactive)
- Search and filter blogs
- Pagination support

### User Interface

Access the public blog interface at:
- Blog list: `http://localhost:8080/blog/blog-list.jsp`
- Blog details: `http://localhost:8080/blog/blog-detail.jsp`

## 🎨 Admin Dashboard Features

### Blog List View
- **Statistics Cards**: Total blogs, active blogs, inactive blogs, total pages
- **Data Table**: Sortable and searchable blog listing
- **Status Badges**: Visual status indicators
- **Action Buttons**: Edit and delete functionality
- **Pagination**: Navigate through large datasets

### Blog Form
- **Rich Text Editor**: Full WYSIWYG editing capabilities
- **Image Upload**: Drag & drop or URL input for featured images
- **Status Toggle**: Switch to activate/deactivate blogs
- **Form Validation**: Client and server-side validation
- **Auto-save**: Prevent data loss during editing

## 🗄️ Database Schema

### Key Tables

**blogs**
- `blog_id` (Primary Key)
- `title` (Blog title)
- `content` (HTML content)
- `image` (Featured image path)
- `is_active` (Status flag)
- `created_date` (Creation date)
- `created_by` (Author ID)

**users**
- `user_id` (Primary Key)
- `full_name` (Author name)
- `email` (Email address)
- `role_id` (User role)

## 🔧 API Endpoints

### Blog Controller (`/admin/blog`)

- `GET ?action=list` - List all blogs with pagination
- `GET ?action=add` - Show add blog form
- `GET ?action=edit&id={id}` - Show edit blog form
- `GET ?action=delete&id={id}` - Delete blog
- `POST action=save` - Create new blog
- `POST action=update` - Update existing blog

## 🎯 Key Features Details

### Rich Text Editor
- Powered by Summernote
- Full formatting capabilities
- Image and link insertion
- Code view for HTML editing
- Responsive design

### Image Management
- File upload support
- URL input option
- Image preview
- Automatic path handling

### Status Management
- Active/Inactive toggles
- Visual status indicators
- Bulk status updates

### Search & Filter
- Real-time search
- Content and title filtering
- DataTables integration

## 🔒 Security Considerations

- SQL injection prevention with PreparedStatements
- Input validation and sanitization
- XSS protection through proper encoding
- File upload restrictions

## 🚀 Performance Optimizations

- Database connection pooling
- Efficient pagination queries
- Lazy loading for images
- Minified CSS/JS resources

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 📞 Support

For support and questions:
- Create an issue on GitHub
- Check the documentation
- Review the sample data

## 🔄 Future Enhancements

- User authentication and authorization
- Comment system for blogs
- Categories and tags
- SEO optimization
- API endpoints for mobile apps
- Email notifications
- Social media integration

---

**Happy Blogging! 🎉** 