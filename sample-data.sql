-- Sample data for testing the blog system
-- Make sure you have created database "EduBlog" and run the table creation scripts first

-- Insert roles
INSERT INTO roles (role_name) VALUES 
('Admin'),
('Teacher'),
('Student');

-- Insert categories
INSERT INTO categories (category_name) VALUES 
('Programming'),
('Web Development'),
('Mobile Development'),
('Data Science'),
('AI & Machine Learning');

-- Insert users
INSERT INTO users (created_date, description, email, full_name, is_active, password, profile_image, updated_date, role_id, date_of_birth, gender, phone) VALUES 
('2025-01-01', 'System Administrator', 'admin@blog.com', 'Admin User', 1, 'admin123', 'assets/images/profile/admin.jpg', '2025-01-01', 1, '1990-01-01', 0, '0123456789'),
('2025-01-02', 'Senior Teacher', 'teacher1@blog.com', 'John Smith', 1, 'teacher123', 'assets/images/profile/teacher1.jpg', '2025-01-02', 2, '1985-05-15', 0, '0123456788'),
('2025-01-03', 'Content Writer', 'teacher2@blog.com', 'Sarah Johnson', 1, 'teacher123', 'assets/images/profile/teacher2.jpg', '2025-01-03', 2, '1988-03-20', 1, '0123456787'),
('2025-01-04', 'Guest Writer', 'writer@blog.com', 'Michael Brown', 1, 'writer123', 'assets/images/profile/writer.jpg', '2025-01-04', 2, '1992-07-10', 0, '0123456786');

-- Insert subjects
INSERT INTO subjects (is_active, subject_image, subject_name, category_id) VALUES 
(1, 'assets/images/subjects/java.jpg', 'Java Programming', 1),
(1, 'assets/images/subjects/html.jpg', 'HTML & CSS', 2),
(1, 'assets/images/subjects/javascript.jpg', 'JavaScript', 2),
(1, 'assets/images/subjects/python.jpg', 'Python', 1),
(1, 'assets/images/subjects/react.jpg', 'React Development', 2);

-- Insert sample blogs
INSERT INTO blogs (content, created_date, image, is_active, title, created_by) VALUES 
('<h2>Introduction to Java Programming</h2><p>Java is one of the most popular programming languages in the world. It''s object-oriented, platform-independent, and widely used in enterprise applications.</p><p>In this comprehensive guide, we''ll explore the fundamentals of Java programming, including:</p><ul><li>Basic syntax and data types</li><li>Object-oriented programming concepts</li><li>Exception handling</li><li>Collections framework</li><li>Multithreading</li></ul><p>Whether you''re a beginner or looking to refresh your knowledge, this tutorial will provide you with a solid foundation in Java programming.</p>', 
'2025-05-20', 'assets/images/blog/latest-blog/pic1.jpg', 1, 'Getting Started with Java Programming: A Complete Guide', 2),

('<h2>Modern Web Development with HTML5 and CSS3</h2><p>Web development has evolved significantly over the years. HTML5 and CSS3 have introduced powerful new features that make creating modern, responsive websites easier than ever.</p><h3>HTML5 New Features</h3><ul><li>Semantic elements (header, nav, main, footer)</li><li>Canvas for graphics</li><li>Video and audio elements</li><li>Local storage</li><li>Geolocation API</li></ul><h3>CSS3 Advantages</h3><ul><li>Flexible box layout (Flexbox)</li><li>Grid layout system</li><li>Animations and transitions</li><li>Media queries for responsive design</li><li>Custom properties (CSS variables)</li></ul><p>Let''s dive into these exciting technologies and see how they can transform your web development workflow.</p>', 
'2025-05-19', 'assets/images/blog/latest-blog/pic2.jpg', 1, 'Modern Web Development: HTML5 & CSS3 Best Practices', 1),

