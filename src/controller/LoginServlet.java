package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户输入的用户名和密码
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 模拟用户验证逻辑（实际应查询数据库）
        if ("admin".equals(username) && "123456".equals(password)) {
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
