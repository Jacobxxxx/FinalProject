<%@ page import="model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- profile.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>个人信息</title>
    <link rel="stylesheet" href="user.css">
    <style>
        /* 提示框样式 */
        .alert {
            padding: 10px 20px;
            color: #fff;
            border-radius: 5px;
            font-size: 16px;
            z-index: 9999;
            text-align: center;
            max-width: 300px; /* 限制最大宽度，避免提示框过宽 */
            word-wrap: break-word;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            opacity: 1;
            transition: opacity 0.3s ease, transform 0.3s ease;
        }

        .alert-success {
            background: linear-gradient(145deg, #28a745, #218838); /* 渐变绿色 */
        }

        .alert-danger {
            background: linear-gradient(145deg, #dc3545, #c82333); /* 渐变红色 */
        }

    </style>
    <script>
        // 显示成功或失败的提示框
        function showMessage(message, isSuccess) {
            let alertBox = document.createElement("div");
            alertBox.classList.add("alert");
            alertBox.classList.add(isSuccess ? "alert-success" : "alert-danger");
            alertBox.innerText = message;

            // 获取提交按钮的位置
            let submitButton = document.getElementById("submit-btn");
            let buttonRect = submitButton.getBoundingClientRect();  // 获取按钮的位置信息

            // 设置提示框的位置
            alertBox.style.position = "absolute";
            alertBox.style.top = (buttonRect.top + buttonRect.height + 10) + "px";  // 提示框位于按钮下方
            alertBox.style.left = buttonRect.left + "px";  // 提示框与按钮左对齐

            document.body.appendChild(alertBox);

            // 2秒后自动消失
            setTimeout(function() {
                alertBox.remove();
            }, 2000);
        }
    </script>

</head>

<body>
<div id="center">
    <header>
        <h1>图书推荐系统</h1>
        <div class="search-bar">
            <form action="search" method="post">
                <input type="text" name="keyword" placeholder="请输入图书名称..." value="${keyword}">
                <button type="submit">搜索</button>
            </form>
        </div>
        <div class="user-actions">
            <a href="profile">个人信息</a>
            <a href="favorites">收藏列表</a>
        </div>
    </header>

    <nav>
        <ul>
            <li><a href="main.jsp">首页</a></li>
            <li><a href="category?category=编程">编程</a></li>
            <li><a href="category?category=科学">科学</a></li>
            <li><a href="category?category=商业">商业</a></li>
            <li><a href="category?category=心理">心理</a></li>
            <li><a href="category?category=生活">生活</a></li>
            <li><a href="category?category=哲学">哲学</a></li>
            <li><a href="category?category=悬疑">悬疑</a></li>
            <li><a href="category?category=经典">经典</a></li>
            <li><a href="category?category=文学">文学</a></li>
            <li><a href="category?category=互联网">互联网</a></li>
            <li><a href="category?category=科普">科普</a></li>
            <li><a href="category?category=管理">管理</a></li>
            <li><a href="category?category=心理">心理</a></li>
        </ul>
    </nav>

    <section>
        <h2 class="personal-recommendations">修改个人信息</h2>
    </section>

    <!-- 用户信息修改表单 -->
    <div class="form-container">
        <form action="updateProfile" method="POST">
            <label for="username">用户名:&nbsp;
                <input type="text" id="username" name="username" value="${user.username}" required>
            </label>
            <br>
            <label for="email">邮箱:&nbsp;&nbsp;
                <input type="email" id="email" name="email" value="${user.email}" required>
            </label>
            <br>
            <label for="password">密码:&nbsp;&nbsp;
                <input type="text" id="password" name="password" value="${user.password}" required>
            </label>
            <br>
            <input type="submit" id="submit-btn" value="提交修改">
        </form>
    </div>

    <!-- 处理消息显示 -->
    <script>
        <c:if test="${not empty message}">
        showMessage('${message}', true);  <!-- 成功 -->
        </c:if>
        <c:if test="${not empty errorMessage}">
        showMessage('${errorMessage}', false);  <!-- 错误 -->
        </c:if>
    </script>

</div>
</body>

</html>