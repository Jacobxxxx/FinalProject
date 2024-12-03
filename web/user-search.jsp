<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<%@ page import="model.User" %>
<%@ page import="Service.UserService" %>

<%
    // 初始化服务层
    UserService userService = new UserService();

    // 获取搜索关键词
    String keyword = request.getParameter("keyword");
    keyword = keyword == null ? "" : keyword;

    // 分页逻辑
    int pageSize = 10; // 每页显示10条
    int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
    long totalUsers = userService.countUsersByKeyword(keyword); // 根据关键词统计总用户数
    int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

    // 获取当前页的用户列表
    List<User> users = userService.searchUsersByKeywordWithPagination(keyword, currentPage, pageSize);

    // 设置 JSP 属性
    request.setAttribute("users", users);
    request.setAttribute("currentPage", currentPage);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("keyword", keyword);
    request.setAttribute("totalUsers", totalUsers);
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>搜索结果 - 用户列表</title>
    <link rel="stylesheet" href="user-list.css">
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
                <li><a href="$book-type.jsp">图书类型列表</a></li>
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
                <li><a href="score-list.jsp">评分列表</a></li>
            </ul>
        </li>
        <li><a href="#">系统设置</a></li>
    </ul>
</div>

<div class="main-content">
    <h2 class="user-list">搜索结果</h2>

    <div class="search-bar">
        <form action="user-search" method="GET">
            <input type="text" name="keyword" value="${keyword}" placeholder="请输入用户名称...">
            <button type="submit">搜索</button>
        </form>
    </div>

    <table id="data-table">
        <thead>
        <tr>
            <th>序号</th>
            <th>用户名</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty users}">
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.user_id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td><a href="delete-user?id=${user.user_id}">删除</a></td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty users}">
            <tr>
                <td colspan="4" style="text-align:center;">未找到符合条件的用户！</td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <div class="pagination">
        <p>共${totalUsers}条数据 第${currentPage}/${totalPages}页
            <c:if test="${currentPage > 1}">
                <a href="user-search?keyword=${keyword}&page=1">首页</a>
                <a href="user-search?keyword=${keyword}&page=${currentPage - 1}">上一页</a>
            </c:if>
            <c:if test="${currentPage < totalPages}">
                <a href="user-search?keyword=${keyword}&page=${currentPage + 1}">下一页</a>
                <a href="user-search?keyword=${keyword}&page=${totalPages}">尾页</a>
            </c:if>
        </p>
    </div>

    <div class="return-link">
        <a href="user-list.jsp">返回用户列表</a>
    </div>
</div>
</body>
</html>
