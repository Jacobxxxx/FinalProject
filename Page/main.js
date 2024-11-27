document.querySelectorAll('.rating').forEach(ratingElement => {
  let rating = 0;

  const stars = ratingElement.querySelectorAll('.star');

  function updateStars(value) {
    stars.forEach(star => {
      const starValue = parseInt(star.getAttribute('data-index'));
      if (starValue < value) {
        star.classList.add('selected');
      } else {
        star.classList.remove('selected');
      }
    });
  }

  stars.forEach(star => {
    star.addEventListener('mouseover', () => {
      const value = parseInt(star.getAttribute('data-index'));
      updateStars(value + 1);
    });

    star.addEventListener('mouseout', () => {
      updateStars(rating);
    });

    star.addEventListener('click', () => {
      rating = parseInt(star.getAttribute('data-index')) + 1;
      updateStars(rating);
    });
  });
});

// 获取页面元素
const currentPageElement = document.getElementById("current-page");
const totalPagesElement = document.getElementById("total-pages");
const previousPageButton = document.getElementById("previous-page");
const nextPageButton = document.getElementById("next-page");
const lastPageButton = document.getElementById("last-page");

let currentPage = 1;
const totalPages = 19;

// 更新分页显示
function updatePagination() {
  currentPageElement.textContent = currentPage;
  totalPagesElement.textContent = totalPages;

  // 控制上一页和下一页按钮是否可点击
  previousPageButton.style.pointerEvents = currentPage === 1 ? 'none' : 'auto';
  nextPageButton.style.pointerEvents = currentPage === totalPages ? 'none' : 'auto';
}

// 处理上一页点击
previousPageButton.addEventListener("click", function (e) {
  e.preventDefault();
  if (currentPage > 1) {
    currentPage--;
    updatePagination();
  }
});

// 处理下一页点击
nextPageButton.addEventListener("click", function (e) {
  e.preventDefault();
  if (currentPage < totalPages) {
    currentPage++;
    updatePagination();
  }
});

// 处理尾页点击
lastPageButton.addEventListener("click", function (e) {
  e.preventDefault();
  currentPage = totalPages;
  updatePagination();
});

// 初始化分页
updatePagination();