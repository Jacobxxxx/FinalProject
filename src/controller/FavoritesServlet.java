package controller;

import Service.UserActionService;
import model.UserAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/favorites")
public class FavoritesServlet extends HttpServlet {

    private UserActionService userActionService;

    @Override
    public void init() throws ServletException {
        // 初始化UserActionService
        userActionService = new UserActionService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前用户的ID（假设用户的ID存在session中）
        String userId = (String) request.getSession().getAttribute("username");

        if (userId == null) {
            // 如果用户没有登录，重定向到登录页面
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 获取用户的收藏列表
            List<UserAction> userFavorites = userActionService.getUserFavorites(userId);

            // 将收藏列表存储到请求属性中
            request.setAttribute("userFavorites", userFavorites);

            // 转发到收藏列表页面
            request.getRequestDispatcher("favorites.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // 错误页面
        }
    }
}
