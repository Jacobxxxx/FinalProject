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
        <div class="user-actions">
          <a href="profile">个人信息</a>
          <a href="favorites">收藏列表</a>
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
              class="favorite-button ${isFavorite ? 'favorited' : ''}"
              id="favorite-button"
              onclick="toggleFavorite()"
            >
              ${isFavorite ? '已收藏' : '收藏'}
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
              class="${isRated ? 'unShow' : ''}"
            />
            <button
              type="button"
              id="submit-rating-button"
              onclick="submitRating()"
              class="${isRated ? 'unShow' : ''}"
            >
              提交评分
            </button>
            <span id="user-rating-display" class="${isRated ? '' : 'unShow'}"
              >${userRating}</span
            >
            <span id="rating-time-display" class="${isRated ? '' : 'unShow'}"
              >评分时间：${ratingTime}</span
            >
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
              var bookId = "${book.id}";
              var rating = document.getElementById("user-rating").value;
              if (rating < 0 || rating > 10) {
                alert("评分必须在0到10之间");
                return;
              }
              var xhr = new XMLHttpRequest();
              xhr.open(
                "POST",
                "${pageContext.request.contextPath}/bookDetail",
                true
              );
              xhr.setRequestHeader(
                "Content-Type",
                "application/x-www-form-urlencoded"
              );
              xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                  alert("您提交的评分是：" + rating);
                  document
                    .getElementById("user-rating")
                    .classList.add("unShow");
                  document.getElementById("user-rating-display").innerText =
                    rating;
                  document
                    .getElementById("submit-rating-button")
                    .classList.add("unShow");
                  document
                    .getElementById("user-rating-display")
                    .classList.remove("unShow");
                  document.getElementById("rating-time-display").innerText =
                    "评分时间：" + new Date().toLocaleString();
                  document
                    .getElementById("rating-time-display")
                    .classList.remove("unShow");
                } else if (xhr.readyState === 4) {
                  // 请求失败，处理错误
                  console.error("Request failed:", xhr.statusText);
                }
              };
              xhr.send(
                "action=submitRating&id=" + bookId + "&rating=" + rating
              );
            }

            function toggleFavorite() {
              var bookId = "${book.id}";
              var xhr = new XMLHttpRequest();
              xhr.open(
                "POST",
                "${pageContext.request.contextPath}/bookDetail",
                true
              );
              xhr.setRequestHeader(
                "Content-Type",
                "application/x-www-form-urlencoded"
              );
              xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                  var button = document.getElementById("favorite-button");
                  if (button.innerText === "收藏") {
                    button.innerText = "已收藏";
                    button.classList.add("favorited");
                    alert("图书已添加到收藏夹！");
                  } else {
                    button.innerText = "收藏";
                    button.classList.remove("favorited");
                    alert("图书已从收藏夹移除！");
                  }
                } else if (xhr.readyState === 4) {
                  // 请求失败，处理错误
                  console.error("Request failed:", xhr.statusText);
                }
              };
              var action =
                document.getElementById("favorite-button").innerText === "收藏"
                  ? "addFavorite"
                  : "removeFavorite";
              xhr.send("action=" + action + "&id=" + bookId);
            }
          </script>
        </div>
      </div>
    </div>
  </body>
</html>
