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

@WebServlet("/book-revise")
public class BookReviseServlet extends HttpServlet {
    private BookService bookService = new BookService();

    @Override
    public void init() {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String bookid = request.getParameter("id");
        Book book = null;
        try {
            book = bookService.getBookById(Integer.parseInt(bookid));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("book", book);
        request.getRequestDispatcher("/book-revise.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String bookId = request.getParameter("id");
        String bookName = request.getParameter("book-name");
        String bookAuthor = request.getParameter("book-author");
        String bookType = request.getParameter("book-type");
        String bookImage = request.getParameter("book-image");
        String bookDescription = request.getParameter("book-description");

        try{
            Book book = bookService.getBookById(Integer.parseInt(bookId));
            book.setId(Integer.parseInt(bookId));
            book.setBook_name(bookName);
            book.setAuthor(bookAuthor);
            book.setTag(bookType);
            book.setCover_image_url(bookImage);
            book.setDescription(bookDescription);

            bookService.updateBook(book);
        } catch(SQLException e){
            e.printStackTrace();
        }

        response.sendRedirect("/book-list");
    }
}
