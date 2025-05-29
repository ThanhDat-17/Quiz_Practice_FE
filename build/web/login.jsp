<%-- 
    Document   : login
    Created on : 26 thg 5, 2025, 00:54:11
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="EduChamp : Education HTML Template" />

        <title>Login | EduChamp</title>

        <!-- Favicon -->
        <link rel="icon" href="assets/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" href="assets/images/favicon.png" />

        <!-- Stylesheets -->
        <link rel="stylesheet" type="text/css" href="assets/css/assets.css">
        <link rel="stylesheet" type="text/css" href="assets/css/typography.css">
        <link rel="stylesheet" type="text/css" href="assets/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link class="skin" rel="stylesheet" type="text/css" href="assets/css/color/color-1.css">
    </head>

    <body id="bg">
        <div class="page-wraper">
            <div id="loading-icon-bx"></div>

            <div class="account-form">
                <div class="account-head" style="background-image:url(assets/images/background/bg2.jpg);">
                    <a href="index.html"><img src="assets/images/logo-white-2.png" alt="Logo"></a>
                </div>
                <div class="account-form-inner">
                    <div class="account-container">
                        <div class="heading-bx left">
                            <h2 class="title-head">Login</h2>
                            <p>You don’t have an account,  <a href="register.html">CREATE NOW</a></p>
                        </div>	

                        <!-- ✅ form login gửi về loginAccount bằng method post-->
                        <form class="contact-bx" action="loginAccount" method="post">
                            <div class="row placeani">
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Email</label>
                                            <input name="username" type="text" required class="form-control">
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Password</label>
                                            <input name="password" type="password" required class="form-control">
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-12">
                                    <div class="form-group form-forget">
                                        <a href="confirm email.jsp" class="ml-auto"><i><u>Forgot Password?</u></i></a>
                                    </div>
                                </div>


                                <div class="col-lg-12 m-b30">
                                    <button name="submit" type="submit" value="Submit" class="btn button-md">Login</button>
                                </div>
                            </div>

                            <!-- Hiển thị lỗi nếu có -->
                            <div>
                                <c:if test="${not empty message}">
                                    <span style="color: green;">${message}</span>
                                </c:if>
                                <c:if test="${not empty error}">
                                    <span style="color: red;">${error}</span>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Scripts -->
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/vendors/bootstrap/js/popper.min.js"></script>
        <script src="assets/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="assets/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
        <script src="assets/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="assets/vendors/counter/waypoints-min.js"></script>
        <script src="assets/vendors/counter/counterup.min.js"></script>
        <script src="assets/vendors/imagesloaded/imagesloaded.js"></script>
        <script src="assets/vendors/masonry/masonry.js"></script>
        <script src="assets/vendors/masonry/filter.js"></script>
        <script src="assets/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="assets/js/functions.js"></script>
        <script src="assets/js/contact.js"></script>
        <script src='assets/vendors/switcher/switcher.js'></script>
    </body>
</html>

