package Service;

import Dao.UserDao;
import model.User;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    // 获取所有用户
    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    // 根据ID获取用户
    public User getUserById(String userId) throws SQLException {
        return userDao.getUserById(userId);
    }

    // 根据用户名和密码获取用户
    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        return userDao.getUserByUsernameAndPassword(username, password);
    }

    // 添加用户
    public int addUser(User user) throws SQLException {
        return userDao.addUser(user);
    }

    // 更新用户
    public int updateUser(User user) throws SQLException {
        return userDao.updateUser(user);
    }

    // 删除用户
    public int deleteUser(String userId) throws SQLException {
        return userDao.deleteUser(userId);
    }

    // 分页查询用户
    public List<User> getUsersByPage(int pageNo, int pageSize) throws SQLException {
        return userDao.getUsersByPage(pageNo, pageSize);
    }

    // 获取用户总数
    public long getUserCount() throws SQLException {
        return userDao.getUserCount();
    }
}
