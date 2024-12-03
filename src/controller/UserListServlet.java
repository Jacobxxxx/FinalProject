package controller;

import Service.UserService;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageSize = 10;
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        try {
            long totalUsers = userService.getUserCount();
            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);
            List<User> users = userService.getUsersByPage(currentPage, pageSize);

            request.setAttribute("users", users);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalUsers", totalUsers);

            request.getRequestDispatcher("Admin/user-list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
