package controller;

import Service.BookService;
import Service.UserActionService;
import model.Book;
import model.UserAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/bookDetail")
public class BookDetailServlet extends HttpServlet {
    @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    String bookId = request.getParameter("id");
    HttpSession session = request.getSession();
    String userId = (String) session.getAttribute("userId");
    String action = request.getParameter("action");

    BookService bookService = new BookService();
    UserActionService userActionService = new UserActionService();
    Book book = null;
    try {
      book = bookService.getBookById(Integer.parseInt(bookId));
    } catch (SQLException e) {
      e.printStackTrace();
    }

    //收藏判断
  if ("addFavorite".equals(action) && userId != null && bookId != null) {
    try {
        UserAction userAction = userActionService.getUserActionByUserIdAndBookId(userId, Integer.parseInt(bookId));
        if (userAction == null) {
            userAction = new UserAction();
            userAction.setUser_id(userId);
            userAction.setBook_id(Integer.parseInt(bookId));
            userAction.setBrowse(1);
            userAction.setFavorite(1);
            userActionService.addUserAction(userAction);
        } else {
            userAction.setFavorite(1);
            userActionService.updateUserAction(userAction);
        }
        response.getWriter().write("success");
        return;
    } catch (SQLException e) {
        e.printStackTrace();
        response.getWriter().write("error");
        return;
    }
  }

    //用户浏览记录
    if (userId != null && bookId != null) {
      try {
          UserAction userAction = userActionService.getUserActionByUserIdAndBookId(userId, Integer.parseInt(bookId));
          if (userAction == null) {
              userAction = new UserAction();
              userAction.setUser_id(userId);
              userAction.setBook_id(Integer.parseInt(bookId));
              userAction.setBrowse(1);
              userAction.setFavorite(0);
              userActionService.addUserAction(userAction);
          } else {
              userAction.setBrowse(1);
              userActionService.updateUserAction(userAction);
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }

    
    request.setAttribute("book", book);
    request.setAttribute("book_name", book.getBook_name());
    request.setAttribute("tag", book.getTag());
    request.setAttribute("publish_year", book.getPublish_year());
    request.setAttribute("publisher", book.getPublisher());
    request.setAttribute("rating", book.getRating());
    request.setAttribute("author", book.getAuthor());
    request.setAttribute("description", book.getDescription());   
    request.setAttribute("cover_image_url", book.getCover_image_url());

    
    request.getRequestDispatcher("/bookDetail.jsp").forward(request, response);
  }
}
