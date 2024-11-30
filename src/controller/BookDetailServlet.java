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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/bookDetail")
public class BookDetailServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BookDetailServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String bookId = request.getParameter("id");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        // if (bookId == null || bookId.isEmpty()) {
        //     response.getWriter().write("error: bookId is null or empty");
        //     return;
        // }

        UserActionService userActionService = new UserActionService();

        //加入收藏
        if ("addFavorite".equals(action) && userId != null && bookId != null) {
            try {
                UserAction userAction = userActionService.getUserActionByUserIdAndBookId(userId, Integer.parseInt(bookId));
                LOGGER.log(Level.INFO, "User ID: {0}", userId);
                if (userAction == null) {
                    userAction = new UserAction();
                    userAction.setUser_id(userId);
                    userAction.setBook_id(Integer.parseInt(bookId));
                    userAction.setBrowse(1);
                    userAction.setFavorite(1);
                    userActionService.addUserAction(userAction);
                } else {
                    LOGGER.log(Level.INFO, "UserAction not found, creating new one.");
                    userAction.setFavorite(1);
                    userActionService.updateUserAction(userAction);
                }
                response.getWriter().write("success");
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("error");
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        BookService bookService = new BookService();

        Book book = null;
        try {
            book = bookService.getBookById(Integer.parseInt(bookId));
        } catch (SQLException e) {
            e.printStackTrace();
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