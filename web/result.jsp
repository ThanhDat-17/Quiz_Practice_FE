<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thông báo</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            display: flex;
            height: 100vh;
            align-items: center;
            justify-content: center;
            background-color: #f4f6f8;
            font-family: Arial, sans-serif;
        }
        .notification-box {
            text-align: center;
            background: #fff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        .countdown {
            font-size: 24px;
            font-weight: bold;
            color: #dc3545;
        }
    </style>
</head>
<body>

<div class="notification-box">
    <h3 class="text-success mb-3">🎉${requestScope.notification}</h3>
    <p>Bạn sẽ được chuyển về trang chủ sau <span class="countdown" id="countdown">5</span> giây...</p>
    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-3">Về trang chủ ngay</a>
</div>

<script>
    let seconds = 5;
    const countdownElement = document.getElementById("countdown");

    const timer = setInterval(() => {
        seconds--;
        countdownElement.textContent = seconds;

        if (seconds <= 0) {
            clearInterval(timer);
            window.location.href = "${pageContext.request.contextPath}/home";
        }
    }, 1000);
</script>

</body>
</html>
