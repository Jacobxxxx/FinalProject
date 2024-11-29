package model;

import java.sql.Timestamp;

public class UserRating {
    private int id;
    private String user_id;
    private int book_id;
    private double rating;
    private Timestamp rating_time;

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Timestamp getRating_time() {
        return rating_time;
    }

    public void setRating_time(Timestamp rating_time) {
        this.rating_time = rating_time;
    }
}

