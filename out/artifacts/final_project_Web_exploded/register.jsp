<%@ page import="java.util.*" %>
<%@ page import="Service.BookService" %>
<%@ page import="model.Book" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>用户注册</title>
  <link rel="stylesheet" href="sign.css" />
  <style>
    /* 基本的提示框样式 */
    .alert {
      position: fixed;
      top: 20px;
      left: 50%;
      transform: translateX(-50%);
      padding: 15px;
      color: #fff;
      border-radius: 8px;
      font-size: 16px;
      z-index: 9999;
      max-width: 80%;
      text-align: center;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
      transition: opacity 0.5s ease-out;
    }

    /* 成功的提示框样式 */
    .alert-success {
      background-color: #28a745;
    }

    /* 错误的提示框样式 */
    .alert-danger {
      background-color: #dc3545;
    }

    /* 自动消失效果 */
    .alert.auto-remove {
      animation: fadeOut 0.5s ease-out forwards;
    }

    @keyframes fadeOut {
      0% { opacity: 1; }
      100% { opacity: 0; }
    }

  </style>
</head>

<body>
<div class="login-container">
  <div class="header">用户注册</div>
  <form class="login-form" action="register" method="post">
    <label for="username"
    >用户名：
      <input
              type="text"
              id="username"
              name="username"
              placeholder="请输入用户名"
              required
      />
    </label>
    <label for="password"
    >&nbsp;&nbsp;&nbsp;&nbsp;密码：
      <input
              type="password"
              id="password"
              name="password"
              placeholder="请输入密码"
              required
      />
    </label>
    <label for="confirmPassword">
      &nbsp;&nbsp;&nbsp;&nbsp;确认密码：
      <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              placeholder="请确认密码"
              required
      />
    </label>
    <label for="email"
    >&nbsp;&nbsp;&nbsp;&nbsp;邮箱：
      <input
              type="email"
              id="email"
              name="email"
              placeholder="请输入邮箱"
              required
      />
    </label>
    <button type="submit" class="login-button">注册</button>
  </form>

  <!-- 显示成功或失败的提示框 -->
  <c:if test="${not empty successMessage}">
    <div class="alert alert-success">
        ${successMessage}
    </div>
  </c:if>

  <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">
        ${errorMessage}
    </div>
  </c:if>

  <div class="login-link">
    已有账号？<a href="login.jsp">立即登录</a>
  </div>
</div>

<script>
  // 自动消失的提示框
  setTimeout(function() {
    let alertBox = document.querySelector('.alert');
    if (alertBox) {
      alertBox.classList.add("auto-remove");
    }
  }, 3000);
</script>
</body>
</html>
