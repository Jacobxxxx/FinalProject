package Service;

import Dao.BookDao;
import model.Book;

import java.sql.SQLException;
import java.util.List;

public class BookService {

    private BookDao bookDao;

    // 构造方法初始化 BookDao
    public BookService() {
        this.bookDao = new BookDao();
    }

    /**
     * 获取所有图书信息
     * @return 图书列表
     * @throws SQLException
     */
    public List<Book> getAllBooks() throws SQLException {
        return bookDao.getAllBooks();
    }

    /**
     * 根据ID获取图书信息
     * @param id 图书ID
     * @return 图书对象
     * @throws SQLException
     */
    public Book getBookById(int id) throws SQLException {
        return bookDao.getBookById(id);
    }

    /**
     * 获取图书总记录数
     * @return 图书总数
     * @throws SQLException
     */
    public long getBookCount() throws SQLException {
        return bookDao.getBookCount();
    }

    /**
     * 添加图书
     * @param book 图书对象
     * @return 添加操作影响的行数
     * @throws SQLException
     */
    public int addBook(Book book) throws SQLException {
        validateBook(book); // 验证图书数据
        return bookDao.addBook(book);
    }

    /**
     * 更新图书信息
     * @param book 图书对象
     * @return 更新操作影响的行数
     * @throws SQLException
     */
    public int updateBook(Book book) throws SQLException {
        validateBook(book); // 验证图书数据
        return bookDao.updateBook(book);
    }

    /**
     * 删除图书
     * @param id 图书ID
     * @return 删除操作影响的行数
     * @throws SQLException
     */
    public int deleteBook(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("图书ID无效！");
        }
        return bookDao.deleteBook(id);
    }

    /**
     * 分页获取图书
     * @param pageNo 当前页码
     * @param pageSize 每页记录数
     * @return 图书列表
     * @throws SQLException
     */
    public List<Book> getBooksByPage(int pageNo, int pageSize) throws SQLException {
        if (pageNo <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException("分页参数无效！");
        }
        return bookDao.getBooksByPage(pageNo, pageSize);
    }

    /**
     * 获取评分最高的图书（用于个性化推荐）
     * @param limit 获取图书的数量
     * @return 高评分图书列表
     * @throws SQLException
     */
    public List<Book> getTopBooksByRating(int limit) throws SQLException {
        if (limit <= 0) {
            throw new IllegalArgumentException("限制数量无效！");
        }
        return bookDao.getTopBooksByRating(limit);
    }

    /**
     * 根据标签获取图书列表
     * @param tag 图书标签
     * @return 符合标签的图书列表
     * @throws SQLException
     */
    public List<Book> getBooksByTag(String tag) throws SQLException {
        if (tag == null || tag.trim().isEmpty()) {
            throw new IllegalArgumentException("标签不能为空！");
        }
        return bookDao.getBooksByTag(tag);
    }

    /**
     * 验证图书数据合法性
     * @param book 图书对象
     */
    private void validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("图书对象不能为空！");
        }
        if (book.getBook_name() == null || book.getBook_name().trim().isEmpty()) {
            throw new IllegalArgumentException("图书名称不能为空！");
        }
        if (book.getRating() < 0 || book.getRating() > 10) {
            throw new IllegalArgumentException("图书评分必须在0到10之间！");
        }
        if (book.getRating_count() < 0) {
            throw new IllegalArgumentException("评分人数不能为负数！");
        }
    }
}
