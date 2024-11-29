<%@ page import="java.util.*" %>
<%@ page import="Service.RecommendationService" %>
<%@ page import="Service.BookService" %>
<%@ page import="model.Book" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  // 获取当前登录的用户
  String userId = (String) session.getAttribute("userId");

  // 检查用户是否已登录
  if (userId == null) {
    response.sendRedirect("login.jsp");  // 如果未登录，跳转到登录页
    return;
  }

  // 初始化服务层
  BookService bookService = new BookService();
  RecommendationService recommendationService = new RecommendationService(bookService);

  // 获取用户的推荐图书
  List<Book> recommendedBooks = recommendationService.generateRecommendations(userId);

  // 获取分页图书列表
  int pageSize = 14;
  int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
  long totalBooks = bookService.getBookCount();
  int totalPages = (int) Math.ceil((double) totalBooks / pageSize);
  List<Book> paginatedBooks = bookService.getBooksByPage(currentPage, pageSize);

  // 设置 JSP 属性
  request.setAttribute("recommendedBooks", recommendedBooks);
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
    </ul>
  </nav>

  <section>
    <h2 class="personal-recommendations">个性化推荐</h2>
    <div class="book-list">
      <c:forEach var="book" items="${recommendedBooks}">
        <div class="book">
          <a href="bookDetail?id=${book.id}">
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
          <a href="bookDetail?id=${book.id}">
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
        <a href="main.jsp?page=1">首页</a>
        <a href="main.jsp?page=${currentPage - 1}">上一页</a>
      </c:if>
      <span>第 ${currentPage} / ${totalPages} 页</span>
      <c:if test="${currentPage < totalPages}">
        <a href="main.jsp?page=${currentPage + 1}">下一页</a>
        <a href="main.jsp?page=${totalPages}">尾页</a>
      </c:if>
    </div>
  </section>
</div>
</body>
</html>
