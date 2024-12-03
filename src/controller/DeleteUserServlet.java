package controller;

import Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        // 初始化 UserService
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求中的用户ID
        String userId = request.getParameter("id");

        if (userId == null || userId.trim().isEmpty()) {
            // 如果用户ID为空或无效，返回错误信息
            request.setAttribute("errorMessage", "用户ID不能为空或无效！");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            // 调用服务层删除用户
            int rowsAffected = userService.deleteUser(userId);

            if (rowsAffected > 0) {
                response.sendRedirect("user-list.jsp?message=success");
            } else {
                request.setAttribute("errorMessage", "删除用户失败，可能用户不存在！");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // 如果发生数据库异常，返回错误信息
            request.setAttribute("errorMessage", "服务器发生错误，请稍后再试！");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 如果尝试 POST 请求，直接调用 doGet 方法处理
        doGet(request, response);
    }
}
