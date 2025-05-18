package com.example.quanlythuvien.model;

public class Document {
    private int id;
    private String title;
    private String author;
    private String imageUrl;
    private String category;
    private String status;
    private int viewCount;
    private String updatedAt;
    private String summary;

    // ✅ Constructor đầy đủ từ CSDL (có id)
    public Document(int id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
        this.summary = summary;
        this.imageUrl = imageUrl;
        this.viewCount = viewCount;
        this.updatedAt = updatedAt;
    }

    // ✅ Constructor cho dữ liệu tạm (không có id)
    public Document(String title, String author, String imageUrl,
                    String category, String status, int viewCount,
                    String updatedAt, String summary) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.category = category;
        this.status = status;
        this.viewCount = viewCount;
        this.updatedAt = updatedAt;
        this.summary = summary;
    }

    // ===== Getter =====
    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getAuthor() { return author; }

    public String getImageUrl() { return imageUrl; }

    public String getCategory() { return category; }

    public String getStatus() { return status; }

    public int getViewCount() { return viewCount; }

    public String getUpdatedAt() { return updatedAt; }

    public String getSummary() { return summary; }

    // ===== Setter =====
    public void setId(int id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setAuthor(String author) { this.author = author; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public void setCategory(String category) { this.category = category; }

    public void setStatus(String status) { this.status = status; }

    public void setViewCount(int viewCount) { this.viewCount = viewCount; }

    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public void setSummary(String summary) { this.summary = summary; }
}
