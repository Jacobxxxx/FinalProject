package controller;

import org.apache.commons.dbutils.QueryRunner;
import utils.DataSourceUtils;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());  // 正确初始化 QueryRunner

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户输入的用户名和密码
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValidUser = false;
        String userId = null;

        // 检查是否为管理员账户
        if ("admin".equals(username) && "123456".equals(password)) {
            // 管理员账户直接跳转到管理员页面
            HttpSession session = request.getSession();
            session.setAttribute("userId", "admin");  // 标记管理员登录
            response.sendRedirect("index.jsp");  // 重定向到管理员页面
            return;
        }

        // 普通用户登录验证逻辑
        String sql = "SELECT user_id FROM users WHERE username = ? AND password = ?";

        try {
            // 使用 ScalarHandler 获取单一值（user_id）
            userId = runner.query(sql, new ScalarHandler<String>(), username, password);
            System.out.println(userId);  // 检查返回的 userId 是否为 null
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (userId != null) {
            // 如果 userId 不为 null，表示登录成功
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);  // 将 userId 存储到 session 中
            response.sendRedirect("main.jsp");  // 重定向到普通用户主页面
        } else {
            // 如果 userId 为 null，表示登录失败
            request.setAttribute("errorMessage", "用户名或密码错误，请重新登录！");
            request.getRequestDispatcher("login.jsp").forward(request, response);  // 返回登录页面
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 默认重定向到普通用户视图页面
        response.sendRedirect("viewer.jsp");
    }
}
