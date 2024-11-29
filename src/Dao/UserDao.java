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
}