('<h2>JavaScript: The Language of the Web</h2><p>JavaScript has come a long way from being just a simple scripting language for web pages. Today, it powers everything from web applications to mobile apps, desktop software, and even server-side applications.</p><h3>Why Learn JavaScript?</h3><p>JavaScript is essential for web development because:</p><ul><li>It''s the only programming language that runs natively in web browsers</li><li>It''s used for both frontend and backend development (Node.js)</li><li>It has a huge ecosystem with countless libraries and frameworks</li><li>It''s constantly evolving with new features and improvements</li></ul><h3>Getting Started</h3><p>To begin your JavaScript journey, you''ll need to understand:</p><ol><li>Variables and data types</li><li>Functions and scope</li><li>DOM manipulation</li><li>Asynchronous programming</li><li>Modern ES6+ features</li></ol><p>Join us as we explore these concepts and build real-world projects together!</p>', 
'2025-05-18', 'assets/images/blog/latest-blog/pic3.jpg', 1, 'Mastering JavaScript: From Basics to Advanced Concepts', 3),

('<h2>Python for Data Science and AI</h2><p>Python has become the go-to language for data science and artificial intelligence. Its simplicity, powerful libraries, and active community make it perfect for data analysis, machine learning, and AI development.</p><h3>Key Python Libraries for Data Science</h3><ul><li><strong>NumPy</strong> - Numerical computing</li><li><strong>Pandas</strong> - Data manipulation and analysis</li><li><strong>Matplotlib/Seaborn</strong> - Data visualization</li><li><strong>Scikit-learn</strong> - Machine learning</li><li><strong>TensorFlow/PyTorch</strong> - Deep learning</li></ul><h3>Getting Started with Data Science</h3><p>If you''re new to data science, here''s a roadmap:</p><ol><li>Learn Python basics</li><li>Master NumPy and Pandas</li><li>Practice data visualization</li><li>Study statistics and probability</li><li>Explore machine learning algorithms</li><li>Work on real projects</li></ol><p>The field of data science is vast and exciting. Let''s embark on this journey together!</p>', 
'2025-05-17', 'assets/images/blog/default/thum1.jpg', 1, 'Python for Data Science: A Beginner''s Roadmap', 4),

('<h2>Building Modern React Applications</h2><p>React has revolutionized frontend development with its component-based architecture and virtual DOM. It''s now one of the most popular JavaScript libraries for building user interfaces.</p><h3>Why Choose React?</h3><ul><li>Component-based architecture promotes reusability</li><li>Virtual DOM ensures optimal performance</li><li>Large ecosystem with extensive tooling</li><li>Strong community support</li><li>Backed by Facebook/Meta</li></ul><h3>React Ecosystem</h3><p>Modern React development involves several tools and libraries:</p><ul><li><strong>Create React App</strong> - Quick project setup</li><li><strong>React Router</strong> - Client-side routing</li><li><strong>Redux/Context API</strong> - State management</li><li><strong>Material-UI/Ant Design</strong> - UI component libraries</li><li><strong>React Testing Library</strong> - Testing utilities</li></ul><h3>Best Practices</h3><p>To write maintainable React code:</p><ol><li>Keep components small and focused</li><li>Use functional components with hooks</li><li>Implement proper error boundaries</li><li>Optimize performance with React.memo</li><li>Write comprehensive tests</li></ol><p>Ready to build amazing React applications? Let''s get started!</p>', 
'2025-05-16', 'assets/images/blog/latest-blog/pic1.jpg', 1, 'React Development: Building Modern User Interfaces', 2),

('<h2>The Future of Web Development</h2><p>Web development is constantly evolving. New technologies, frameworks, and best practices emerge regularly, shaping how we build and deploy web applications.</p><h3>Emerging Trends</h3><ul><li><strong>Progressive Web Apps (PWAs)</strong> - Native app-like experiences</li><li><strong>Jamstack Architecture</strong> - Better performance and security</li><li><strong>Serverless Computing</strong> - Reduced infrastructure management</li><li><strong>WebAssembly</strong> - Near-native performance in browsers</li><li><strong>AI Integration</strong> - Smarter user experiences</li></ul><h3>Tools and Technologies to Watch</h3><p>Keep an eye on these emerging technologies:</p><ul><li>Next.js and Gatsby for React</li><li>Vue 3 and Nuxt.js</li><li>Svelte and SvelteKit</li><li>Deno as Node.js alternative</li><li>GraphQL for API development</li></ul><p>The web development landscape is exciting and full of opportunities. Stay curious and keep learning!</p>', 
'2025-05-15', 'assets/images/blog/latest-blog/pic2.jpg', 1, 'The Future of Web Development: Trends and Technologies', 3),

