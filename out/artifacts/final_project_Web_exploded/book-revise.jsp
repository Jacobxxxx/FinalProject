<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Book" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>图书修改</title>
  <link rel="stylesheet" href="book-revise.css">
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
          <li><a href="./book-list.jsp">图书列表</a></li>
        </ul>
      </li>
      <li id="score-management">
        <a href="#">评分管理</a>
        <ul id="score-submenu">
          <li><a href="./score-list.jsp">评分列表</a></li>
        </ul>
      </li>

    </ul>
  </div>

  <div class="main-content">
    <h2 class="book-revise">图书修改</h2>

    <form action="book-revise" method="post">
        <input type="hidden" name="id" value="${book.id}">
      <div class="form-group">
        <label for="book-name">图书名称：</label>
        <input type="text" id="book-name" name="book-name" placeholder="请输入图书名称" value="${book.book_name}">
      </div>
      <div class="form-group">
        <label for="book-author">图书作者：</label>
        <input type="text" id="book-author" name="book-author" placeholder="请输入图书作者" value="${book.author}">
      </div>
      <div class="form-group">
        <label for="book-type">图书类型：</label>
        <select id="book-type" name="book-type">
          <option value="商业" ${book.tag == '商业' ? 'selected' : ''}>商业</option>
          <option value="悬疑" ${book.tag == '悬疑' ? 'selected' : ''}>悬疑</option>
          <option value="经典" ${book.tag == '经典' ? 'selected' : ''}>经典</option>
          <option value="文学" ${book.tag == '文学' ? 'selected' : ''}>文学</option>
          <option value="互联网" ${book.tag == '互联网' ? 'selected' : ''}>互联网</option>
          <option value="科普" ${book.tag == '科普' ? 'selected' : ''}>科普</option>
          <option value="管理" ${book.tag == '管理' ? 'selected' : ''}>管理</option>
          <option value="心理" ${book.tag == '心理' ? 'selected' : ''}>心理</option>
          <option value="编程" ${book.tag == '编程' ? 'selected' : ''}>编程</option>
          <option value="科学" ${book.tag == '科学' ? 'selected' : ''}>科学</option>
          <option value="生活" ${book.tag == '生活' ? 'selected' : ''}>生活</option>
          <option value="哲学" ${book.tag == '哲学' ? 'selected' : ''}>哲学</option>
        </select>
      </div>
      <div class="form-group">
        <label for="book-image">图书图片：</label>
        <input type="text" id="book-image" name="book-image" placeholder="请输入图书图片URL" value="${book.cover_image_url}">
      </div>
      <div class="form-group">
        <label for="book-description">图书简介：</label>
        <textarea id="book-description" name="book-description" rows="5" placeholder="请输入图书简介">${book.description}</textarea>
      </div>
      <button type="submit">提交</button>
      <button type="reset">重置</button>
    </form>
  </div>
</body>
</html>