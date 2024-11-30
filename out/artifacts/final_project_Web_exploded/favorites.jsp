<%@ page import="java.util.*" %>
<%@ page import="Service.BookService" %>
<%@ page import="model.Book" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>图书推荐系统 - 收藏列表</title>
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
        <h2 class="personal-recommendations">我的收藏</h2>
        <div class="book-list">
            <!-- 检查是否有收藏的书籍 -->
            <c:if test="${not empty favoriteBooks}">
                <!-- 遍历收藏的书籍并显示 -->
                <c:forEach var="book" items="${favoriteBooks}">
                    <div class="book">
                        <a href="bookDetail?id=${book.id}">
                            <img src="imageProxy?url=${book.cover_image_url}" alt="${book.book_name}" onerror="this.src='default-image.jpg';">
                        </a>
                        <h3>${book.book_name}</h3>
                        <p>类型: ${book.tag}</p>
                        <p>评分: ${book.rating}</p>
                    </div>
                </c:forEach>
            </c:if>

            <!-- 如果没有收藏的书籍 -->
            <c:if test="${empty favoriteBooks}">
                <div class="no-results">您还没有收藏任何图书，快去浏览并收藏您喜欢的书籍吧！</div>
            </c:if>
        </div>
    </section>
</div>
</body>
</html>
