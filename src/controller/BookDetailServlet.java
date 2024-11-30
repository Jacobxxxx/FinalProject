package controller;

import Service.BookService;
import Service.UserActionService;
import Service.UserRatingService;
import model.Book;
import model.UserAction;
import model.UserRating;

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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            handleRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            handleRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        String bookId = request.getParameter("id");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        UserActionService userActionService = new UserActionService();
        UserRatingService userRatingService = new UserRatingService();

        //是否收藏
        boolean isFavorite =false;
        if (userId != null && bookId != null) {
            try {
                UserAction userAction = userActionService.getUserActionByUserIdAndBookId(userId, Integer.parseInt(bookId));
                if (userAction != null && userAction.getFavorite() == 1) {
                    isFavorite = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //是否已评分
        boolean isRated = false;
        double userRatingValue = 0.0;
        String ratingTime = "";
        if (userId != null && bookId != null) {
            try {
                UserRating userRating = userRatingService.getUserRatingByUserIdAndBookId(userId, Integer.parseInt(bookId));
                if (userRating != null) {
                    isRated = true;
                    userRatingValue = userRating.getRating();
                    ratingTime = userRating.getRating_time().toString();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //加入收藏
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //取消收藏
        if ("removeFavorite".equals(action) && userId != null && bookId != null) {
            try {
                UserAction userAction = userActionService.getUserActionByUserIdAndBookId(userId, Integer.parseInt(bookId));
                if (userAction != null) {
                    userAction.setFavorite(0);
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

        //提交评分
        if ("submitRating".equals(action) && userId != null && bookId != null) {
            String ratingStr = request.getParameter("rating");
            if (ratingStr == null || ratingStr.isEmpty()) {
                response.getWriter().write("error: rating is null or empty");
                return;
            }
            try {
                double rating = Double.parseDouble(ratingStr);
                UserRating userRating = userRatingService.getUserRatingByUserIdAndBookId(userId, Integer.parseInt(bookId));
                if (userRating == null) {
                    userRating = new UserRating();
                    userRating.setUser_id(userId);
                    userRating.setBook_id(Integer.parseInt(bookId));
                    userRating.setRating(rating);
                    userRatingService.addUserRating(userRating);
                }
                response.getWriter().write("success");
                return;
            } catch (SQLException | NumberFormatException e) {
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
        request.setAttribute("isFavorite", isFavorite);
        request.setAttribute("isRated", isRated);
        request.setAttribute("userRating", userRatingValue);
        request.setAttribute("ratingTime", ratingTime);

        request.getRequestDispatcher("/bookDetail.jsp").forward(request, response);
    }

}