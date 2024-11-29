package model;

public class UserAction {
    private int id;                // 行为记录的唯一ID
    private String user_id;        // 用户ID
    private int book_id;           // 图书ID
    private int browse;            // 是否浏览，0表示未浏览，1表示已浏览
    private int favorite;          // 是否收藏，0表示未收藏，1表示已收藏

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getBrowse() {
        return browse;
    }

    public void setBrowse(int browse) {
        this.browse = browse;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
