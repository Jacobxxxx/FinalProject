package Service;
import Service.UserActionService;
import model.UserAction;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserActionServiceTest {

    private static UserActionService userActionService;

    @BeforeAll
    public static void setUp() {
        userActionService = new UserActionService();
    }

    // 测试添加用户行为
    @Test
    @Order(1)
    public void testAddUserAction() throws SQLException {
        // 创建一个新的 UserAction 实例
        UserAction userAction = new UserAction();
        userAction.setUser_id("U0002");
        userAction.setBook_id(2);
        userAction.setBrowse(1);
        userAction.setFavorite(1);

        // 添加用户行为记录
        int rowsAffected = userActionService.addUserAction(userAction);

        System.out.println("添加的用户行为记录影响的行数: " + rowsAffected);
        assertEquals(1, rowsAffected, "添加用户行为失败，影响的行数不对");

        // 确认记录是否已成功添加
        UserAction addedUserAction = userActionService.getUserActionByUserIdAndBookId("U0002", 2);
        assertNotNull(addedUserAction, "添加的用户行为记录不存在");
        System.out.println("添加的用户行为记录 ID: " + addedUserAction.getId());
        assertEquals(userAction.getUser_id(), addedUserAction.getUser_id(), "用户ID不匹配");
        assertEquals(userAction.getBook_id(), addedUserAction.getBook_id(), "书籍ID不匹配");
    }

    // 测试查询所有用户行为记录
    @Test
    @Order(2)
    public void testGetAllUserActions() throws SQLException {
        List<UserAction> actions = userActionService.getAllUserActions();
        assertNotNull(actions, "用户行为列表为空");
        System.out.println("所有用户行为记录数量: " + actions.size());
    }

    // 测试查询指定用户行为记录
    @Test
    @Order(3)
    public void testGetUserActionById() throws SQLException {
        UserAction action = userActionService.getUserActionById(1);
        assertNotNull(action, "查询不到指定ID的用户行为记录");
        System.out.println("查询到的用户行为记录: " + action.getId() + ", 用户: " + action.getUser_id() + ", 图书ID: " + action.getBook_id());
    }

    // 测试更新用户行为记录
    @Test
    @Order(4)
    public void testUpdateUserAction() throws SQLException {
        // 获取某个行为记录
        UserAction action = userActionService.getUserActionById(1);
        assertNotNull(action, "获取的用户行为记录为空");
        System.out.println("准备更新用户行为记录 ID: " + action.getId());

        action.setBrowse(0); // 更新浏览状态
        action.setFavorite(0); // 更新收藏状态

        // 更新行为记录
        int rowsAffected = userActionService.updateUserAction(action);
        assertEquals(1, rowsAffected, "更新用户行为失败");

        // 确保行为记录更新
        UserAction updatedAction = userActionService.getUserActionById(1);
        assertEquals(0, updatedAction.getBrowse(), "浏览状态未更新正确");
        assertEquals(0, updatedAction.getFavorite(), "收藏状态未更新正确");
        System.out.println("成功更新用户行为记录 ID: " + updatedAction.getId());
    }

    // 测试删除用户行为记录
    @Test
    @Order(5)
    public void testDeleteUserAction() throws SQLException {
        // 获取最后一条行为记录的 ID
        int maxId = userActionService.getMaxUserActionId();

        // 删除刚才添加的行为记录
        int rowsAffected = userActionService.deleteUserAction(maxId);
        assertEquals(1, rowsAffected, "删除用户行为记录失败");

        // 确保该行为记录被删除
        UserAction deletedAction = userActionService.getUserActionById(maxId);
        assertNull(deletedAction, "用户行为记录未被删除");
        System.out.println("成功删除用户行为记录 ID: " + maxId);
    }

    // 测试分页获取用户行为记录
    @Test
    @Order(6)
    public void testGetUserActionsByPage() throws SQLException {
        List<UserAction> actions = userActionService.getUserActionsByPage(1, 10);
        assertNotNull(actions, "分页查询用户行为失败");
        System.out.println("分页查询到的用户行为记录数量: " + actions.size());
    }

    // 测试获取用户行为记录总数
    @Test
    @Order(7)
    public void testGetUserActionCount() throws SQLException {
        long count = userActionService.getUserActionCount();
        System.out.println("用户行为记录总数: " + count);
        assertTrue(count > 0, "用户行为记录总数应该大于0");
    }

    // 测试通过用户ID和书籍ID查询用户行为记录
    @Test
    @Order(8)
    public void testGetUserActionByUserIdAndBookId() throws SQLException {
        UserAction action = userActionService.getUserActionByUserIdAndBookId("U0002", 2);
        assertNotNull(action, "查询不到指定用户和书籍的行为记录");
        System.out.println("查询到的用户行为记录: 用户ID: " + action.getUser_id() + ", 图书ID: " + action.getBook_id() + ", 浏览: " + action.getBrowse() + ", 收藏: " + action.getFavorite());
    }
}
