package Service;

import Dao.UserActionDao;
import model.UserAction;

import java.sql.SQLException;
import java.util.List;

public class UserActionService {

    private UserActionDao userActionDao;

    public UserActionService() {
        this.userActionDao = new UserActionDao();
    }

    // 获取所有用户行为记录
    public List<UserAction> getAllUserActions() throws SQLException {
        return userActionDao.getAllUserActions();
    }

    // 根据ID获取指定用户行为记录
    public UserAction getUserActionById(int id) throws SQLException {
        return userActionDao.getUserActionById(id);
    }

    // 获取用户行为记录总数
    public long getUserActionCount() throws SQLException {
        return userActionDao.getUserActionCount();
    }

    // 获取最新的 ID（最大 ID），用于新数据插入时使用
    public int getMaxUserActionId() throws SQLException {
        return userActionDao.getMaxUserActionId();
    }

    // 添加用户行为记录
    public int addUserAction(UserAction userAction) throws SQLException {
        return userActionDao.addUserAction(userAction);
    }

    // 更新用户行为记录
    public int updateUserAction(UserAction userAction) throws SQLException {
        return userActionDao.updateUserAction(userAction);
    }

    // 删除指定ID的用户行为记录
    public int deleteUserAction(int id) throws SQLException {
        return userActionDao.deleteUserAction(id);
    }

    // 分页获取用户行为记录
    public List<UserAction> getUserActionsByPage(int pageNo, int pageSize) throws SQLException {
        return userActionDao.getUserActionsByPage(pageNo, pageSize);
    }

    // 根据用户ID和书籍ID获取用户行为记录
    public UserAction getUserActionByUserIdAndBookId(String userId, int bookId) throws SQLException {
        return userActionDao.getUserActionByUserIdAndBookId(userId, bookId);
    }
}
