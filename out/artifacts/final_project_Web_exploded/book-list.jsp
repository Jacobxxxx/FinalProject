<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Book" %>
<%@ page import="Service.BookService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  BookService bookservice = new BookService();

  int pageSize = 10;
  int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
  String search = request.getParameter("search");
  long totalBooks;
  List<Book> bookList;

  if (search != null && !search.isEmpty()) {
    totalBooks = bookservice.getSearchResultCount(search);
    bookList = bookservice.searchBooks(search);
  } else {
    totalBooks = bookservice.getBookCount();
    bookList = bookservice.getBooksByPage(currentPage, pageSize);
  }

  int totalPages = (int) Math.ceil((double) totalBooks / pageSize);


  request.setAttribute("bookList", bookList);
  request.setAttribute("currentPage", currentPage);
  request.setAttribute("totalPages", totalPages);
  request.setAttribute("totalBooks", totalBooks);
  request.setAttribute("search", search);

%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>图书推荐系统后台管理员</title>
  <link rel="stylesheet" href="./book-list.css">
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
    <h2 class="book-list">图书列表</h2>

    <div class="search-bar">
      <form action="book-list" method="get">
        <button><a href="./book-revise.jsp">添加</a></button>&nbsp;&nbsp;&nbsp;
        <input type="text" name="search" placeholder="请输入图书名称..." value="${param.search}">
        <button type="submit">搜索</button>
      </form>
    </div>

    <table id="data-table">
      <thead>
        <tr>
          <th><input type="checkbox" id="select-all"></th>
          <th style="background-color: rgb(255, 229, 229);text-align: center;">序号</th>
          <th style="background-color: rgb(255, 229, 229);text-align: center;">图书封面</th>
          <th style="background-color: rgb(255, 229, 229);text-align: center;">图书名称</th>
          <th style="background-color: rgb(255, 229, 229);text-align: center;">图书类型</th>
          <th style="background-color: rgb(255, 229, 229);text-align: center;">操作</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="book" items="${bookList}">
          <tr>
            <td><input type="checkbox"></td>
            <td>${book.id}</td>
            <td><img src="imageProxy?url=${book.cover_image_url}" alt="${book.book_name}"></td>
            <td>${book.book_name}</td>
            <td>${book.tag}</td>
            <td>
              <a href="./book-revise.jsp?id=${book.id}">修改</a>
              <a href="./delete-book?id=${book.id}">删除</a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div class="pagination">
      <p>共${totalBooks}条数据 第${currentPage}/${totalPages}页 
      <c:if test="${currentPage > 1}">
        <a href="?page=1">首页</a>
         <a href="?page=${currentPage - 1}">上一页</a>
      </c:if>
      <c:if test="${currentPage < totalPages}">
        <a href="?page=${currentPage + 1}">下一页</a> 
        <a href="?page=${totalPages}">尾页</a>
      </c:if></p>
    </div>
  </div>

</body>
<script src="./book-list.js"></script>

</html>