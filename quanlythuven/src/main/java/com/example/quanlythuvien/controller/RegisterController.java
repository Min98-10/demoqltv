package com.example.quanlythuvien.controller;

import com.example.quanlythuvien.dao.RegisterDAO;
import com.example.quanlythuvien.view.LoginView;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;

public class RegisterController {
    private final Stage stage;

    public RegisterController(Stage stage) {
        this.stage = stage;
    }

    public void handleFullRegister(
            String fullName,
            LocalDate birthDate,
            String email,
            String address,
            String username,
            String password,
            String confirmPassword,
            Label errorLabel
    ) {
        if (fullName.isEmpty() || birthDate == null || email.isEmpty() ||
                address.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Vui lòng điền đầy đủ thông tin.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Mật khẩu xác nhận không khớp.");
            return;
        }

        if (RegisterDAO.usernameExists(username)) {
            errorLabel.setText("Tên đăng nhập đã tồn tại.");
            return;
        }

        if (RegisterDAO.isDuplicateInfo(fullName, birthDate, email)) {
            errorLabel.setText("Họ tên, ngày sinh hoặc email đã được sử dụng.");
            return;
        }

        boolean success = RegisterDAO.registerUserWithInfo(
                fullName,
                birthDate,
                email,
                address,
                username,
                password
        );

        if (success) {
            errorLabel.setStyle("-fx-text-fill: green;");
            errorLabel.setText("Đăng ký thành công! Chuyển về đăng nhập...");
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    javafx.application.Platform.runLater(() -> new LoginView().show(stage));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText("Lỗi khi đăng ký. Vui lòng thử lại.");
        }
    }

    public void backToLogin() {
        new LoginView().show(stage);
    }
}
