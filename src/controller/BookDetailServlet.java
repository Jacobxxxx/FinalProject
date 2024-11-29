package controller;

import Service.BookService;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/bookDetail")
public class BookDetailServlet extends HttpServlet {
    @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("text/html;charset=UTF-8");

    String bookId = request.getParameter("id");
    
    BookService bookService = new BookService();
    Book book = null;
    try {
      book = bookService.getBookById(Integer.parseInt(bookId));
    } catch (SQLException e) {
      e.printStackTrace();
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
