document.addEventListener('DOMContentLoaded', function () {
  var bookTypeManagement = document.getElementById('book-type-management');
  var bookTypeSubmenu = document.getElementById('book-type-submenu');

  bookTypeManagement.addEventListener('click', function () {
    bookTypeManagement.classList.toggle('active');
  });
});