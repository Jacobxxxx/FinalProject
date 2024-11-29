<%@ page import="java.util.*" %>
<%@ page import="Service.BookService" %>
<%@ page import="model.Book" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  // 使用服务层获取数据
  BookService bookService = new BookService();

  // 个性化推荐：评分最高的前 14 本书
  List<Book> topRatedBooks = bookService.getTopBooksByRating(14);

  // 分页图书列表
  int pageSize = 14;
  int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
  long totalBooks = bookService.getBookCount();
  int totalPages = (int) Math.ceil((double) totalBooks / pageSize);
  List<Book> paginatedBooks = bookService.getBooksByPage(currentPage, pageSize);

  // 设置 JSP 属性
  request.setAttribute("topRatedBooks", topRatedBooks);
  request.setAttribute("paginatedBooks", paginatedBooks);
  request.setAttribute("currentPage", currentPage);
  request.setAttribute("totalPages", totalPages);
%>

<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <meta charset="UTF-8">
  <title>图书推荐系统</title>
  <link rel="stylesheet" href="main.css">
</head>

<body>
<div id="center">
  <header>
    <h1>图书推荐系统</h1>
    <div class="search-bar">
      <form action="login.jsp" method="post">
        <input type="text" name="keyword" placeholder="请输入图书名称..." value="${keyword}">
        <button type="submit">搜索</button>
      </form>
    </div>

    <div class="user-actions">
      <a href="login.jsp">登录</a>
      <a href="register.jsp">注册</a>
    </div>
  </header>
  <nav>
    <ul>
      <li><a href="viewer.jsp">首页</a></li>
      <li><a href="login.jsp">编程</a></li>
      <li><a href="login.jsp">科学</a></li>
      <li><a href="login.jsp">商业</a></li>
      <li><a href="login.jsp">心理</a></li>
      <li><a href="login.jsp">生活</a></li>
      <li><a href="login.jsp">哲学</a></li>
      <li><a href="login.jsp">悬疑</a></li>
      <li><a href="login.jsp">经典</a></li>
      <li><a href="login.jsp">文学</a></li>
      <li><a href="login.jsp">互联网</a></li>
      <li><a href="login.jsp">科普</a></li>
      <li><a href="login.jsp">管理</a></li>
    </ul>
  </nav>

  <section>
    <h2 class="personal-recommendations">个性化推荐</h2>
    <div class="book-list">

      <c:forEach var="book" items="${topRatedBooks}">
        <div class="book">
          <a href="login.jsp">
            <img src="imageProxy?url=${book.cover_image_url}" alt="${book.book_name}" onerror="this.src='default-image.jpg';">
          </a>
          <h3>${book.book_name}</h3>
          <p>类型: ${book.tag}</p>
          <p>评分: ${book.rating}</p>
        </div>
      </c:forEach>
    </div>
  </section>

  <section>
    <h2 class="personal-recommendations">图书列表</h2>
    <div class="book-list">
      <c:forEach var="book" items="${paginatedBooks}">
        <div class="book">
          <a href="login.jsp">
            <img src="imageProxy?url=${book.cover_image_url}" alt="${book.book_name}" onerror="this.src='default-image.jpg';">
          </a>
          <h3>${book.book_name}</h3>
          <p>类型: ${book.tag}</p>
          <p>评分: ${book.rating}</p>
        </div>
      </c:forEach>
    </div>
    <div class="pagination">
      <c:if test="${currentPage > 1}">
        <a href="viewer.jsp?page=1">首页</a>
        <a href="viewer.jsp?page=${currentPage - 1}">上一页</a>
      </c:if>
      <span>第 ${currentPage} / ${totalPages} 页</span>
      <c:if test="${currentPage < totalPages}">
        <a href="viewer.jsp?page=${currentPage + 1}">下一页</a>
        <a href="viewer.jsp?page=${totalPages}">尾页</a>
      </c:if>
    </div>
  </section>
</div>
</body>
</html>
