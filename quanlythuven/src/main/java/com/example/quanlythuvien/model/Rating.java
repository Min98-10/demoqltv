package com.example.quanlythuvien.model;

import java.io.Serializable;

public class Rating implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String username;
    private final String documentTitle;
    private final int stars; // 1–5
    private final String review; // nội dung đánh giá (tuỳ chọn)

    public Rating(String username, String documentTitle, int stars, String review) {
        this.username = username;
        this.documentTitle = documentTitle;
        this.stars = stars;
        this.review = review;
    }

    public String getUsername() { return username; }
    public String getDocumentTitle() { return documentTitle; }
    public int getStars() { return stars; }
    public String getReview() { return review; }
}
