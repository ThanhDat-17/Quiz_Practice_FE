<%-- 
    Document   : profile
    Created on : May 27, 2025, 1:19:18 AM
    Author     : The Shuyy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>User Profile</title>
        <link rel="stylesheet" href="style.css" />
    </head>
    <body>
        <header>
            <div class="top-bar">
                <span>📧 ${sessionScope.user.email}</span>
                <span class="greeting">Xin chào ${sessionScope.user.fullName}!</span>
            </div>
            <nav class="navbar">
                <a href="#">HOME</a>
                <a href="#">ABOUT</a>
                <a href="#">COURSES</a>
                <a href="#">QUIZ</a>
                <a href="#">BLOG</a>
            </nav>
        </header>

        <main>
            
            <div class="breadcrumb">Home > Profile</div>
            <div class="profile-container">
                <aside class="sidebar">
                     <!-- META ============================================= -->
                    <div class="avatar-wrapper">
                        <img id="avatar" src="${sessionScope.user.profileImage}" class="avatar" />
                        <form action="upload-avatar" method="post" enctype="multipart/form-data">
                            <input type="file" name="avatar" accept="image/*" required />
                            <button class="change-avatar-btn" type="submit">Đổi ảnh đại diện</button>
                        </form>
                    </div>
                    <h3>${sessionScope.user.fullName}</h3>
                    <p>${sessionScope.user.email}</p>
                    <button class="sidebar-btn">▶ Edit Profile</button>
                    <button class="sidebar-btn">▶ Change Password</button>
                </aside>

                <section class="profile-form">
                    <h3>EDIT PROFILE</h3>
                    <form id="profileForm" action="profile" method="post">
                        <label>Full Name <input type="text" name="name" value="${sessionScope.user.fullName}" /></label>
                        <label>Email <input type="email" value="${sessionScope.user.email}" disabled /></label>
                        <label>Gender
                            <select name="gender">
                                <option value="1" ${sessionScope.user.gender == true? 'selected' : ''}>Male</option>
                                <option value="0" ${sessionScope.user.gender == false? 'selected' : ''}>Female</option>
                            </select>
                        </label>
                        <label>Phone No. <input type="text" value="0134333456" disabled="" /></label>
                        <label>Description <textarea name="description">${sessionScope.user.description}</textarea></label>
                        <div class="buttons">
                            <button type="submit" class="save-btn" >SAVE</button>
                            <button class="cancel-btn" onclick="cancelEdit()">CANCEL</button>
                        </div>
                    </form>
                </section>
            </div>
        </main>

        <script>
            function cancelEdit() {
                if (confirm("Bạn có chắc muốn hủy thay đổi không?")) {
                    document.getElementById("profileForm").reset();
                }
            }

        </script>
         <!-- META ============================================= -->
    </body>
</html>

