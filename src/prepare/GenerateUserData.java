package prepare;

import java.sql.*;
import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class GenerateUserData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "123456";

        int numRatingsPerBook = 27; // 每本书生成的用户评分数量
        double stdMin = 0.3; // 最小标准差
        double stdBase = 2.0; // 最大标准差
        int maxRatingCount = 856630; // 假设评论数的最大值
        int numUsers = 500; // 用户数量

        Connection connection = null;

        try {
            // 连接数据库
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功！");

            // 清空旧数据
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM user_ratings"); // 清空用户评分数据
                stmt.execute("DELETE FROM users");        // 清空用户数据
                System.out.println("删除 users 和 user_ratings 表数据完成！");
            }

            // 插入用户数据
            String insertUsersQuery = "INSERT INTO users (user_id, username, password, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement userStmt = connection.prepareStatement(insertUsersQuery)) {
                for (int i = 1; i <= numUsers; i++) {
                    String userId = "U" + String.format("%04d", i); // 用户ID
                    String username = "User" + String.format("%04d", i); // 用户名
                    String userPassword = generateRandomPassword(8); // 随机生成密码
                    String email = username.toLowerCase() + "@example.com"; // 邮箱

                    userStmt.setString(1, userId);
                    userStmt.setString(2, username);
                    userStmt.setString(3, userPassword);
                    userStmt.setString(4, email);
                    userStmt.addBatch();
                }
                userStmt.executeBatch();
                System.out.println("用户数据生成完成！");
            }

            // 读取书籍数据
            String fetchBooksQuery = "SELECT id, rating, rating_count FROM books";
            List<Map<String, Object>> books = new ArrayList<>();
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(fetchBooksQuery)) {
                while (rs.next()) {
                    Map<String, Object> book = new HashMap<>();
                    book.put("id", rs.getInt("id")); // 书籍ID
                    book.put("rating", rs.getDouble("rating")); // 书籍评分
                    book.put("rating_count", rs.getInt("rating_count")); // 评论数
                    books.add(book);
                }
            }
            System.out.println("共读取 " + books.size() + " 本书籍数据。");

            // 插入评分数据
            String insertRatingsQuery = "INSERT INTO user_ratings (id, user_id, book_id, rating, rating_time) VALUES (?, ?, ?, ?, ?)";
            Random random = new Random();
            Set<String> existingRatings = new HashSet<>(); // 存储已生成的 user_id 和 book_id 组合
            int ratingId = 1; // 手动管理评分ID

            try (PreparedStatement ratingsStmt = connection.prepareStatement(insertRatingsQuery)) {
                for (Map<String, Object> book : books) {
                    int bookId = (Integer) book.get("id");
                    double bookRating = (Double) book.get("rating");
                    int ratingCount = (Integer) book.get("rating_count");

                    // 根据评论数计算评分分布的标准差
                    double stdDev = Math.max(stdMin, stdBase * (1 - (double) ratingCount / maxRatingCount));

                    for (int i = 0; i < numRatingsPerBook; i++) {
                        String userId = "U" + String.format("%04d", random.nextInt(numUsers) + 1); // 随机选择用户ID
                        String uniqueKey = userId + "-" + bookId; // 生成唯一组合

                        // 检查是否已经存在
                        if (existingRatings.contains(uniqueKey)) {
                            i--; // 如果重复，则重新尝试当前评分
                            continue;
                        }
                        existingRatings.add(uniqueKey); // 记录新组合

                        double rating;
                        if (stdDev <= 0.5) {
                            // 评论数多，使用正态分布
                            rating = bookRating + (random.nextGaussian() * stdDev);
                        } else {
                            // 评论数少，使用均匀分布
                            rating = bookRating - 2 + (random.nextDouble() * 4);
                        }

                        // 限制评分范围在 1.0 到 10.0
                        rating = Math.min(Math.max(rating, 1.0), 9.8);

                        // 生成随机评分时间
                        Timestamp randomTimestamp = generateRandomTimestamp(random);

                        // 插入评分数据
                        ratingsStmt.setInt(1, ratingId); // 手动设置评分ID
                        ratingsStmt.setString(2, userId);
                        ratingsStmt.setInt(3, bookId);
                        ratingsStmt.setDouble(4, rating);
                        ratingsStmt.setTimestamp(5, randomTimestamp);
                        ratingsStmt.addBatch();

                        ratingId++; // 增加评分ID
                    }
                    ratingsStmt.executeBatch();
                }
                System.out.println("评分数据生成完成！");
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

    /**
     * 生成随机密码
     *
     * @param length 密码长度
     * @return 随机密码
     */
    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    /**
     * 生成 2022-2024 之间的随机时间
     *
     * @param random 随机数生成器
     * @return 随机时间的 Timestamp
     */
    public static Timestamp generateRandomTimestamp(Random random) {
        LocalDate startDate = LocalDate.of(2022, 1, 1); // 起始日期
        LocalDate endDate = LocalDate.of(2024, 12, 31); // 结束日期

        long start = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long end = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        long randomTime = start + (long) (random.nextDouble() * (end - start));
        return new Timestamp(randomTime);
    }
}
