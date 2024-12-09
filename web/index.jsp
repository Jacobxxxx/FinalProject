<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ page import="java.util.*" %> <%@ page
import="Service.UserService" %> <%@ page import="Service.BookService" %> <%@
page import="Service.UserRatingService" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>图书推荐系统后台管理员</title>
    <link rel="stylesheet" href="index.css" />
  </head>

  <body>
    <% // 动态统计数据
    UserService userService = new UserService();
    BookService bookService = new BookService();
    UserRatingService userRatingService = new UserRatingService();
    long userCount = userService.getUserCount();
    long bookCount = bookService.getBookCount();
    long scoreCount = userRatingService.getUserRatingCount();
    %>

    <div class="header">
      <h1>图书推荐系统后台管理员</h1>
      <div class="admin-info">
        <span>管理员: admin</span>
        <a href="../login.jsp">退出</a>
        <a href="../viewer.jsp">前台网站首页</a>
      </div>
    </div>

    <div class="sidebar">
      <ul>
        <li><a href="./index.jsp">数据统计</a></li>

        <li id="user-list">
          <a href="#">用户管理</a>
          <ul id="user-submenu">
            <li><a href="./user-list.jsp">用户列表</a></li>
          </ul>
        </li>

        <li id="book-management">
          <a href="#">图书管理</a>
          <ul id="book-submenu">
            <li>
              <a href="${pageContext.request.contextPath}/book-list"
                >图书列表</a
              >
            </li>
          </ul>
        </li>

        <li id="score-management">
          <a href="#">评分管理</a>
          <ul id="score-submenu">
            <li>
              <a href="${pageContext.request.contextPath}/score-list"
                >评分列表</a
              >
            </li>
          </ul>
        </li>
      </ul>
    </div>

    <div class="main-content">
      <h2 class="data-statistics">数据统计</h2>
      <div class="stats">
        <div class="stat-box">
          <span><a href="./user-list.jsp">用户数量: <%= userCount %></a></span>
        </div>
        <div class="stat-box">
          <span><a href="./book-type.jsp">图书类型数量: <%= 14 %></a></span>
        </div>
        <div class="stat-box">
          <span><a href="./book-list.jsp">图书数量: <%= bookCount %></a></span>
        </div>
        <div class="stat-box">
          <span
            ><a href="./score-list.jsp">评分数量: <%= scoreCount %></a></span
          >
        </div>
      </div>
    </div>
  </body>
  <script src="./index.js"></script>
</html>
