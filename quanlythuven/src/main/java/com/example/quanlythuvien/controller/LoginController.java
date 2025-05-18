package com.example.quanlythuvien.controller;

import com.example.quanlythuvien.model.Member;
import com.example.quanlythuvien.util.MemberDataManager;
import com.example.quanlythuvien.view.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

public class LoginController {
    private final Stage stage;

    public LoginController(Stage stage) {
        this.stage = stage;
    }

    public void handleLogin(String username, String password, Label errorLabel) {
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("❗ Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            return;
        }

        // ✅ Tài khoản admin cứng (mặc định)
        if (username.equals("admin") && password.equals("admin123")) {
            new MainAdminView().show(stage);
            return;
        }

        // ✅ Kiểm tra trong members.json
        List<Member> members = MemberDataManager.loadMembers();

        Member matched = members.stream()
                .filter(m -> m != null
                        && m.getUsername() != null
                        && m.getPassword() != null
                        && m.getUsername().equalsIgnoreCase(username)
                        && m.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (matched == null) {
            errorLabel.setText("❌ Sai tên đăng nhập hoặc mật khẩu.");
            return;
        }

        String role = matched.getRole() != null ? matched.getRole().toLowerCase() : "";
        switch (role) {
            case "admin" -> new MainAdminView().show(stage);
            case "user" -> new MainUserView(matched.getUsername()).show(stage);
            default -> errorLabel.setText("❗ Tài khoản không có vai trò hợp lệ.");
        }
    }

    public void goToRegister() {
        new RegisterView().show(stage);
    }
    public void handleForgotPassword() {
        ForgotPasswordView forgotView = new ForgotPasswordView();
        forgotView.show(stage);
    }

}
