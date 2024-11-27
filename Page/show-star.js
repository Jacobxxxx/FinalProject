const stars = document.querySelectorAll('.star');
const ratingValue = document.getElementById('rating-value');

// 初始化评分
let rating = 0;

// 鼠标悬停效果
stars.forEach(star => {
    star.addEventListener('mouseover', () => {
        const value = parseInt(star.getAttribute('data-value'));
        updateStars(value);
    });

    // 鼠标移出时恢复显示的星星
    star.addEventListener('mouseout', () => {
        updateStars(rating);
    });

    // 点击星星更新评分
    star.addEventListener('click', () => {
        rating = parseInt(star.getAttribute('data-value'));
        ratingValue.textContent = rating;
        updateStars(rating);
    });
});

// 更新星星的显示状态
function updateStars(value) {
    stars.forEach(star => {
        const starValue = parseInt(star.getAttribute('data-value'));
        if (starValue <= value) {
            star.classList.add('selected'); // 选中
        } else {
            star.classList.remove('selected'); // 取消选中
        }
    });
}
