package Dao;

import model.Book;
import org.junit.jupiter.api.*;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDaoTest {

    private BookDao bookDao;

    @BeforeAll
    public void setUp() {
        bookDao = new BookDao();
        System.out.println("测试环境初始化...");
    }

    @AfterAll
    public void tearDown() {
        System.out.println("测试完成，清理环境...");
    }

    @Test
    @Order(1)
    public void testGetAllBooks() {
        System.out.println("测试: 获取所有图书信息");
        try {
            List<Book> allBooks = bookDao.getAllBooks();
            assertNotNull(allBooks, "获取的图书列表不应为空");
            System.out.println("图书总数: " + allBooks.size());
            allBooks.forEach(System.out::println);
        } catch (SQLException e) {
            fail("获取所有图书信息失败: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void testGetBookById() {
        System.out.println("测试: 根据ID获取图书信息");
        int testId = 1; // 假设ID为1的图书存在
        try {
            Book book = bookDao.getBookById(testId);
            assertNotNull(book, "获取到的图书信息不应为空");
            System.out.println("获取到的图书: " + book);
        } catch (SQLException e) {
            fail("根据ID获取图书信息失败: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void testGetBookCount() {
        System.out.println("测试: 获取图书总记录数");
        try {
            long count = bookDao.getBookCount();
            assertTrue(count >= 0, "图书总记录数应为非负数");
            System.out.println("图书总记录数: " + count);
        } catch (SQLException e) {
            fail("获取图书总记录数失败: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    public void testAddBook() {
        System.out.println("测试: 添加图书");
        Book newBook = new Book();
        newBook.setBook_url("https://example.com/test-book");
        newBook.setBook_name("测试图书");
        newBook.setAuthor("测试作者");
        newBook.setCover_image_url("https://example.com/image.jpg");
        newBook.setPublisher("测试出版社");
        newBook.setPublish_year("2024");
        newBook.setRating(8.5);
        newBook.setRating_count(100);
        newBook.setDescription("这是一个测试用的图书");
        newBook.setAuthor_description("测试作者简介");
        newBook.setTag("测试");

        try {
            int rowsAffected = bookDao.addBook(newBook);
            assertEquals(1, rowsAffected, "应成功插入一条记录");
            System.out.println("添加图书成功, 影响行数: " + rowsAffected);
        } catch (SQLException e) {
            fail("添加图书失败: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    public void testUpdateBook() {
        System.out.println("测试: 更新图书信息");
        try {
            Book book = bookDao.getBookById(1); // 假设ID为1的图书存在
            assertNotNull(book, "要更新的图书应存在");
            book.setBook_name("更新后的图书名称");
            book.setRating(9.0);

            int rowsAffected = bookDao.updateBook(book);
            assertEquals(1, rowsAffected, "应成功更新一条记录");
            System.out.println("更新图书成功, 影响行数: " + rowsAffected);
        } catch (SQLException e) {
            fail("更新图书信息失败: " + e.getMessage());
        }
    }

    @Test
    @Order(6)
    public void testDeleteBook() {
        System.out.println("测试: 删除图书");
        int testId = 1; // 假设ID为1的图书存在
        try {
            int rowsAffected = bookDao.deleteBook(testId);
            assertTrue(rowsAffected >= 0, "删除记录时影响行数应为非负数");
            System.out.println("删除图书成功, 影响行数: " + rowsAffected);
        } catch (SQLException e) {
            fail("删除图书失败: " + e.getMessage());
        }
    }

    @Test
    @Order(7)
    public void testGetBooksByPage() {
        System.out.println("测试: 分页获取图书列表");
        int pageNo = 1;
        int pageSize = 5;
        try {
            List<Book> booksByPage = bookDao.getBooksByPage(pageNo, pageSize);
            assertNotNull(booksByPage, "分页获取的图书列表不应为空");
            assertTrue(booksByPage.size() <= pageSize, "返回的图书数量应小于等于页面大小");
            booksByPage.forEach(System.out::println);
        } catch (SQLException e) {
            fail("分页获取图书列表失败: " + e.getMessage());
        }
    }

    @Test
    @Order(8)
    public void testGetTopBooksByRating() {
        System.out.println("测试: 获取评分最高的前N本书");
        int topN = 5;
        try {
            List<Book> topBooks = bookDao.getTopBooksByRating(topN);
            assertNotNull(topBooks, "获取的图书列表不应为空");
            assertTrue(topBooks.size() <= topN, "返回的图书数量应小于等于指定值");
            topBooks.forEach(System.out::println);
        } catch (SQLException e) {
            fail("获取评分最高的图书失败: " + e.getMessage());
        }
    }

    @Test
    @Order(9)
    public void testGetBooksByTag() {
        System.out.println("测试: 根据标签获取图书");
        String tag = "测试";
        try {
            List<Book> booksByTag = bookDao.getBooksByTag(tag);
            assertNotNull(booksByTag, "根据标签获取的图书列表不应为空");
            booksByTag.forEach(System.out::println);
        } catch (SQLException e) {
            fail("根据标签获取图书失败: " + e.getMessage());
        }
    }

    @Test
    @Order(10)
    public void testSearchBooksByKeyword() {
        System.out.println("测试: 根据关键词进行图书搜索");

        String keyword = "余华"; // 测试关键词

        try {
            // 使用BookDao的searchBooksByKeyword方法进行搜索
            List<Book> books = bookDao.searchBooksByKeyword(keyword);

            // 断言返回结果不为空
            assertNotNull(books, "搜索结果不应为空");
            assertTrue(books.size() > 0, "搜索结果应包含至少1本图书");

            // 输出搜索结果
            books.forEach(book -> System.out.println(book));

        } catch (SQLException e) {
            e.printStackTrace();
            fail("根据关键词搜索图书失败: " + e.getMessage());
        }
    }

    @Test
    @Order(11)
    public void testGetSearchResultCount() {
        System.out.println("测试: 获取搜索结果的总记录数");

        String keyword = "余华"; // 测试关键词

        try {
            // 使用BookDao的getSearchResultCount方法获取图书总数
            long count = bookDao.getSearchResultCount(keyword);

            // 断言返回的记录数应为非负数
            assertTrue(count >= 0, "搜索结果总数应为非负数");
            System.out.println("搜索结果总数: " + count);

        } catch (SQLException e) {
            e.printStackTrace();
            fail("获取搜索结果总记录数失败: " + e.getMessage());
        }
    }

    @Test
    @Order(12)
    public void testGetUserRatingMatrix() {
        System.out.println("测试: 获取用户评分矩阵");
        try {
            List<Object[]> userRatingMatrix = bookDao.getUserRatingMatrix();
            assertNotNull(userRatingMatrix, "用户评分矩阵不应为空");
            System.out.println("用户评分矩阵记录数: " + userRatingMatrix.size());
            for (Object[] row : userRatingMatrix) {
                System.out.println("User ID: " + row[0] + ", Book ID: " + row[1] + ", Rating: " + row[2]);
            }
        } catch (SQLException e) {
            fail("获取用户评分矩阵失败: " + e.getMessage());
        }
    }

    @Test
    @Order(13)
    public void testGetUserBehaviorMatrix() {
        System.out.println("测试: 获取用户行为矩阵");
        try {
            List<Object[]> userBehaviorMatrix = bookDao.getUserBehaviorMatrix();
            assertNotNull(userBehaviorMatrix, "用户行为矩阵不应为空");
            System.out.println("用户行为矩阵记录数: " + userBehaviorMatrix.size());
            for (Object[] row : userBehaviorMatrix) {
                System.out.println("User ID: " + row[0] + ", Book ID: " + row[1] + ", Behavior Score: " + row[2]);
            }
        } catch (SQLException e) {
            fail("获取用户行为矩阵失败: " + e.getMessage());
        }
    }

    @Test
    @Order(14)
    public void testGetFinalUserRatingMatrix() {
        System.out.println("测试: 获取最终评分矩阵");
        try {
            List<Object[]> finalUserRatingMatrix = bookDao.getFinalUserRatingMatrix();
            assertNotNull(finalUserRatingMatrix, "最终评分矩阵不应为空");
            System.out.println("最终评分矩阵记录数: " + finalUserRatingMatrix.size());
            for (Object[] row : finalUserRatingMatrix) {
                System.out.println("User ID: " + row[0] + ", Book ID: " + row[1] + ", Final Score: " + row[2]);
            }
        } catch (SQLException e) {
            fail("获取最终评分矩阵失败: " + e.getMessage());
        }
    }
}