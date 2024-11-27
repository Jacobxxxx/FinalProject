package model;

public class Book {
    private int id;                     // 图书ID
    private String book_url;            // 图书链接
    private String book_name;           // 图书名称
    private String author;              // 作者
    private String cover_image_url;     // 封面图片URL
    private String publisher;           // 出版社
    private String publish_year;        // 出版年份
    private double rating;              // 图书评分
    private int rating_count;           // 评分人数
    private String description;         // 图书简介
    private String author_description;  // 作者简介
    private String tag;                 // 标签

    // 无参构造方法
    public Book() {}

    // 全参构造方法
    public Book(int id, String book_url, String book_name, String author, String cover_image_url,
                String publisher, String publish_year, double rating, int rating_count,
                String description, String author_description, String tag) {
        this.id = id;
        this.book_url = book_url;
        this.book_name = book_name;
        this.author = author;
        this.cover_image_url = cover_image_url;
        this.publisher = publisher;
        this.publish_year = publish_year;
        this.rating = rating;
        this.rating_count = rating_count;
        this.description = description;
        this.author_description = author_description;
        this.tag = tag;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook_url() {
        return book_url;
    }

    public void setBook_url(String book_url) {
        this.book_url = book_url;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover_image_url() {
        return cover_image_url;
    }

    public void setCover_image_url(String cover_image_url) {
        this.cover_image_url = cover_image_url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublish_year() {
        return publish_year;
    }

    public void setPublish_year(String publish_year) {
        this.publish_year = publish_year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor_description() {
        return author_description;
    }

    public void setAuthor_description(String author_description) {
        this.author_description = author_description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    // 重写 toString 方法
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", book_url='" + book_url + '\'' +
                ", book_name='" + book_name + '\'' +
                ", author='" + author + '\'' +
                ", cover_image_url='" + cover_image_url + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publish_year='" + publish_year + '\'' +
                ", rating=" + rating +
                ", rating_count=" + rating_count +
                ", description='" + description + '\'' +
                ", author_description='" + author_description + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
