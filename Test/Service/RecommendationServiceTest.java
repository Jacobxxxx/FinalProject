package Service;

import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecommendationServiceTest {

    private BookService bookService;
    private RecommendationService recommendationService;

    @BeforeEach
    public void setUp() {
        // 初始化真实的 BookService 实例
        bookService = new BookService();
        recommendationService = new RecommendationService(bookService);
    }

    @Test
    public void testGenerateRecommendations() throws SQLException {
        // 假设我们要为用户 "U0001" 生成推荐
        String targetUserId = "U0102";

        // 获取推荐的图书列表
        List<Book> recommendedBooks = recommendationService.generateRecommendations(targetUserId);

        // 打印推荐的图书信息
        System.out.println("推荐给用户 " + targetUserId + " 的图书：");
        for (Book book : recommendedBooks) {
            System.out.println("图书名称: " + book.getBook_name() + ", 评分: " + book.getRating());
        }

        // 检查推荐结果是否为非空
        assertNotNull(recommendedBooks, "推荐图书列表不应为空");

        // 检查推荐图书数量是否满足要求
        assertTrue(recommendedBooks.size() <= 14, "推荐图书数量应小于或等于14本");
    }

    @Test
    public void testCalculateSimilarity() throws SQLException {
        // 获取用户评分矩阵（模拟）
        List<Object[]> finalUserRatingMatrix = recommendationService.getFinalUserRatingMatrix();

        // 生成用户-图书评分矩阵
        Map<String, Map<Integer, Double>> userRatings = new HashMap<>();
        for (Object[] row : finalUserRatingMatrix) {
            String userId = (String) row[0];
            int bookId = (int) row[1];
            double rating = (double) row[2];

            userRatings.computeIfAbsent(userId, k -> new HashMap<>()).put(bookId, rating);
        }

        // 遍历所有用户之间的相似度
        for (String user1 : userRatings.keySet()) {
            for (String user2 : userRatings.keySet()) {
                if (!user1.equals(user2)) {  // 避免计算同一用户与自己的相似度
                    double similarity = recommendationService.calculateSimilarity(user1, user2, userRatings);
                    System.out.println("用户 " + user1 + " 和 " + user2 + " 的相似度: " + similarity);
                    assertTrue(similarity >= 0, "相似度计算应返回非负值");
                }
            }
        }
    }

}
