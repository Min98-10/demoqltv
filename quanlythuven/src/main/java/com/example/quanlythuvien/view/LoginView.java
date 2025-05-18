package com.example.quanlythuvien.view;

import com.example.quanlythuvien.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginView {

    public void show(Stage stage) {
        // ===== TIÊU ĐỀ =====
        Label title = new Label("🔐 Đăng nhập hệ thống");
        title.setFont(Font.font("Arial", 22));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // ===== INPUT =====
        TextField usernameField = new TextField();
        usernameField.setPromptText("👤 Tên đăng nhập");
        usernameField.setPrefHeight(45);
        usernameField.setStyle("-fx-font-size: 14px; -fx-background-radius: 8;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("🔑 Mật khẩu");
        passwordField.setPrefHeight(45);
        passwordField.setStyle("-fx-font-size: 14px; -fx-background-radius: 8;");

        // ===== LABEL LỖI =====
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-background-color: #fdecea; -fx-padding: 8; -fx-background-radius: 6;");
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(300);
        errorLabel.setVisible(false);

        // ===== NÚT =====
        Button loginBtn = new Button("Đăng nhập");
        Button registerBtn = new Button("Đăng ký tài khoản");
        Button forgotPasswordBtn = new Button("Quên mật khẩu");

        loginBtn.setPrefHeight(40);
        loginBtn.setPrefWidth(250);
        registerBtn.setPrefHeight(40);
        registerBtn.setPrefWidth(250);

        loginBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8;");
        registerBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8;");
        forgotPasswordBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2980b9; -fx-underline: true;");

        // ===== CONTROLLER =====
        LoginController controller = new LoginController(stage);
        loginBtn.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            controller.handleLogin(user, pass, errorLabel);
            errorLabel.setVisible(true);
        });

        registerBtn.setOnAction(e -> controller.goToRegister());
        forgotPasswordBtn.setOnAction(e -> controller.handleForgotPassword());

        // ===== LAYOUT =====
        VBox inputBox = new VBox(12, usernameField, passwordField, errorLabel, loginBtn, registerBtn, forgotPasswordBtn);
        inputBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(25, title, inputBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setMaxWidth(400);
        layout.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);");

        StackPane wrapper = new StackPane(layout);
        wrapper.setPadding(new Insets(40));
        wrapper.setStyle("-fx-background-color: linear-gradient(to bottom right, #74ebd5, #ACB6E5);");

        Scene scene = new Scene(wrapper, 700, 500);
        stage.setTitle("Thư viện - Đăng nhập");
        stage.setScene(scene);
        stage.show();
    }
}
