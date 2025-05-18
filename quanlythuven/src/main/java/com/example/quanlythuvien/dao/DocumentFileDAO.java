package com.example.quanlythuvien.dao;

import com.example.quanlythuvien.model.Document;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentFileDAO {
    private static final String FILE_PATH = "data/documents.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Đọc toàn bộ danh sách tài liệu
    public static List<Document> getAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Document>>() {}.getType();
            List<Document> list = gson.fromJson(reader, listType);
            return (list != null) ? list : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    // Ghi toàn bộ danh sách vào file
    public static void saveAll(List<Document> docs) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(docs, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thêm một tài liệu mới nếu chưa tồn tại (so sánh theo tiêu đề)
    public static void add(Document doc) {
        if (!contains(doc)) {
            List<Document> list = getAll();
            list.add(doc);
            saveAll(list);
        }
    }

    // Kiểm tra xem tài liệu đã tồn tại chưa
    public static boolean contains(Document doc) {
        return getAll().stream().anyMatch(d -> d.getTitle().equalsIgnoreCase(doc.getTitle()));
    }

    // Cập nhật thông tin một tài liệu
    public static void update(Document updatedDoc) {
        List<Document> all = getAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getTitle().equalsIgnoreCase(updatedDoc.getTitle())) {
                all.set(i, updatedDoc);
                break;
            }
        }
        saveAll(all);
    }

    // Xoá tài liệu theo tiêu đề
    public static void remove(Document target) {
        List<Document> list = getAll();
        list.removeIf(doc -> doc.getTitle().equalsIgnoreCase(target.getTitle()));
        saveAll(list);
    }

    // Lấy một tài liệu theo tiêu đề
    public static Document getByTitle(String title) {
        return getAll().stream()
                .filter(doc -> doc.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    // Tìm kiếm theo tiêu đề hoặc tác giả
    public static List<Document> search(String keyword) {
        String lower = keyword.toLowerCase();
        return getAll().stream()
                .filter(doc -> doc.getTitle().toLowerCase().contains(lower) ||
                        doc.getAuthor().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }
}
