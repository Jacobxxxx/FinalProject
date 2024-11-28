package Service;

import model.Book;
import java.sql.SQLException;
import java.util.*;

public class RecommendationService {

    private BookService bookService;

    public RecommendationService(BookService bookService) {
        this.bookService = bookService;
    }

    // 1. 获取用户评分矩阵
    public List<Object[]> getUserRatingMatrix() throws SQLException {
        return bookService.getUserRatingMatrix();
    }

    // 2. 获取用户行为矩阵
    public List<Object[]> getUserBehaviorMatrix() throws SQLException {
        return bookService.getUserBehaviorMatrix();
    }

    // 3. 获取最终评分矩阵
    public List<Object[]> getFinalUserRatingMatrix() throws SQLException {
        return bookService.getFinalUserRatingMatrix();
    }

    // 4. 计算用户相似度：使用余弦相似度
    public double calculateSimilarity(String user1, String user2, Map<String, Map<Integer, Double>> userRatings) {
        Set<Integer> commonBooks = new HashSet<>(userRatings.get(user1).keySet());
        commonBooks.retainAll(userRatings.get(user2).keySet());

        if (commonBooks.isEmpty()) {
            return 0; // 无共同评分图书，返回相似度为0
        }

        double dotProduct = 0;
        double normUser1 = 0;
        double normUser2 = 0;

        for (int bookId : commonBooks) {
            double ratingUser1 = userRatings.get(user1).get(bookId);
            double ratingUser2 = userRatings.get(user2).get(bookId);

            dotProduct += ratingUser1 * ratingUser2;
            normUser1 += ratingUser1 * ratingUser1;
            normUser2 += ratingUser2 * ratingUser2;
        }

        return dotProduct / (Math.sqrt(normUser1) * Math.sqrt(normUser2)); // 余弦相似度公式
    }

    // 5. 为用户生成推荐图书
    public List<Book> generateRecommendations(String targetUserId) throws SQLException {
        // 通过 bookService 获取最终评分矩阵（用户-图书-评分）
        List<Object[]> finalUserRatingMatrix = getFinalUserRatingMatrix();

        // 生成用户-图书评分矩阵
        Map<String, Map<Integer, Double>> userRatings = new HashMap<>();
        for (Object[] row : finalUserRatingMatrix) {
            String userId = (String) row[0];
            int bookId = (int) row[1];
            double rating = (double) row[2];

            userRatings.computeIfAbsent(userId, k -> new HashMap<>()).put(bookId, rating);
        }

        // 计算与目标用户最相似的用户
        Map<String, Double> userSimilarities = new HashMap<>();
        for (String otherUserId : userRatings.keySet()) {
            if (!otherUserId.equals(targetUserId)) {
                double similarity = calculateSimilarity(targetUserId, otherUserId, userRatings);
                userSimilarities.put(otherUserId, similarity);
            }
        }

        // 根据相似度选择最相似的用户（可以调整相似度的阈值和数量）
        List<String> mostSimilarUsers = getMostSimilarUsers(userSimilarities, targetUserId, 10);

        // 为目标用户生成推荐
        Set<Integer> recommendedBooks = new HashSet<>();
        Map<Integer, Double> bookScores = new HashMap<>();

        for (String similarUser : mostSimilarUsers) {
            Map<Integer, Double> similarUserRatings = userRatings.get(similarUser);
            double similarity = userSimilarities.get(similarUser);

            for (Map.Entry<Integer, Double> entry : similarUserRatings.entrySet()) {
                int bookId = entry.getKey();
                double rating = entry.getValue();

                if (!userRatings.get(targetUserId).containsKey(bookId)) { // 如果目标用户没有评分过该图书
                    double weightedScore = rating * similarity;
                    bookScores.merge(bookId, weightedScore, Double::sum); // 加权评分
                    recommendedBooks.add(bookId);
                }
            }
        }

        // 获取推荐的图书
        List<Book> recommendedBooksList = new ArrayList<>();
        for (int bookId : recommendedBooks) {
            Book book = bookService.getBookById(bookId);
            if (book != null) {
                recommendedBooksList.add(book);
            }
        }

        // 如果推荐的图书大于等于 14 本，返回 14 本
        if (recommendedBooksList.size() >= 14) {
            return recommendedBooksList.subList(0, 14); // 截取前 14 本图书
        }

        // 如果推荐的图书少于 14 本，返回所有推荐图书
        return recommendedBooksList;
    }

    // 获取与目标用户最相似的 N 个用户
    private List<String> getMostSimilarUsers(Map<String, Double> userSimilarities, String targetUserId, int n) {
        // 按相似度降序排序
        List<Map.Entry<String, Double>> sortedSimilarities = new ArrayList<>(userSimilarities.entrySet());
        sortedSimilarities.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));

        // 获取最相似的 N 个用户
        List<String> mostSimilarUsers = new ArrayList<>();
        for (int i = 0; i < Math.min(n, sortedSimilarities.size()); i++) {
            mostSimilarUsers.add(sortedSimilarities.get(i).getKey());
        }

        return mostSimilarUsers;
    }
}
