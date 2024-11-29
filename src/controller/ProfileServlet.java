package controller;

import model.User;
import Service.UserService;  // 假设你有一个服务层来处理用户信息的业务逻辑
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UserService userService; // 假设你有一个 UserService 来处理业务逻辑

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();  // 初始化服务层
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前用户的 userId
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        // 如果用户没有登录（userId 为 null），重定向到登录页面
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 使用 UserService 获取用户信息
        try {
            User user = userService.getUserById(userId);
            if (user != null) {
                // 将用户信息传递到 JSP 页面
                request.setAttribute("user", user);
                // 转发到 profile.jsp 页面
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            } else {
                // 如果用户信息不存在，重定向到错误页面
                response.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");  // 出错时重定向到错误页面
        }
    }
}
