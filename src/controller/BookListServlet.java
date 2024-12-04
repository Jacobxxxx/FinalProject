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
import java.util.List;

@WebServlet("/book-list")
public class BookListServlet extends HttpServlet {
    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int pageSize = 10;
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        String search = request.getParameter("search");
        if (search != null) {
            try {
                List<Book> books = bookService.searchBooks(search);
                request.setAttribute("books", books);
                request.getRequestDispatcher("/book-list.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
            return;
        }else{
            try {
                BookService bookService = new BookService();
                long totalBooks = bookService.getBookCount();
                int totalPages = (int) Math.ceil((double) totalBooks / pageSize);
                List<Book> books = bookService.getBooksByPage(currentPage, pageSize);
    
                request.setAttribute("books", books);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("totalBooks", totalBooks);
    
                request.getRequestDispatcher("/book-list.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }

        }

        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
