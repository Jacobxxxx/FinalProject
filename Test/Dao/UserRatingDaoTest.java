import Dao.UserRatingDao;
import model.UserRating;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRatingDaoTest {

    private static UserRatingDao userRatingDao;

    @BeforeAll
    public static void setUp() {
        userRatingDao = new UserRatingDao();
    }

    // 测试添加用户评分
    @Test
    @Order(1)
    public void testAddUserRating() throws SQLException {
        // 创建一个新的 UserRating 实例
        UserRating userRating = new UserRating();
        userRating.setUser_id("U0001");
        userRating.setBook_id(1);
        userRating.setRating(8.5);
        userRating.setRating_time(new Timestamp(System.currentTimeMillis()));

        // 添加用户评分
        int rowsAffected = userRatingDao.addUserRating(userRating);

        System.out.println("添加的评分记录影响的行数: " + rowsAffected);
        assertEquals(1, rowsAffected, "添加评分失败，影响的行数不对");

        // 确认评分记录是否已成功添加
        UserRating addedUserRating = userRatingDao.getUserRatingByUserIdAndBookId("U0001", 1);
        assertNotNull(addedUserRating, "添加的评分记录不存在");
        System.out.println("添加的评分记录 ID: " + addedUserRating.getId());
        assertEquals(userRating.getRating(), addedUserRating.getRating(), "评分值不匹配");
    }

    // 测试查询所有用户评分
    @Test
    @Order(2)
    public void testGetAllUserRatings() throws SQLException {
        List<UserRating> ratings = userRatingDao.getAllUserRatings();
        assertNotNull(ratings, "用户评分列表为空");
        System.out.println("所有用户评分记录数量: " + ratings.size());
    }

    // 测试查询指定用户评分
    @Test
    @Order(3)
    public void testGetUserRatingById() throws SQLException {
        UserRating rating = userRatingDao.getUserRatingById(1);
        assertNotNull(rating, "查询不到指定ID的评分记录");
        System.out.println("查询到的评分记录: " + rating.getId() + ", 用户: " + rating.getUser_id() + ", 图书ID: " + rating.getBook_id());
    }

    // 测试删除用户评分
    @Test
    @Order(4)
    public void testDeleteUserRating() throws SQLException {
        // 获取最后一条评分记录的 ID
        int maxId = userRatingDao.getMaxUserRatingId();

        // 删除刚才添加的评分记录
        int rowsAffected = userRatingDao.deleteUserRating(maxId);
        assertEquals(1, rowsAffected, "删除评分记录失败");

        // 确保该评分记录被删除
        UserRating deletedRating = userRatingDao.getUserRatingById(maxId);
        assertNull(deletedRating, "评分记录未被删除");
        System.out.println("成功删除评分记录 ID: " + maxId);
    }

    // 测试分页获取用户评分
    @Test
    @Order(5)
    public void testGetUserRatingsByPage() throws SQLException {
        List<UserRating> ratings = userRatingDao.getUserRatingsByPage(1, 10);
        assertNotNull(ratings, "分页查询用户评分失败");
        System.out.println("分页查询到的评分记录数量: " + ratings.size());
    }

    // 测试获取用户评分总数
    @Test
    @Order(6)
    public void testGetUserRatingCount() throws SQLException {
        long count = userRatingDao.getUserRatingCount();
        System.out.println("用户评分总数: " + count);
        assertTrue(count > 0, "用户评分总数应该大于0");
    }

    // 测试更新用户评分
    @Test
    @Order(7)
    public void testUpdateUserRating() throws SQLException {
        // 获取某个评分记录
        UserRating rating = userRatingDao.getUserRatingById(1);
        rating.setRating(9.0);  // 更新评分

        // 更新评分
        int rowsAffected = userRatingDao.updateUserRating(rating);
        assertEquals(1, rowsAffected, "更新评分失败");

        // 确保评分更新
        UserRating updatedRating = userRatingDao.getUserRatingById(1);
        assertEquals(9.0, updatedRating.getRating(), "评分未更新正确");
        System.out.println("成功更新评分记录 ID: " + updatedRating.getId());
    }
}
