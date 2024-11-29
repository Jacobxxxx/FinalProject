package Dao;
import Dao.UserActionDao;
import model.UserAction;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserActionDaoTest {

    private static UserActionDao userActionDao;

    @BeforeAll
    public static void setUp() {
        userActionDao = new UserActionDao();
    }

    // 测试添加用户行为记录
    @Test
    @Order(1)
    public void testAddUserAction() throws SQLException {
        // 创建一个新的 UserAction 实例
        UserAction userAction = new UserAction();
        userAction.setUser_id("U0001");
        userAction.setBook_id(1);
        userAction.setBrowse(1);
        userAction.setFavorite(1);

        // 添加用户行为记录
        int rowsAffected = userActionDao.addUserAction(userAction);

        System.out.println("添加的用户行为记录影响的行数: " + rowsAffected);
        assertEquals(1, rowsAffected, "添加用户行为失败，影响的行数不对");

        // 确认记录是否已成功添加
        UserAction addedUserAction = userActionDao.getUserActionByUserIdAndBookId("U0001", 1);
        assertNotNull(addedUserAction, "添加的用户行为记录不存在");
        System.out.println("添加的用户行为记录 ID: " + addedUserAction.getId());
        assertEquals(userAction.getUser_id(), addedUserAction.getUser_id(), "用户ID不匹配");
        assertEquals(userAction.getBook_id(), addedUserAction.getBook_id(), "书籍ID不匹配");
    }

    // 测试查询所有用户行为记录
    @Test
    @Order(2)
    public void testGetAllUserActions() throws SQLException {
        List<UserAction> actions = userActionDao.getAllUserActions();
        assertNotNull(actions, "用户行为列表为空");
        System.out.println("所有用户行为记录数量: " + actions.size());
    }

    // 测试查询指定用户行为记录
    @Test
    @Order(3)
    public void testGetUserActionById() throws SQLException {
        UserAction action = userActionDao.getUserActionById(1);
        assertNotNull(action, "查询不到指定ID的用户行为记录");
        System.out.println("查询到的用户行为记录: " + action.getId() + ", 用户: " + action.getUser_id() + ", 图书ID: " + action.getBook_id());
    }

    // 测试删除用户行为记录
    @Test
    @Order(4)
    public void testDeleteUserAction() throws SQLException {
        // 获取最后一条行为记录的 ID
        int maxId = userActionDao.getMaxUserActionId();

        // 删除刚才添加的行为记录
        int rowsAffected = userActionDao.deleteUserAction(maxId);
        assertEquals(1, rowsAffected, "删除用户行为记录失败");

        // 确保该行为记录被删除
        UserAction deletedAction = userActionDao.getUserActionById(maxId);
        assertNull(deletedAction, "用户行为记录未被删除");
        System.out.println("成功删除用户行为记录 ID: " + maxId);
    }

    // 测试分页获取用户行为记录
    @Test
    @Order(5)
    public void testGetUserActionsByPage() throws SQLException {
        List<UserAction> actions = userActionDao.getUserActionsByPage(1, 10);
        assertNotNull(actions, "分页查询用户行为失败");
        System.out.println("分页查询到的用户行为记录数量: " + actions.size());
    }

    // 测试获取用户行为记录总数
    @Test
    @Order(6)
    public void testGetUserActionCount() throws SQLException {
        long count = userActionDao.getUserActionCount();
        System.out.println("用户行为记录总数: " + count);
        assertTrue(count > 0, "用户行为记录总数应该大于0");
    }

    // 测试更新用户行为记录
    @Test
    @Order(7)
    public void testUpdateUserAction() throws SQLException {
        // 获取某个行为记录
        UserAction action = userActionDao.getUserActionById(1);
        action.setBrowse(0);  // 更新浏览状态

        // 更新行为记录
        int rowsAffected = userActionDao.updateUserAction(action);
        assertEquals(1, rowsAffected, "更新用户行为失败");

        // 确保行为记录更新
        UserAction updatedAction = userActionDao.getUserActionById(1);
        assertEquals(0, updatedAction.getBrowse(), "浏览状态未更新正确");
        System.out.println("成功更新用户行为记录 ID: " + updatedAction.getId());
    }
}
