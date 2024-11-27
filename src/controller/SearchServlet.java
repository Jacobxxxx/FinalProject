package controller;

import Service.BookService;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private BookService bookService = new BookService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取搜索关键词
        request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("keyword");



        try {
            // 获取搜索结果
            List<Book> searchResults = bookService.searchBooks(keyword);
            // 获取搜索结果总数
            long totalResults = bookService.getSearchResultCount(keyword);

            System.out.println("Total results: " + searchResults);
            // 设置 JSP 请求属性
            request.setAttribute("searchResults", searchResults);
            request.setAttribute("keyword", keyword);


            // 转发到搜索结果页面
            request.getRequestDispatcher("search.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "搜索失败");
        }
    }
}
