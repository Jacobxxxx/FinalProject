<%@ page import="java.util.*" %>
<%@ page import="Service.BookService" %>
<%@ page import="model.Book" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>用户登录</title>
  <link rel="stylesheet" href="sign.css">
</head>

<body>
<div class="login-container">
  <div class="header">用户登录</div>
  <form class="login-form" action="login" method="post">
    <label for="username">用户名：
      <input type="text" id="username" name="username" placeholder="请输入用户名" required>
    </label>
    <label for="password">&nbsp;&nbsp;&nbsp;&nbsp;密码：
      <input type="password" id="password" name="password" placeholder="请输入密码" required>
    </label>
    <button type="submit" class="login-button">登录</button>
  </form>
  <div class="register-link">
    还没有注册账号？<a href="register.jsp">立即注册</a>
  </div>
</div>
</body>

</html>
