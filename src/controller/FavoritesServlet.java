package controller;

import Service.UserActionService;
import model.UserAction;
import model.Book;
import Service.BookService;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/favorites")
public class FavoritesServlet extends HttpServlet {

    private UserActionService userActionService;
    private BookService bookService;

    @Override
    public void init() throws ServletException {
        // 初始化 UserActionService 和 BookService
        userActionService = new UserActionService();
        bookService = new BookService();  // 初始化 BookService
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前用户的ID（假设用户的ID存在 session 中）
        String userId = (String) request.getSession().getAttribute("userId");

        if (userId == null) {
            // 如果用户没有登录，重定向到登录页面
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 获取用户的收藏行为记录
            List<UserAction> userFavorites = userActionService.getUserFavorites(userId);

            // 提取收藏的图书ID，并获取对应的图书信息
            List<Book> favoriteBooks = new ArrayList<>();
            for (UserAction userAction : userFavorites) {
                if (userAction.getFavorite() == 1) {
                    // 获取图书信息
                    Book favoriteBook = bookService.getBookById(userAction.getBook_id());
                    if (favoriteBook != null) {  // 确保图书存在
                        favoriteBooks.add(favoriteBook);
                    }
                }
            }

            // 将收藏的图书列表传递到 JSP 页面
            request.setAttribute("favoriteBooks", favoriteBooks);

            // 转发到收藏列表页面
            request.getRequestDispatcher("favorites.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // 错误页面
        }
    }
}
