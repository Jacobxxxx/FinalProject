import Service.BookService;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        // 初始化BookService
        bookService = new BookService();
    }

    // 测试获取所有图书信息
    @Test
    public void testGetAllBooks() throws SQLException {
        System.out.println("测试: 获取所有图书信息");

        // 直接调用实际方法获取图书数据
        List<Book> books = bookService.getAllBooks();

        // 打印获取的结果，查看每本书的详细信息
        System.out.println("获取的图书列表: ");
        for (Book book : books) {
            System.out.println(book);
        }

        // 验证返回结果
        assertNotNull(books);
        assertTrue(books.size() > 0, "图书列表不应为空");
    }

    // 测试根据ID获取图书
    @Test
    public void testGetBookById() throws SQLException {
        int bookId = 1; // 假设我们查询ID为1的图书

        System.out.println("测试: 根据ID获取图书信息");

        // 调用实际方法
        Book book = bookService.getBookById(bookId);

        // 打印图书信息
        System.out.println("获取到的图书: ");
        System.out.println(book);

        // 验证返回结果
        assertNotNull(book);
        assertEquals(bookId, book.getId(), "返回的图书ID应与查询ID一致");
    }

    // 测试获取图书总记录数
    @Test
    public void testGetBookCount() throws SQLException {
        System.out.println("测试: 获取图书总记录数");

        // 调用实际方法
        long count = bookService.getBookCount();

        // 打印记录数
        System.out.println("图书总数: " + count);

        // 验证返回结果
        assertTrue(count > 0, "图书总数应大于0");
    }

    // 测试添加图书
    @Test
    public void testAddBook() throws SQLException {
        System.out.println("测试: 添加图书");

        // 创建一个新的图书对象
        Book newBook = new Book();
        newBook.setBook_name("新书");
        newBook.setAuthor("新作者");
        newBook.setCover_image_url("新封面");
        newBook.setPublisher("新出版社");
        newBook.setPublish_year("2023");
        newBook.setRating(9.0);
        newBook.setRating_count(100);
        newBook.setDescription("这是一本新书");
        newBook.setAuthor_description("这是作者简介");
        newBook.setTag("新标签");

        // 调用添加图书的方法
        int result = bookService.addBook(newBook);

        // 输出添加操作影响的行数
        System.out.println("添加图书影响的行数: " + result);

        // 验证结果
        assertEquals(1, result, "应成功添加图书");
    }

    // 测试更新图书信息
    @Test
    public void testUpdateBook() throws SQLException {
        System.out.println("测试: 更新图书信息");

        // 假设ID为1的图书已经存在
        Book book = bookService.getBookById(1);
        assertNotNull(book, "图书应存在");

        // 更新图书的名称
        book.setBook_name("更新后的书名");

        // 调用更新方法
        int result = bookService.updateBook(book);

        // 输出更新操作影响的行数
        System.out.println("更新图书影响的行数: " + result);

        // 验证更新结果
        assertEquals(1, result, "应成功更新图书");
    }

    // 测试删除图书
    @Test
    public void testDeleteBook() throws SQLException {
        int bookId = 1; // 假设我们删除ID为1的图书

        System.out.println("测试: 删除图书");

        // 调用删除图书的方法
        int result = bookService.deleteBook(bookId);

        // 输出删除操作影响的行数
        System.out.println("删除图书影响的行数: " + result);

        // 验证删除结果
        assertEquals(1, result, "应成功删除图书");
    }

    // 测试分页获取图书
    @Test
    public void testGetBooksByPage() throws SQLException {
        int pageNo = 1; // 获取第一页的图书
        int pageSize = 14;

        System.out.println("测试: 获取分页图书");

        // 获取分页图书列表
        List<Book> books = bookService.getBooksByPage(pageNo, pageSize);

        // 输出分页图书的信息
        System.out.println("获取到的图书列表: ");
        for (Book book : books) {
            System.out.println(book);
        }

        // 验证结果
        assertNotNull(books);
        assertTrue(books.size() > 0, "分页图书列表不应为空");
    }

    // 测试根据评分获取高评分图书
    @Test
    public void testGetTopBooksByRating() throws SQLException {
        int limit = 5; // 获取前5本高评分图书

        System.out.println("测试: 获取评分最高的图书");

        // 获取高评分图书列表
        List<Book> books = bookService.getTopBooksByRating(limit);

        // 输出高评分图书的信息
        System.out.println("获取到的高评分图书列表: ");
        for (Book book : books) {
            System.out.println(book);
        }

        // 验证结果
        assertNotNull(books);
        assertTrue(books.size() > 0, "高评分图书列表不应为空");
    }

    // 测试根据关键词搜索图书
    @Test
    public void testSearchBooks() throws SQLException {
        String keyword = "余华"; // 假设我们搜索关键词为"Java"

        System.out.println("测试: 根据关键词搜索图书");

        // 获取搜索结果
        List<Book> result = bookService.searchBooks(keyword);

        // 输出搜索结果
        System.out.println("搜索结果: ");
        for (Book book : result) {
            System.out.println(book);
        }

        // 验证结果
        assertNotNull(result);
        assertTrue(result.size() > 0, "搜索结果不应为空");
    }

    // 测试获取搜索结果数量
    @Test
    public void testGetSearchResultCount() throws SQLException {
        String keyword = "余华"; // 假设我们搜索关键词为"Java"

        System.out.println("测试: 获取搜索结果数量");

        // 获取搜索结果总数
        long count = bookService.getSearchResultCount(keyword);

        // 输出搜索结果总数
        System.out.println("搜索结果总数: " + count);

        // 验证结果
        assertTrue(count > 0, "搜索结果总数应大于0");
    }
}
