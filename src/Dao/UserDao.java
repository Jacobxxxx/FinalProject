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
import model.User;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class UserDao {

    private QueryRunner runner = new QueryRunner();

    // 获取所有用户
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanListHandler<>(User.class));
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 根据ID获取用户信息
    public User getUserById(String userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanHandler<>(User.class), userId);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 获取用户总记录数
    public long getUserCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new ScalarHandler<>());
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 添加用户
    public int addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (user_id, username, password, email) VALUES (?, ?, ?, ?)";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql, user.getUser_id(), user.getUsername(), user.getPassword(), user.getEmail());
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 更新用户信息
    public int updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ?, email = ? WHERE user_id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getUser_id());
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 删除用户
    public int deleteUser(String userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql, userId);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 分页获取用户列表
    public List<User> getUsersByPage(int pageNo, int pageSize) throws SQLException {
        String sql = "SELECT * FROM users LIMIT ?, ?";
        Connection conn = DataSourceUtils.getConnection();
        int offset = (pageNo - 1) * pageSize;
        try {
            return runner.query(conn, sql, new BeanListHandler<>(User.class), offset, pageSize);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 根据用户名和密码查询用户
    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanHandler<>(User.class), username, password);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

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
