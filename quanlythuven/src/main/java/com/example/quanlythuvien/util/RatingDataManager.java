package com.example.quanlythuvien.util;

import com.example.quanlythuvien.model.Rating;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class RatingDataManager {
    private static final String FILE_PATH = "data/ratings.dat";

    public static void add(Rating rating) {
        List<Rating> all = loadAll();
        all.add(rating);
        save(all);
    }

    public static List<Rating> loadAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Rating>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void save(List<Rating> ratings) {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(ratings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Rating> getByDocument(String documentTitle) {
        return loadAll().stream()
                .filter(r -> r.getDocumentTitle().equalsIgnoreCase(documentTitle))
                .collect(Collectors.toList());
    }

    public static double getAverageRating(String documentTitle) {
        List<Rating> ratings = getByDocument(documentTitle);
        OptionalDouble avg = ratings.stream().mapToInt(Rating::getStars).average();
        return avg.isPresent() ? Math.round(avg.getAsDouble() * 10.0) / 10.0 : 0.0;
    }
}
