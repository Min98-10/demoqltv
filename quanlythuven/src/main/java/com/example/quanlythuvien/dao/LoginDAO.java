package com.example.quanlythuvien.dao;

import com.example.quanlythuvien.model.Member;
import com.example.quanlythuvien.util.MemberDataManager;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDAO {

    private static final Logger LOGGER = Logger.getLogger(LoginDAO.class.getName());

    public static boolean checkLogin(String username, String password) {
        try {
            List<Member> members = MemberDataManager.loadMembers();
            return members.stream()
                    .anyMatch(m -> m.getUsername().equals(username) && m.getPassword().equals(password));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi kiểm tra đăng nhập: " + username, e);
            return false;
        }
    }
}
