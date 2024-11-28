<%@ page import="java.util.*" %>
<%@ page import="Service.BookService" %>
<%@ page import="model.Book" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="UTF-8">
        <title>?{category}图书</title>
        <link rel="stylesheet" href="main.css">
        <style>
          * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
          }
      
          #center {
            width: 70%;
            margin: 0 auto;
          }
      
          header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 20px;
            background-color: white;
          }
      
          h1 {
            font-size: 24px;
          }
      
          .user-actions a:hover {
            color: red;
          }
      
          .search-bar {
            display: flex;
          }
      
          .search-bar input {
            padding: 5px 10px;
            border: 1px solid #ccc;
            border-radius: 3px 0 0 3px;
          }
      
          .search-bar button {
            padding: 5px 15px;
            border: 1px solid #ccc;
            border-left: none;
            border-radius: 0 3px 3px 0;
            background-color: #eee;
            cursor: pointer;
          }
      
          .user-actions {
            display: flex;
          }
      
          .user-actions a {
            margin-left: 10px;
            text-decoration: none;
            color: #333;
          }
      
          nav {
            background-color: rgb(133, 96, 153);
          }
      
          nav ul {
            flex-wrap: wrap;
            display: flex;
            list-style: none;
            padding: 0px 2px;
          }
      
          nav li {
            margin: 20px 20px;
          }
      
          nav a {
            color: white;
            text-decoration: none;
            padding: 10px;
          }
      
          li:hover {
            background-color: red;
            transform: scale(1.2);
            transform-origin: left top;
          }
      
          nav li.active {
            background-color: red;
          }
      
          .personal-recommendations {
            margin-top: 10px;
            background-color: rgb(255, 185, 0);
            height: 50px;
            line-height: 50px;
            padding-left: 10px;
          }
      
          .book-list {
            display: flex;
            flex-wrap: wrap;
          }
      
          .book {
            border: 2px solid rgb(241, 241, 241);
            width: 200px;
            margin: 20px;
            text-align: center;
            padding: 5px;
          }
      
          .book img {
            max-width: 100%;
            height: auto;
          }
      
          .pagination {
            text-align: center;
            margin-top: 20px;
          }
      
          .page-link {
            font-size: 16px;
            margin: 0 10px;
            cursor: pointer;
            color: #777777;
            text-decoration: none;
          }
      
          .page-link:hover {
            text-decoration: underline;
          }
      
          .page-info {
            font-size: 16px;
            color: #333;
          }
        </style>
  <body>
    <header>
        <h1>图书推荐系统</h1>
        <div class="search-bar">
          <form action="search" method="post">
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
          <li><a href="category?category=首页">首页</a></li>
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
          <li><a href="category?category=心理学">心理</a></li>
        </ul>
      </nav>
      <section>
        <h2 class="personal-recommendations">图书列表</h2>
        <div class="book-list">
          <c:forEach var="book" items="${books}">
            <div class="book">
              <a href="bookDetail.jsp?id=${book.id}">
                <img src="imageProxy?url=${book.cover_image_url}" alt="${book.book_name}" onerror="this.src='default-image.jpg';">
              </a>
              <h3>${book.book_name}</h3>
              <p>类型: ${book.tag}</p>
              <p>评分: ${book.rating}</p>
            </div>
          </c:forEach>
        </div>
      </section>
  </body>
</html>
