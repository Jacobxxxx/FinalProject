<%@ page import="java.util.*" %>
<%@ page import="Service.BookService" %>
<%@ page import="model.Book" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>图书推荐系统 - 搜索结果</title>
    <link rel="stylesheet" href="main.css">
</head>

<body>
<div id="center">
    <header>
        <h1>图书推荐系统</h1>
        <div class="search-bar">
            <form action="search" method="POST">
                <input type="text" name="keyword" value="${keyword}" placeholder="请输入图书名称...">
                <button type="submit">搜索</button>
            </form>
        </div>
    </header>

    <nav>
        <ul>
            <li><a href="#">首页</a></li>
            <li><a href="#">编程</a></li>
            <li><a href="#">科学</a></li>
            <li><a href="#">商业</a></li>
            <li><a href="#">心理</a></li>
            <li><a href="#">生活</a></li>
            <li><a href="#">哲学</a></li>
            <li><a href="#">悬疑</a></li>
            <li><a href="#">经典</a></li>
            <li><a href="#">文学</a></li>
            <li><a href="#">互联网</a></li>
            <li><a href="#">科普</a></li>
            <li><a href="#">管理</a></li>
            <li><a href="#">心理学</a></li>
            <li><a href="#">历史</a></li>
            <li><a href="#">小说</a></li>
        </ul>
    </nav>

    <section>
        <h2 class="personal-recommendations">搜索结果</h2>
        <div class="book-list">
            <c:if test="${searchResults!= null && !searchResults.isEmpty()}">
                <c:forEach var="book" items="${searchResults}">
                    <div class="book">
                        <a href="bookDetail.jsp?id=${book.id}">
                            <img src="imageProxy?url=${book.cover_image_url}" alt="${book.book_name}" onerror="this.src='default-image.jpg';">
                        </a>
                        <h3>${book.book_name}</h3>
                        <p>类型: ${book.tag}</p>
                        <p>评分: ${book.rating}</p>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${searchResults == null || searchResults.isEmpty()}">
                <div class="no-results">未找到匹配的图书，请尝试其他关键词。</div>
            </c:if>
        </div>
    </section>
</div>
</body>
</html>