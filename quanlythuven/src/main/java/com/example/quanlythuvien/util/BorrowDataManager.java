package com.example.quanlythuvien.util;

import com.example.quanlythuvien.model.BorrowRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDataManager {
    private static final String FILE_PATH = "data/borrow.dat";

    public static List<BorrowRecord> load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<BorrowRecord>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void save(List<BorrowRecord> records) {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs(); // tạo thư mục nếu chưa có

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void add(BorrowRecord record) {
        List<BorrowRecord> records = load();
        records.add(record);
        save(records);
    }

    public static List<BorrowRecord> findByUsername(String username) {
        List<BorrowRecord> all = load();
        List<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord r : all) {
            if (r.getUsername().equals(username)) result.add(r);
        }
        return result;
    }
}
