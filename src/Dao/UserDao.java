package Dao;

import model.Book;
import model.UserRating;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DataSourceUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    
    QueryRunner runner = new QueryRunner();

    // 记录用户浏览行为
    public void recordBrowseAction(String userId, int bookId) throws SQLException {
        try (Connection connection = DataSourceUtils.getConnection()) {
            String sql = "INSERT INTO user_actions (user_id, book_id, browse) VALUES (?, ?, 1) " +
                         "ON DUPLICATE KEY UPDATE browse = 1";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userId);
                statement.setInt(2, bookId);
                statement.executeUpdate();
            }
        }
    }

    // 记录用户收藏行为
    public void recordFavoriteAction(String userId, int bookId) throws SQLException {
        try (Connection connection = DataSourceUtils.getConnection()) {
            String sql = "UPDATE user_actions SET favorite = 1 WHERE user_id = ? AND book_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userId);
                statement.setInt(2, bookId);
                statement.executeUpdate();
            }
        }
    }

    // 记录用户评分行为
    public void recordRatingAction(String userId, int bookId, double rating) throws SQLException {
        try (Connection connection = DataSourceUtils.getConnection()) {
            String sql = "INSERT INTO user_ratings (user_id, book_id, rating) VALUES (?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE rating = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userId);
                statement.setInt(2, bookId);
                statement.setDouble(3, rating);
                statement.setDouble(4, rating);
                statement.executeUpdate();
            }
        }
    }

    // 取消用户收藏行为
    public void cancelFavoriteAction(String userId, int bookId) throws SQLException {
        try (Connection connection = DataSourceUtils.getConnection()) {
            String sql = "UPDATE user_actions SET favorite = 0 WHERE user_id = ? AND book_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userId);
                statement.setInt(2, bookId);
                statement.executeUpdate();
            }
        }
    }

     // 获取用户所收藏的书籍
     public List<Book> getFavoriteBooks(String userId) throws SQLException {
        String sql = "SELECT b.* FROM books b " +
                     "JOIN user_actions ua ON b.id = ua.book_id " +
                     "WHERE ua.user_id = ? AND ua.favorite = 1";
        Connection conn = DataSourceUtils.getConnection();
        return runner.query(conn, sql, new BeanListHandler<>(Book.class), userId);
    }

    // 获取用户评分
    public List<UserRating> getUserRatings(String userId) throws SQLException {
        String sql = "SELECT * FROM user_ratings WHERE user_id = ?";
        Connection conn = DataSourceUtils.getConnection();
        return runner.query(conn, sql, new BeanListHandler<>(UserRating.class), userId);
    }
}
