<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="java.util.List" %> <%@ page import="model.UserRating" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>图书推荐系统后台管理员</title>
    <link rel="stylesheet" href="score-list.css" />
  </head>
  <body>
    <div class="header">
      <h1>图书推荐系统后台管理员</h1>
      <div class="admin-info">
        <span>管理员: admin</span>
        <a href="#">退出</a>
        <a href="#">前台网站首页</a>
      </div>
    </div>

    <div class="sidebar">
      <ul>
        <li><a href="./index.html">数据统计</a></li>
        <li id="user-list">
          <a href="#">用户管理</a>
          <ul id="user-submenu">
            <li><a href="./user-list.jsp">用户列表</a></li>
          </ul>
        </li>
        <li id="book-type-management">
          <a href="#">图书类型管理</a>
          <ul id="book-type-submenu">
            <li><a href="./book-type.jsp">图书类型列表</a></li>
          </ul>
        </li>
        <li id="book-management">
          <a href="#">图书管理</a>
          <ul id="book-submenu">
            <li><a href="./book-list.jsp">图书列表</a></li>
          </ul>
        </li>
        <li id="score-management">
          <a href="#">评分管理</a>
          <ul id="score-submenu">
            <li><a href="./score-list">评分列表</a></li>
          </ul>
        </li>
        <li><a href="#">系统设置</a></li>
      </ul>
    </div>

    <div class="main-content">
      <h2 class="score-list">评分列表</h2>

      <div class="search-bar">
        <form action="score-list" method="get">
          <input
            type="text"
            name="searchByUser"
            placeholder="请输入用户名称..."
            value="${searchByUser}"
          />
          <input
            type="text"
            name="searchByBook"
            placeholder="请输入图书名称..."
            value="${searchByBook}"
          />
          <button type="submit">搜索</button>
        </form>
      </div>

      <table id="data-table">
        <thead>
          <tr>
            <th
              style="background-color: rgb(255, 229, 229); text-align: center"
            >
              序号
            </th>
            <th
              style="background-color: rgb(255, 229, 229); text-align: center"
            >
              用户
            </th>
            <th
              style="background-color: rgb(255, 229, 229); text-align: center"
            >
              图书名称
            </th>
            <th
              style="background-color: rgb(255, 229, 229); text-align: center"
            >
              评分
            </th>
            <th
              style="background-color: rgb(255, 229, 229); text-align: center"
            >
              评分时间
            </th>
            <th
              style="background-color: rgb(255, 229, 229); text-align: center"
            >
              操作
            </th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="userRating" items="${userRatings}">
            <tr>
              <td>${userRating.id}</td>
              <td>${userNames[userRating.user_id]}</td>
              <td>${bookNames[userRating.book_id]}</td>
              <td>${userRating.rating}</td>
              <td>${userRating.rating_time}</td>
              <td>
                <a href="./delete-score?id=${userRating.id}">删除</a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <div class="pagination">
        <p>
          共${totalUserRatingCount}条数据 第${currentPage}/${totalPages}页
          <c:if test="${currentPage > 1}">
            <a
              href="?page=1&searchByUser=${searchByUser}&searchByBook=${searchByBook}"
              >首页</a
            >
            <a
              href="?page=${currentPage - 1}&searchByUser=${searchByUser}&searchByBook=${searchByBook}"
              >上一页</a
            >
          </c:if>
          <c:if test="${currentPage < totalPages}">
            <a
              href="?page=${currentPage + 1}&searchByUser=${searchByUser}&searchByBook=${searchByBook}"
              >下一页</a
            >
            <a
              href="?page=${totalPages}&searchByUser=${searchByUser}&searchByBook=${searchByBook}"
              >尾页</a
            >
          </c:if>
        </p>
      </div>
    </div>
  </body>
  <script src="score-list.js"></script>
</html>
