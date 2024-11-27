package Dao;

import model.Book;
import utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookDao {

    private QueryRunner runner = new QueryRunner();

    // 获取所有图书信息列表
    public List<Book> getAllBooks() throws SQLException {
        String sql = "SELECT * FROM books";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanListHandler<>(Book.class));
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 根据ID获取指定图书信息
    public Book getBookById(int id) throws SQLException {
        String sql = "SELECT * FROM books WHERE id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanHandler<>(Book.class), id);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 获取图书总记录数
    public long getBookCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM books";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new ScalarHandler<>());
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 添加图书
    public int addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (book_url, book_name, author, cover_image_url, publisher, publish_year, rating, rating_count, description, author_description, tag) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql,
                    book.getBook_url(),
                    book.getBook_name(),
                    book.getAuthor(),
                    book.getCover_image_url(),
                    book.getPublisher(),
                    book.getPublish_year(),
                    book.getRating(),
                    book.getRating_count(),
                    book.getDescription(),
                    book.getAuthor_description(),
                    book.getTag()
            );
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 更新图书信息
    public int updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET book_url = ?, book_name = ?, author = ?, cover_image_url = ?, publisher = ?, publish_year = ?, rating = ?, rating_count = ?, description = ?, author_description = ?, tag = ? WHERE id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql,
                    book.getBook_url(),
                    book.getBook_name(),
                    book.getAuthor(),
                    book.getCover_image_url(),
                    book.getPublisher(),
                    book.getPublish_year(),
                    book.getRating(),
                    book.getRating_count(),
                    book.getDescription(),
                    book.getAuthor_description(),
                    book.getTag(),
                    book.getId()
            );
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 删除指定ID的图书
    public int deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.update(conn, sql, id);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 分页获取图书列表
    public List<Book> getBooksByPage(int pageNo, int pageSize) throws SQLException {
        String sql = "SELECT * FROM books LIMIT ?, ?";
        Connection conn = DataSourceUtils.getConnection();
        int offset = (pageNo - 1) * pageSize;
        try {
            return runner.query(conn, sql, new BeanListHandler<>(Book.class), offset, pageSize);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 根据评分获取前N本书（用于个性化推荐）
    public List<Book> getTopBooksByRating(int limit) throws SQLException {
        String sql = "SELECT * FROM books ORDER BY rating DESC LIMIT ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanListHandler<>(Book.class), limit);
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 根据标签获取图书列表
    public List<Book> getBooksByTag(String tag) throws SQLException {
        String sql = "SELECT * FROM books WHERE tag LIKE ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new BeanListHandler<>(Book.class), "%" + tag + "%");
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }


    // 根据关键词进行搜索（书名、作者模糊搜索）
    public List<Book> searchBooksByKeyword(String keyword) throws SQLException {
        String sql = "SELECT * FROM books WHERE book_name LIKE ? OR author LIKE ? ";
        Connection conn = DataSourceUtils.getConnection();

        try {
            return runner.query(conn, sql, new BeanListHandler<>(Book.class), "%" + keyword + "%", "%" + keyword + "%");
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 获取搜索结果的总记录数
    public long getSearchResultCount(String keyword) throws SQLException {
        String sql = "SELECT COUNT(*) FROM books WHERE book_name LIKE ? OR author LIKE ?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, new ScalarHandler<>(), "%" + keyword + "%", "%" + keyword + "%");
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }
}


