package com.example.quanlythuvien.view;

import com.example.quanlythuvien.util.MemberDataManager;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ForgotPasswordView {

    public void show(Stage stage) {
        // ===== TIÊU ĐỀ =====
        Label title = new Label("🔐 Khôi phục mật khẩu");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // ===== INPUT =====
        TextField usernameField = new TextField();
        usernameField.setPromptText("👤 Tên đăng nhập");

        TextField emailField = new TextField();
        emailField.setPromptText("✉️ Email");

        PasswordField newPassField = new PasswordField();
        newPassField.setPromptText("🔑 Mật khẩu mới");
        newPassField.setVisible(false);

        PasswordField confirmPassField = new PasswordField();
        confirmPassField.setPromptText("🔁 Nhập lại mật khẩu mới");
        confirmPassField.setVisible(false);

        // ===== LABEL LỖI =====
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-background-color: #fdecea; -fx-padding: 8; -fx-background-radius: 6;");
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(350);
        errorLabel.setVisible(false);

        // ===== NÚT =====
        Button verifyBtn = new Button("Khôi phục mật khẩu");
        Button updateBtn = new Button("Cập nhật");
        updateBtn.setVisible(false);

        String btnStyle = "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8;";
        verifyBtn.setStyle(btnStyle);
        updateBtn.setStyle(btnStyle);

        verifyBtn.setPrefWidth(220);
        updateBtn.setPrefWidth(220);

        // ===== XỬ LÝ XÁC THỰC =====
        verifyBtn.setOnAction(_ -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();

            if (username.isEmpty() || email.isEmpty()) {
                errorLabel.setText("❗ Vui lòng nhập tên đăng nhập và email.");
                errorLabel.setStyle("-fx-text-fill: red; -fx-background-color: #fdecea;");
                errorLabel.setVisible(true);
                return;
            }

            newPassField.setVisible(true);
            confirmPassField.setVisible(true);
            updateBtn.setVisible(true);
            verifyBtn.setDisable(true);
            errorLabel.setVisible(false);
        });

        // ===== XỬ LÝ CẬP NHẬT MẬT KHẨU =====
        updateBtn.setOnAction(_ -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String pass1 = newPassField.getText();
            String pass2 = confirmPassField.getText();

            if (!pass1.equals(pass2)) {
                errorLabel.setText("❗ Mật khẩu nhập lại không khớp.");
                errorLabel.setStyle("-fx-text-fill: red; -fx-background-color: #fdecea;");
                errorLabel.setVisible(true);
                return;
            }

            boolean success = MemberDataManager.updatePassword(username, email, pass1);

            if (success) {
                errorLabel.setText("✅ Mật khẩu đã được cập nhật thành công!");
                errorLabel.setStyle("-fx-text-fill: green; -fx-background-color: #e6f4ea;");
                errorLabel.setVisible(true);

                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> new LoginView().show(stage));
                delay.play();
            } else {
                errorLabel.setText("❌ Không tìm thấy tài khoản phù hợp.");
                errorLabel.setStyle("-fx-text-fill: red; -fx-background-color: #fdecea;");
                errorLabel.setVisible(true);
            }
        });

        // ===== FORM =====
        VBox form = new VBox(12,
                usernameField, emailField,
                verifyBtn, newPassField, confirmPassField,
                updateBtn, errorLabel
        );
        form.setAlignment(Pos.CENTER);

        VBox layout = new VBox(25, title, form);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setMaxWidth(450);
        layout.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);");

        StackPane wrapper = new StackPane(layout);
        wrapper.setPadding(new Insets(40));
        wrapper.setStyle("-fx-background-color: linear-gradient(to bottom right, #74ebd5, #ACB6E5);");

        Scene scene = new Scene(wrapper, 750, 600);
        stage.setScene(scene);
        stage.setTitle("Thư viện - Khôi phục mật khẩu");
        stage.show();
    }
}
