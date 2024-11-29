<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="model.Book" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="show.css" />
    <title>${book_name} - 图书详情</title>
  </head>
  <body>
    <div id="center">
      <header>
        <h1>图书推荐系统</h1>
        <div class="search-bar">
          <input type="text" placeholder="请输入图书名称..." />
          <button>搜索</button>
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
          <li><a href="category?category=心理">心理</a></li>
        </ul>
      </nav>
      <section class="details">
        <h2>图书详情</h2>
      </section>
      <div class="container">
        <div class="book-image">
          <img src="imageProxy?url=${cover_image_url}" alt="${book_name}" />
        </div>
        <div class="book-details">
          <div class="favorite-button-container">
            <button
              type="button"
              class="favorite-button"
              onclick="addToFavorites()"
            >
              收藏
            </button>
          </div>
          <h2>${book_name}</h2>
          <p><strong>图书类型：</strong>${tag}</p>
          <p><strong>出版时间：</strong>${publish_year}</p>
          <p>
            <strong>图书评分：</strong>
            <input
              type="number"
              id="user-rating"
              name="user-rating"
              min="0"
              max="10"
              step="0.1"
              value=""
            />
            <button type="button" onclick="submitRating()">提交评分</button>
          </p>
          <p><strong>出版社：</strong>${publisher}</p>
          <p><strong>官方评分：</strong>${rating}/10</p>
          <p><strong>作者简介：</strong>${author_intro}</p>
          <p><strong>图书简介：</strong></p>
          <p
            class="text"
            style="background-color: #bcb8b8; padding: 10px; border-radius: 8px"
          >
            ${description}
          </p>
          <script>
            function submitRating() {
              var rating = document.getElementById("user-rating").value;
              if (rating < 0 || rating > 10) {
                alert("评分必须在0到10之间");
                return;
              }
              // 这里可以添加代码将评分提交到服务器
              alert("您提交的评分是：" + rating);
            }

            function addToFavorites() {
              var bookId = "${book.id}";
              var xhr = new XMLHttpRequest();
              xhr.open("POST", "/bookDetail", true);
              xhr.setRequestHeader(
                "Content-Type",
                "application/x-www-form-urlencoded"
              );
              xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                  alert("图书已添加到收藏夹！");
                } else if (xhr.readyState === 4) {
                  // 请求失败，处理错误
                  console.error("Request failed:", xhr.statusText);
                }
              };
              xhr.send("action=addFavorite&bookId=" + bookId);
            }
          </script>
        </div>
      </div>
    </div>
  </body>
</html>
