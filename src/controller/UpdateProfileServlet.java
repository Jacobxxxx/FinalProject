package controller;

import Service.UserService;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService(); // 初始化 UserService
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从 session 中获取当前登录用户的 user_id
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        // 如果没有登录，重定向到登录页面
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 获取用户提交的个人信息
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 创建一个 User 对象，封装用户提交的信息
        User user = new User();
        user.setUser_id(userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        try {
            // 调用 UserService 来更新用户信息
            int result = userService.updateUser(user);

            if (result > 0) {
                // 更新成功，设置成功提示信息
                request.setAttribute("message", "修改成功！");
            } else {
                // 更新失败，设置失败提示信息
                request.setAttribute("errorMessage", "更新失败，请稍后再试！");
            }

            // 将用户信息传递到 JSP 页面
            request.setAttribute("user", user);
            // 转发到 profile.jsp 页面
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
