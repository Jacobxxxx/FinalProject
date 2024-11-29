<%@ page import="model.UserAction, model.Book" %>
<%@ page import="Service.UserActionService, Service.BookService" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String userId = (String) session.getAttribute("username");
    if (userId == null) {
        response.sendRedirect("login.jsp");  // 如果没有登录，跳转到登录页面
        return;
    }

    // 获取当前用户的收藏书籍
    UserActionService userActionService = new UserActionService();
    List<UserAction> userActions = userActionService.getUserFavorites(userId);
    BookService bookService = new BookService();
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>收藏列表</title>
    <link rel="stylesheet" href="main.css">
</head>
<body>
<h1>收藏列表</h1>

<c:forEach var="userAction" items="${userActions}">
    <%
        // 获取每本书的详细信息
        Book book = bookService.getBookById(userAction.getBook_id());
    %>
    <div>
        <p>书名: ${book.book_name}</p>
        <p>评分: ${book.rating}</p>
        <img src="imageProxy?url=${book.cover_image_url}" alt="${book.book_name}" width="100">
    </div>
</c:forEach>

<a href="main.jsp">返回首页</a>
</body>
</html>
