package controller;

import Service.UserService;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        // 初始化 UserService
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户提交的注册信息
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // 确保密码和确认密码一致
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "密码和确认密码不一致！");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try {
            // 检查用户名是否已经存在
            User existingUser = userService.getUserByUsername(username);
            if (existingUser != null) {
                request.setAttribute("errorMessage", "用户名已存在，请选择其他用户名！");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // 获取最大用户ID，生成新的user_id（UXXXX）
            Long maxUserId = userService.getMaxUserId();  // 获取最大ID
            String userId = "U" + String.format("%04d", maxUserId + 1);  // 格式化为 UXXXX 格式

            // 创建用户对象并设置信息
            User user = new User();
            user.setUser_id(userId);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            // 调用UserService注册用户
            int result = userService.addUser(user);

            if (result > 0) {
                // 注册成功，重定向到登录页面
                request.setAttribute("successMessage", "注册成功，请登录！");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // 注册失败，返回注册页面
                request.setAttribute("errorMessage", "注册失败，请稍后再试！");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // 错误页面
        }
    }
}
