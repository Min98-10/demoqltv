package com.example.quanlythuvien.util;

import com.example.quanlythuvien.model.Member;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MemberDataManager {
    private static final String FILE_PATH = "data/members.json";

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    // ===== Lưu danh sách thành viên =====
    public static void saveMembers(List<Member> members) {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();

            try (Writer writer = new FileWriter(FILE_PATH)) {
                gson.toJson(members, writer);
            }
            System.out.println("✅ Ghi members.json thành công.");
        } catch (IOException e) {
            System.err.println("❌ Ghi file thất bại: " + e.getMessage());
        }
    }

    // ===== Tải danh sách thành viên =====
    public static List<Member> loadMembers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Member>>() {}.getType();
            List<Member> members = gson.fromJson(reader, listType);
            return members != null ? members : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("❌ Đọc file thất bại: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ===== Cập nhật mật khẩu theo username + email =====
    public static boolean updatePassword(String username, String email, String newPassword) {
        List<Member> members = loadMembers();
        for (Member member : members) {
            if (member.getUsername().equalsIgnoreCase(username) &&
                    member.getEmail().equalsIgnoreCase(email)) {
                member.setPassword(newPassword);
                saveMembers(members);
                return true;
            }
        }
        return false;
    }

    // ===== LocalDateAdapter nội bộ =====
    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(formatter));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
            return LocalDate.parse(json.getAsString(), formatter);
        }
    }
}
