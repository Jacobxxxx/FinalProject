//1.方格选中效果
document.addEventListener('DOMContentLoaded', function () {
  var selectAll = document.getElementById('select-all');
  var checkboxes = document.querySelectorAll('#data-table tbody input[type="checkbox"]');

  selectAll.addEventListener('click', function () {
    checkboxes.forEach(function (checkbox) {
      checkbox.checked = selectAll.checked;
    });
  });

  checkboxes.forEach(function (checkbox) {
    checkbox.addEventListener('click', function () {
      if (!this.checked) {
        selectAll.checked = false;
      }
    });
  });
});

//2.分页效果
document.addEventListener('DOMContentLoaded', function () {
  var nextPage = document.getElementById('next-page');
  var lastPage = document.getElementById('last-page');

  nextPage.addEventListener('click', function (e) {
    e.preventDefault();
    // 这里添加切换到下一页的逻辑
  });

  lastPage.addEventListener('click', function (e) {
    e.preventDefault();
    // 这里添加切换到尾页的逻辑
  });
});
