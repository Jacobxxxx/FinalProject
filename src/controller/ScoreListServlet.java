package controller;

import Service.BookService;
import Service.UserRatingService;
import Service.UserService;
import model.Book;
import model.User;
import model.UserRating;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/score-list")
public class ScoreListServlet extends HttpServlet {
    private UserRatingService userRatingService;
    private BookService bookService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userRatingService = new UserRatingService();
        bookService = new BookService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        int pageSize = 10;
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        String searchByUser = request.getParameter("searchByUser");
        String searchByBook = request.getParameter("searchByBook");

        List<UserRating> userRatings = new ArrayList<>();
        Map<Integer, String> bookNames = new HashMap<>();
        Map<String, String> userNames = new HashMap<>();
        long totalUserRatingCount = 0;

        try {
            if((searchByUser == null || searchByUser.isEmpty()) && (searchByBook == null || searchByBook.isEmpty())) {
                userRatings = userRatingService.getUserRatingsByPage(currentPage, pageSize);
                for (UserRating userRating : userRatings) {
                    Book book = bookService.getBookById(userRating.getBook_id());
                    User user = userService.getUserById(userRating.getUser_id());
                    bookNames.put(book.getId(), book.getBook_name());
                    userNames.put(user.getUser_id(), user.getUsername());
                }
                totalUserRatingCount = userRatingService.getUserRatingCount();
            }else if (searchByUser != null && !searchByUser.isEmpty() && searchByBook != null && !searchByBook.isEmpty()) {
                User user = userService.getUserByUsername(searchByUser);
                if (user != null) {
                    List<Book> books = bookService.searchBooks(searchByBook);
                    for (Book book : books) {
                        UserRating userRating = userRatingService.getUserRatingByUserIdAndBookId(user.getUser_id(), book.getId());
                        if (userRating != null) {
                            userRatings.add(userRating);
                            bookNames.put(book.getId(), book.getBook_name());
                            userNames.put(user.getUser_id(), user.getUsername());
                        }
                    }
                }
                totalUserRatingCount = userRatings.size();
            } else if (searchByUser != null && !searchByUser.isEmpty()) {
                User user = userService.getUserByUsername(searchByUser);
                if (user != null) {
                    userRatings = userRatingService.getUserRatingsByUserId(user.getUser_id());
                    for (UserRating userRating : userRatings) {
                        Book book = bookService.getBookById(userRating.getBook_id());
                        bookNames.put(book.getId(), book.getBook_name());
                        userNames.put(user.getUser_id(), user.getUsername());
                    }
                }
                totalUserRatingCount = userRatings.size();
            } else if (searchByBook != null && !searchByBook.isEmpty()) {
                List<Book> books = bookService.searchBooks(searchByBook);
                for (Book book : books) {
                    List<UserRating> ratings = userRatingService.getUserRatingsByBookId(book.getId());
                    userRatings.addAll(ratings);
                    bookNames.put(book.getId(), book.getBook_name());
                    for (UserRating userRating : ratings) {
                        User user = userService.getUserById(userRating.getUser_id());
                        userNames.put(user.getUser_id(), user.getUsername());
                    }
                }
                totalUserRatingCount = userRatings.size();
            }

            int totalPages = (int) Math.ceil((double) totalUserRatingCount / pageSize);
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, userRatings.size());
            userRatings = userRatings.subList(startIndex, endIndex);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
            return;
        }

        int totalPages = (int) Math.ceil((double) totalUserRatingCount / pageSize);

        request.setAttribute("userRatings", userRatings);
        request.setAttribute("bookNames", bookNames);
        request.setAttribute("userNames", userNames);
        request.setAttribute("totalUserRatingCount", totalUserRatingCount);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchByUser", searchByUser);
        request.setAttribute("searchByBook", searchByBook);

        request.getRequestDispatcher("/score-list.jsp").forward(request, response);
    }
}