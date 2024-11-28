package prepare;

import java.sql.*;
import java.util.*;

public class GenerateUserActions {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "123456";

        Connection connection = null;

        try {
            // 连接数据库
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功！");

            // 清空 user_actions 表并重置自增 ID
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM user_actions");
                stmt.execute("ALTER TABLE user_actions AUTO_INCREMENT = 1"); // 重置自增 ID
                System.out.println("已清空 user_actions 表并重置自增 ID！");
            }

            // 读取所有用户数据
            String fetchUsersQuery = "SELECT user_id FROM users ORDER BY user_id";
            List<String> allUsers = new ArrayList<>();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(fetchUsersQuery)) {
                while (rs.next()) {
                    allUsers.add(rs.getString("user_id"));
                }
            }
            System.out.println("共读取 " + allUsers.size() + " 个用户。");

            // 读取所有有效的书籍 ID
            String fetchBooksQuery = "SELECT id FROM books";
            List<Integer> allBooks = new ArrayList<>();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(fetchBooksQuery)) {
                while (rs.next()) {
                    allBooks.add(rs.getInt("id"));
                }
            }
            System.out.println("共读取 " + allBooks.size() + " 本书籍。");

            // 读取所有评分数据
            String fetchRatingsQuery = "SELECT user_id, book_id, rating FROM user_ratings";
            Map<String, List<Map<String, Object>>> userRatingsMap = new HashMap<>();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(fetchRatingsQuery)) {
                while (rs.next()) {
                    String userId = rs.getString("user_id");
                    int bookId = rs.getInt("book_id");
                    double rating = rs.getDouble("rating");

                    userRatingsMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(Map.of(
                            "book_id", bookId,
                            "rating", rating
                    ));
                }
            }
            System.out.println("共读取 " + userRatingsMap.size() + " 个用户的评分数据。");

            // 插入 user_actions 数据
            String insertActionsQuery = "INSERT INTO user_actions (id, user_id, book_id, browse, favorite) VALUES (?, ?, ?, ?, ?)";
            Random random = new Random();
            int minActionsPerUser = 20;

            int actionId = 1; // 手动管理行为 ID

            try (PreparedStatement actionsStmt = connection.prepareStatement(insertActionsQuery)) {
                for (String userId : allUsers) {
                    List<Map<String, Object>> ratings = userRatingsMap.getOrDefault(userId, new ArrayList<>());
                    Set<Integer> processedBooks = new HashSet<>(); // 避免重复的书籍

                    // 处理评分数据
                    for (Map<String, Object> ratingData : ratings) {
                        int bookId = (int) ratingData.get("book_id");
                        double rating = (double) ratingData.get("rating");

                        int browse = 1; // 评分必定浏览
                        int favorite = 0;

                        // 根据评分生成收藏行为
                        if (rating >= 8) {
                            favorite = (Math.random() < 0.8) ? 1 : 0;
                        } else if (rating >= 6) {
                            favorite = (Math.random() < 0.5) ? 1 : 0;
                        } else {
                            favorite = (Math.random() < 0.3) ? 1 : 0;
                        }

                        processedBooks.add(bookId);

                        // 插入行为数据
                        actionsStmt.setInt(1, actionId++);
                        actionsStmt.setString(2, userId);
                        actionsStmt.setInt(3, bookId);
                        actionsStmt.setInt(4, browse);
                        actionsStmt.setInt(5, favorite);
                        actionsStmt.addBatch();
                    }

                    // 随机补充行为数据至至少 20 条
                    while (processedBooks.size() < minActionsPerUser) {
                        int randomBookId = allBooks.get(random.nextInt(allBooks.size())); // 从有效书籍中随机选择
                        if (processedBooks.contains(randomBookId)) continue; // 避免重复

                        int browse = 1; // 随机生成的收藏也必定浏览
                        int favorite = (Math.random() < 0.5) ? 1 : 0;

                        processedBooks.add(randomBookId);

                        actionsStmt.setInt(1, actionId++);
                        actionsStmt.setString(2, userId);
                        actionsStmt.setInt(3, randomBookId);
                        actionsStmt.setInt(4, browse);
                        actionsStmt.setInt(5, favorite);
                        actionsStmt.addBatch();
                    }
                }
                actionsStmt.executeBatch();
                System.out.println("用户行为数据生成完成！");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