('<h2>Database Design Best Practices</h2><p>A well-designed database is the foundation of any successful application. Poor database design can lead to performance issues, data inconsistency, and maintenance nightmares.</p><h3>Fundamental Principles</h3><ol><li><strong>Normalization</strong> - Eliminate data redundancy</li><li><strong>Indexing</strong> - Optimize query performance</li><li><strong>Constraints</strong> - Ensure data integrity</li><li><strong>Relationships</strong> - Model real-world connections</li><li><strong>Security</strong> - Protect sensitive data</li></ol><h3>Common Mistakes to Avoid</h3><ul><li>Over-normalization leading to complex queries</li><li>Missing indexes on frequently queried columns</li><li>Poor naming conventions</li><li>Lack of proper documentation</li><li>Ignoring database security</li></ul><h3>Modern Database Technologies</h3><p>Consider these options for your next project:</p><ul><li><strong>Relational</strong> - PostgreSQL, MySQL, SQL Server</li><li><strong>NoSQL</strong> - MongoDB, CouchDB, Cassandra</li><li><strong>Graph</strong> - Neo4j, Amazon Neptune</li><li><strong>Time-series</strong> - InfluxDB, TimescaleDB</li></ul><p>Choose the right database for your specific use case and requirements.</p>', 
'2025-05-14', 'assets/images/blog/latest-blog/pic3.jpg', 1, 'Database Design: Best Practices and Common Pitfalls', 1),

('<h2>Mobile App Development in 2025</h2><p>Mobile app development continues to evolve with new frameworks, tools, and platforms. Whether you''re building for iOS, Android, or both, there are more options than ever.</p><h3>Development Approaches</h3><ul><li><strong>Native Development</strong> - Platform-specific apps (Swift/Kotlin)</li><li><strong>Cross-platform</strong> - React Native, Flutter, Xamarin</li><li><strong>Hybrid</strong> - Ionic, Cordova/PhoneGap</li><li><strong>Progressive Web Apps</strong> - Web technologies for mobile</li></ul><h3>Flutter vs React Native</h3><p>Two popular cross-platform solutions:</p><table><tr><th>Flutter</th><th>React Native</th></tr><tr><td>Dart language</td><td>JavaScript/TypeScript</td></tr><tr><td>Custom UI widgets</td><td>Native components</td></tr><tr><td>Google backing</td><td>Facebook backing</td></tr><tr><td>Consistent UI across platforms</td><td>Platform-specific look</td></tr></table><h3>Key Considerations</h3><p>When choosing your mobile development approach:</p><ol><li>Target audience and platforms</li><li>Development timeline and budget</li><li>Team expertise and preferences</li><li>Performance requirements</li><li>Maintenance and updates</li></ol><p>The mobile development landscape offers exciting opportunities. Choose the right approach for your project!</p>', 
'2025-05-13', 'assets/images/blog/default/thum1.jpg', 1, 'Mobile App Development: Native vs Cross-Platform in 2025', 4);

-- Insert lessons (optional for blog context)
INSERT INTO lessons (created_date, lesson_name, updated_date, subject_id) VALUES 
('2025-05-01', 'Java Basics', '2025-05-01', 1),
('2025-05-02', 'HTML Fundamentals', '2025-05-02', 2),
('2025-05-03', 'JavaScript Essentials', '2025-05-03', 3),
('2025-05-04', 'Python Introduction', '2025-05-04', 4),
('2025-05-05', 'React Components', '2025-05-05', 5); 