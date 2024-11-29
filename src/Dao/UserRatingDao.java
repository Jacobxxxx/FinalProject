package Dao;

import model.UserRating;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserRatingDao {

    private QueryRunner runner = new QueryRunner();

    // 获取所有用户评分列表
    public List<UserRating> getAllUserRatings() throws SQLException {
        String sql = "SELECT * FROM user_ratings";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanListHandler<>(UserRating.class));
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 根据ID获取指定用户评分信息
    public UserRating getUserRatingById(int id) throws SQLException {
        String sql = "SELECT * FROM user_ratings WHERE id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanHandler<>(UserRating.class), id);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 获取用户评分总记录数
    public long getUserRatingCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM user_ratings";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new ScalarHandler<>());
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 获取最新的ID（最大ID），用于新数据插入时使用
    public int getMaxUserRatingId() throws SQLException {
        String sql = "SELECT MAX(id) FROM user_ratings";
        Connection conn = DataSourceUtils.getConnection();
        try {
            Integer maxId = runner.query(conn, sql, new ScalarHandler<>());
            return (maxId == null) ? 0 : maxId;
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 添加用户评分
    public int addUserRating(UserRating userRating) throws SQLException {
        // 获取最大ID+1
        int newId = getMaxUserRatingId() + 1;
        String sql = "INSERT INTO user_ratings (id, user_id, book_id, rating, rating_time) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql, newId, userRating.getUser_id(), userRating.getBook_id(), userRating.getRating(), userRating.getRating_time());
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 更新用户评分
    public int updateUserRating(UserRating userRating) throws SQLException {
        String sql = "UPDATE user_ratings SET user_id = ?, book_id = ?, rating = ?, rating_time = ? WHERE id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql, userRating.getUser_id(), userRating.getBook_id(), userRating.getRating(), userRating.getRating_time(), userRating.getId());
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 删除指定ID的用户评分
    public int deleteUserRating(int id) throws SQLException {
        String sql = "DELETE FROM user_ratings WHERE id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql, id);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 分页获取用户评分列表
    public List<UserRating> getUserRatingsByPage(int pageNo, int pageSize) throws SQLException {
        String sql = "SELECT * FROM user_ratings LIMIT ?, ?";
        Connection conn = DataSourceUtils.getConnection();
        int offset = (pageNo - 1) * pageSize;
        try {
            return runner.query(conn, sql, new BeanListHandler<>(UserRating.class), offset, pageSize);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 获取特定用户和书籍的评分
    public UserRating getUserRatingByUserIdAndBookId(String userId, int bookId) throws SQLException {
        String sql = "SELECT * FROM user_ratings WHERE user_id = ? AND book_id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanHandler<>(UserRating.class), userId, bookId);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }
}
