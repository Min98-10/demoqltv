package com.example.quanlythuvien.dao;

import com.example.quanlythuvien.model.Document;
import com.example.quanlythuvien.util.DatabaseHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {

    public static List<Document> getLatestDocuments() {
        List<Document> list = new ArrayList<>();
        String sql = "SELECT TOP 8 * FROM documents ORDER BY id DESC";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToDocument(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Document> search(String keyword) {
        List<Document> list = new ArrayList<>();
        String sql = "SELECT * FROM documents WHERE title LIKE ? OR author LIKE ? ORDER BY id DESC";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToDocument(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Document getByTitle(String title) {
        String sql = "SELECT * FROM documents WHERE title = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToDocument(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // === Helper ===
    private static Document mapResultSetToDocument(ResultSet rs) throws SQLException {
        return new Document(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("category")
        );
    }
}
