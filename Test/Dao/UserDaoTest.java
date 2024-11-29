package Dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private UserDao userdao;
    @BeforeEach
    void setUp() {
        UserDao userdao = new UserDao();
        System.out.println("测试环境初始化...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("测试完成，清理环境...");
    }

    @Test
    void recordBrowseAction() {
    }

    @Test
    void recordFavoriteAction() {
    }

    @Test
    void recordRatingAction() {
    }

    @Test
    void cancelFavoriteAction() {
    }

    @Test
    void getFavoriteBooks() {
    }
}