package com.example.quanlythuvien.dao;

import com.example.quanlythuvien.model.Member;
import com.example.quanlythuvien.util.FileUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RegisterDAO {

    private static final String FILE_PATH = "data/members.json";

    public static boolean usernameExists(String username) {
        return FileUtil.readMembers(FILE_PATH).stream()
                .anyMatch(m -> m.getUsername().equalsIgnoreCase(username));
    }

    public static boolean isDuplicateInfo(String fullName, LocalDate birthDate, String email) {
        return FileUtil.readMembers(FILE_PATH).stream()
                .anyMatch(m ->
                        m.getFullName().equalsIgnoreCase(fullName)
                                || m.getBirthDate().equals(birthDate)
                                || m.getEmail().equalsIgnoreCase(email)
                );
    }

    public static boolean registerUserWithInfo(String fullName, LocalDate birthDate,
                                               String email, String address,
                                               String username, String password) {
        if (usernameExists(username)) return false;

        List<Member> members = FileUtil.readMembers(FILE_PATH);
        members.add(new Member(username, password, "user", fullName, birthDate, email, address));
        return FileUtil.writeMembers(FILE_PATH, members);
    }

    public static Member getUserInfo(String username) {
        return FileUtil.readMembers(FILE_PATH).stream()
                .filter(m -> m.getUsername().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }

    public static boolean updateUserInfo(String username, String fullName, LocalDate birthDate,
                                         String email, String address) {
        List<Member> members = FileUtil.readMembers(FILE_PATH);
        Optional<Member> opt = members.stream()
                .filter(m -> m.getUsername().equalsIgnoreCase(username))
                .findFirst();
        if (opt.isPresent()) {
            Member m = opt.get();
            m.setFullName(fullName);
            m.setBirthDate(birthDate);
            m.setEmail(email);
            m.setAddress(address);
            return FileUtil.writeMembers(FILE_PATH, members);
        }
        return false;
    }

    public static boolean updateUserFullInfo(String currentUsername, String newUsername, String newPassword,
                                             String fullName, LocalDate birthDate,
                                             String email, String address) {
        List<Member> members = FileUtil.readMembers(FILE_PATH);
        for (Member m : members) {
            if (m.getUsername().equalsIgnoreCase(currentUsername)) {
                m.setUsername(newUsername);
                m.setPassword(newPassword);
                m.setFullName(fullName);
                m.setBirthDate(birthDate);
                m.setEmail(email);
                m.setAddress(address);
                return FileUtil.writeMembers(FILE_PATH, members);
            }
        }
        return false;
    }
}
