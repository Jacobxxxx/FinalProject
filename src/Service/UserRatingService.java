package Service;

import Dao.UserRatingDao;
import model.UserRating;

import java.sql.SQLException;
import java.util.List;

public class UserRatingService {

    private UserRatingDao userRatingDao;

    public UserRatingService() {
        this.userRatingDao = new UserRatingDao();
    }

    // 获取所有用户评分
    public List<UserRating> getAllUserRatings() throws SQLException {
        return userRatingDao.getAllUserRatings();
    }

    // 根据ID获取指定用户评分
    public UserRating getUserRatingById(int id) throws SQLException {
        return userRatingDao.getUserRatingById(id);
    }

    // 获取用户评分总记录数
    public long getUserRatingCount() throws SQLException {
        return userRatingDao.getUserRatingCount();
    }

    // 获取最新的ID，用于插入新记录时
    public int getMaxUserRatingId() throws SQLException {
        return userRatingDao.getMaxUserRatingId();
    }

    // 添加用户评分
    public int addUserRating(UserRating userRating) throws SQLException {
        return userRatingDao.addUserRating(userRating);
    }

    // 更新用户评分
    public int updateUserRating(UserRating userRating) throws SQLException {
        return userRatingDao.updateUserRating(userRating);
    }

    // 删除用户评分
    public int deleteUserRating(int id) throws SQLException {
        return userRatingDao.deleteUserRating(id);
    }

    // 分页获取用户评分列表
    public List<UserRating> getUserRatingsByPage(int pageNo, int pageSize) throws SQLException {
        return userRatingDao.getUserRatingsByPage(pageNo, pageSize);
    }

    // 获取特定用户和书籍的评分
    public UserRating getUserRatingByUserIdAndBookId(String userId, int bookId) throws SQLException {
        return userRatingDao.getUserRatingByUserIdAndBookId(userId, bookId);
    }
}
