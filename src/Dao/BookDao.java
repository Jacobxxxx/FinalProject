package Dao;
import model.Book;
import utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.SQLException;


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

    public int deleteBook(int id) throws SQLException {
        Connection conn = DataSourceUtils.getConnection();

        String deleteBookSql = "DELETE FROM books WHERE id = ?";
        String deleteUserRatingSql = "DELETE FROM user_ratings WHERE book_id = ?";
        String deleteUserActionSql = "DELETE FROM user_actions WHERE book_id = ?";
        try {
            conn.setAutoCommit(false);

            runner.update(conn, deleteUserRatingSql, id);
            runner.update(conn, deleteUserActionSql, id);
            int result = runner.update(conn, deleteBookSql, id);

            conn.commit();
            return result;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
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

    // 获取用户评分矩阵：返回评分矩阵数据，计算每个用户对每本书的评分
    public List<Object[]> getUserRatingMatrix() throws SQLException {
        String sql = "SELECT user_id, book_id, rating FROM user_ratings";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, resultSet -> {
                List<Object[]> matrix = new ArrayList<>();
                while (resultSet.next()) {
                    String userId = resultSet.getString("user_id");
                    int bookId = resultSet.getInt("book_id");
                    double rating = resultSet.getDouble("rating");
                    matrix.add(new Object[]{userId, bookId, rating});
                }
                return matrix;
            });
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 获取用户行为矩阵：根据用户行为数据（浏览和收藏）更新评分
    public List<Object[]> getUserBehaviorMatrix() throws SQLException {
        String sql = "SELECT ua.user_id, ua.book_id, ua.browse, ua.favorite FROM user_actions ua";
        Connection conn = DataSourceUtils.getConnection();
        try {
            return runner.query(conn, sql, resultSet -> {
                List<Object[]> matrix = new ArrayList<>();
                while (resultSet.next()) {
                    String userId = resultSet.getString("user_id");
                    int bookId = resultSet.getInt("book_id");
                    int browse = resultSet.getInt("browse");
                    int favorite = resultSet.getInt("favorite");

                    // 根据行为生成评分
                    double behaviorScore = browse * 0.05 + favorite * 0.25;
                    matrix.add(new Object[]{userId, bookId, behaviorScore});
                }
                return matrix;
            });
        } finally {
            DataSourceUtils.closeConnection(conn);
        }
    }

    // 获取最终评分矩阵：结合评分和行为矩阵
    public List<Object[]> getFinalUserRatingMatrix() throws SQLException {
        List<Object[]> ratingMatrix = getUserRatingMatrix();
        List<Object[]> behaviorMatrix = getUserBehaviorMatrix();

        // 合并评分和行为矩阵的评分
        Map<String, Double> finalMatrix = new HashMap<>();
        for (Object[] behaviorRow : behaviorMatrix) {
            String userId = (String) behaviorRow[0];
            int bookId = (int) behaviorRow[1];
            double behaviorScore = (double) behaviorRow[2];

            finalMatrix.put(userId + "_" + bookId, behaviorScore);
        }

        for (Object[] ratingRow : ratingMatrix) {
            String userId = (String) ratingRow[0];
            int bookId = (int) ratingRow[1];
            double rating = (double) ratingRow[2];

            String key = userId + "_" + bookId;
            double finalScore = rating * 0.7 + finalMatrix.getOrDefault(key, 0.0);
            finalMatrix.put(key, finalScore);
        }

        List<Object[]> finalMatrixList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : finalMatrix.entrySet()) {
            String[] parts = entry.getKey().split("_");
            String userId = parts[0];
            int bookId = Integer.parseInt(parts[1]);
            double finalScore = entry.getValue();
            finalMatrixList.add(new Object[]{userId, bookId, finalScore});
        }

        return finalMatrixList;
    }
}
