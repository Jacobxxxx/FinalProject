<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<%@ page import="model.User" %>
<%@ page import="Service.UserService" %>

<%
  // 初始化服务层
  UserService userService = new UserService();

  // 分页逻辑
  int pageSize = 10; // 每页显示10条
  int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
  long totalUsers = userService.getUserCount(); // 获取总用户数
  int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

  // 获取当前页的用户列表
  List<User> users = userService.getUsersByPage(currentPage, pageSize);

  // 设置 JSP 属性
  request.setAttribute("users", users);
  request.setAttribute("currentPage", currentPage);
  request.setAttribute("totalPages", totalPages);
  request.setAttribute("totalUsers", totalUsers);
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>图书推荐系统后台管理员</title>
  <link rel="stylesheet" href="user-list.css">
  <style>
    .alert {
      position: fixed;
      top: 20px;
      left: 50%;
      transform: translateX(-50%);
      padding: 15px 20px;
      border-radius: 5px;
      color: #fff;
      font-size: 16px;
      box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.2);
      z-index: 1000;
      opacity: 0.9;
      transition: opacity 0.5s ease;
    }

    .alert-success {
      background-color: #28a745;
    }

    .alert.hide {
      opacity: 0;
      visibility: hidden;
      transition: opacity 0.5s ease, visibility 0.5s;
    }

  </style>
</head>
<body>
<div class="header">
  <h1>图书推荐系统后台管理员</h1>
  <div class="admin-info">
    <span>管理员: admin</span>
    <a href="login.jsp">退出</a>
    <a href="viewer.jsp">前台网站首页</a>
  </div>
</div>

<div class="sidebar">
  <ul>
    <li><a href="index.jsp">数据统计</a></li>
    <li id="user-list">
      <a href="#">用户管理</a>
      <ul id="user-submenu">
        <li><a href="user-list.jsp">用户列表</a></li>
      </ul>
    </li>
    <li id="book-type-management">
      <a href="#">图书类型管理</a>
      <ul id="book-type-submenu">
        <li><a href="book-type.jsp">图书类型列表</a></li>
      </ul>
    </li>
    <li id="book-management">
      <a href="#">图书管理</a>
      <ul id="book-submenu">
        <li><a href="book-list.jsp">图书列表</a></li>
      </ul>
    </li>
    <li id="score-management">
      <a href="#">评分管理</a>
      <ul id="score-submenu">
        <li><a href="/score-list">评分列表</a></li>
      </ul>
    </li>
    <li><a href="#">系统设置</a></li>
  </ul>
</div>

<div class="main-content">
  <h2 class="user-list">用户列表</h2>

  <!-- 删除成功提示框 -->
  <c:if test="${param.message == 'success'}">
    <div class="alert alert-success">用户删除成功！</div>
  </c:if>

  <div class="search-bar">
    <form action="user-search" method="POST">
      <input type="text" name="keyword" value="${keyword}" placeholder="请输入用户名称...">
      <button type="submit">搜索</button>
    </form>
  </div>

  <table id="data-table">
    <thead>
    <tr>
      <th><input type="checkbox" id="select-all"></th>
      <th style="background-color: rgb(255, 229, 229);">序号</th>
      <th style="background-color: rgb(255, 229, 229);">用户名</th>
      <th style="background-color: rgb(255, 229, 229);">邮箱</th>
      <th style="background-color: rgb(255, 229, 229);">操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
      <tr>
        <td><input type="checkbox"></td>
        <td>${user.user_id}</td>
        <td>${user.username}</td>
        <td>${user.email}</td>
        <td><a href="delete-user?id=${user.user_id}">删除</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <div class="pagination">
    <p>共${totalUsers}条数据 第${currentPage}/${totalPages}页
      <c:if test="${currentPage > 1}">
        <a href="user-list.jsp?page=1">首页</a>
        <a href="user-list.jsp?page=${currentPage - 1}">上一页</a>
      </c:if>
      <c:if test="${currentPage < totalPages}">
        <a href="user-list.jsp?page=${currentPage + 1}">下一页</a>
        <a href="user-list.jsp?page=${totalPages}">尾页</a>
      </c:if>
    </p>
  </div>
</div>

<script>
  // 自动隐藏提示框
  setTimeout(() => {
    const alertBox = document.querySelector('.alert');
    if (alertBox) {
      alertBox.classList.add('hide');
    }
  }, 3000); // 3秒后隐藏
</script>

</body>
</html>
