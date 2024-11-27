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

@WebServlet("/category")
public class ClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String category = request.getParameter("category");

        if ("首页".equals(category)) {
            request.getRequestDispatcher("main.jsp").forward(request, response);
            return;
        }
        BookService bookService = new BookService();
        List<Book> books = null;
        try {
            books = bookService.getBooksByTag(category);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("category", category);
        request.setAttribute("books", books);
        request.getRequestDispatcher("category.jsp").forward(request, response);
    }
    
}
