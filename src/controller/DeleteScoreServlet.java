package controller;

import Service.UserRatingService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet("/delete-score")
public class DeleteScoreServlet extends HttpServlet {
    private UserRatingService userratingservice;

    @Override
    public void init() {
        userratingservice = new UserRatingService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String id = request.getParameter("id");
        String searchByUser = request.getParameter("searchByUser");
        String searchByBook = request.getParameter("searchByBook");
        String page = request.getParameter("page");
        try {
            userratingservice.deleteUserRating(Integer.parseInt(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String redirectUrl = request.getContextPath() + "/score-list";
        if (searchByUser != null || searchByBook != null || page != null) {
            redirectUrl += "?";
            if (page != null) {
                redirectUrl += "page=" + URLEncoder.encode(page, StandardCharsets.UTF_8.toString()) + "&";
            }
            if (searchByUser != null) {
                redirectUrl += "searchByUser=" + URLEncoder.encode(searchByUser, StandardCharsets.UTF_8.toString()) + "&";
            }
            if (searchByBook != null) {
                redirectUrl += "searchByBook=" + URLEncoder.encode(searchByBook, StandardCharsets.UTF_8.toString());
            }
        }

        response.sendRedirect(redirectUrl);
    }
    
}
