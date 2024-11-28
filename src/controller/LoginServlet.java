package controller;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户输入的用户名和密码
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isValidUser = false;

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            Object result = runner.query(sql, new ScalarHandler<>(), username, password);
            if (result != null) {
                // 如果查询结果不为空，则表示用户存在
                isValidUser = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (isValidUser) {
            // 登录成功，将用户信息保存到 session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // 重定向到主页面
            response.sendRedirect("main.jsp");
        } else {
            // 登录失败，返回登录页面并显示错误信息
            request.setAttribute("errorMessage", "用户名或密码错误，请重新登录！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 如果尝试GET方法，重定向到登录页面
        response.sendRedirect("login.jsp");
    }
}

