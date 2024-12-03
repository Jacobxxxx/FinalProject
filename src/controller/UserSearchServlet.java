package controller;

import Service.UserService;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/user-search")
public class UserSearchServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取搜索关键词和分页参数
        String keyword = request.getParameter("keyword");
        keyword = (keyword == null) ? "" : keyword;

        int currentPage = 1;
        int pageSize = 10;

        try {
            String pageStr = request.getParameter("page");
            if (pageStr != null) {
                currentPage = Integer.parseInt(pageStr);
            }

            // 获取用户总数
            long totalUsers = userService.countUsersByKeyword(keyword);
            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

            // 获取分页用户数据
            List<User> users = userService.searchUsersByKeywordWithPagination(keyword, currentPage, pageSize);

            // 设置请求属性
            request.setAttribute("users", users);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("keyword", keyword);
            request.setAttribute("totalUsers", totalUsers);

            // 转发到搜索结果 JSP
            request.getRequestDispatcher("user-search.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 将 POST 请求重定向到 GET 请求，避免代码重复
        doGet(request, response);
    }
}
