package com.example.quanlythuvien.util;

import com.example.quanlythuvien.model.Comment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentDataManager {
    private static final String FILE_PATH = "data/comments.dat";

    public static void add(Comment comment) {
        List<Comment> all = loadAll();
        all.add(comment);
        save(all);
    }

    public static List<Comment> loadAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Comment>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void save(List<Comment> comments) {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(comments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Comment> getByDocument(String documentTitle) {
        return loadAll().stream()
                .filter(c -> c.getDocumentTitle().equalsIgnoreCase(documentTitle))
                .collect(Collectors.toList());
    }
}
