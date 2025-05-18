package com.example.quanlythuvien.model;

import java.io.Serializable;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String username;
    private final String documentTitle;
    private final String content;
    private final String date;

    public Comment(String username, String documentTitle, String content, String date) {
        this.username = username;
        this.documentTitle = documentTitle;
        this.content = content;
        this.date = date;
    }

    public String getUsername() { return username; }

    public String getDocumentTitle() { return documentTitle; }

    public String getContent() { return content; }

    public String getDate() { return date; }
}
