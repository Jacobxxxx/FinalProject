package Dao;

import model.UserAction;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserActionDao {

    private QueryRunner runner = new QueryRunner();

    // 获取所有用户行为记录
    public List<UserAction> getAllUserActions() throws SQLException {
        String sql = "SELECT * FROM user_actions";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanListHandler<>(UserAction.class));
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 根据ID获取指定用户行为记录
    public UserAction getUserActionById(int id) throws SQLException {
        String sql = "SELECT * FROM user_actions WHERE id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanHandler<>(UserAction.class), id);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 获取用户行为记录总数
    public long getUserActionCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM user_actions";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new ScalarHandler<>());
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 获取最大 ID，用于插入新的记录
    public int getMaxUserActionId() throws SQLException {
        String sql = "SELECT MAX(id) FROM user_actions";
        Connection conn = DataSourceUtils.getConnection();
        try {
            Integer maxId = runner.query(conn, sql, new ScalarHandler<>());
            return (maxId == null) ? 0 : maxId;
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 添加用户行为记录
    public int addUserAction(UserAction userAction) throws SQLException {
        // 获取最大ID+1
        int newId = getMaxUserActionId() + 1;
        String sql = "INSERT INTO user_actions (id, user_id, book_id, browse, favorite) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql, newId, userAction.getUser_id(), userAction.getBook_id(), userAction.getBrowse(), userAction.getFavorite());
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 更新用户行为记录
    public int updateUserAction(UserAction userAction) throws SQLException {
        String sql = "UPDATE user_actions SET user_id = ?, book_id = ?, browse = ?, favorite = ? WHERE id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql, userAction.getUser_id(), userAction.getBook_id(), userAction.getBrowse(), userAction.getFavorite(), userAction.getId());
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 删除指定ID的用户行为记录
    public int deleteUserAction(int id) throws SQLException {
        String sql = "DELETE FROM user_actions WHERE id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql, id);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 分页获取用户行为记录列表
    public List<UserAction> getUserActionsByPage(int pageNo, int pageSize) throws SQLException {
        String sql = "SELECT * FROM user_actions LIMIT ?, ?";
        Connection conn = DataSourceUtils.getConnection();
        int offset = (pageNo - 1) * pageSize;
        try {
            return runner.query(conn, sql, new BeanListHandler<>(UserAction.class), offset, pageSize);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 根据用户ID和书籍ID获取用户行为记录
    public UserAction getUserActionByUserIdAndBookId(String userId, int bookId) throws SQLException {
        String sql = "SELECT * FROM user_actions WHERE user_id = ? AND book_id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanHandler<>(UserAction.class), userId, bookId);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    public List<UserAction> getUserActionsByUserIdAndFavorite(String userId, int favorite) throws SQLException {
        String sql = "SELECT * FROM user_actions WHERE user_id = ? AND favorite = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanListHandler<>(UserAction.class), userId, favorite);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

}
