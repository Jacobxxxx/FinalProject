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

// 更新后的代码，使用 ScalarHandler 获取 user_id
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
            response.sendRedirect("main.jsp");  // 重定向到主页面
        } else {
            // 如果 userId 为 null，表示登录失败
            request.setAttribute("errorMessage", "用户名或密码错误，请重新登录！");
            request.getRequestDispatcher("login.jsp").forward(request, response);  // 返回登录页面
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 如果尝试GET方法，重定向到登录页面
        response.sendRedirect("viewer.jsp");
    }
}
