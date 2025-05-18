package com.example.quanlythuvien.view;

import com.example.quanlythuvien.model.Member;
import com.example.quanlythuvien.util.MemberDataManager;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class RegisterView {

    public void show(Stage stage) {
        // ===== TIÊU ĐỀ =====
        Label title = new Label("📝 Đăng ký thành viên");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // ===== TRƯỜNG NHẬP =====
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Họ và tên");

        DatePicker birthDatePicker = new DatePicker();

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField addressField = new TextField();
        addressField.setPromptText("Địa chỉ");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Tên đăng nhập");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mật khẩu");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Xác nhận mật khẩu");

        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("user", "admin");
        roleCombo.setValue("user");

        // ===== LABEL THÔNG BÁO =====
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-background-color: #fdecea; -fx-padding: 8; -fx-background-radius: 6;");
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(400);
        errorLabel.setVisible(false);

        // ===== NÚT =====
        Button registerBtn = new Button("Đăng ký");
        Button backBtn = new Button("Quay lại đăng nhập");

        String btnStyle = "-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8;";
        registerBtn.setStyle(btnStyle);
        backBtn.setStyle(btnStyle);

        registerBtn.setPrefWidth(180);
        backBtn.setPrefWidth(180);

        registerBtn.setOnAction(e -> {
            String fullName = fullNameField.getText();
            LocalDate birthDate = birthDatePicker.getValue();
            String email = emailField.getText();
            String address = addressField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String role = roleCombo.getValue();

            if (fullName.isEmpty() || birthDate == null || email.isEmpty()
                    || address.isEmpty() || username.isEmpty() || password.isEmpty()
                    || confirmPassword.isEmpty() || role == null) {
                errorLabel.setStyle("-fx-text-fill: red; -fx-background-color: #fdecea;");
                errorLabel.setText("❗ Vui lòng điền đầy đủ thông tin.");
                errorLabel.setVisible(true);
                return;
            }

            if (!password.equals(confirmPassword)) {
                errorLabel.setStyle("-fx-text-fill: red; -fx-background-color: #fdecea;");
                errorLabel.setText("❗ Mật khẩu xác nhận không khớp.");
                errorLabel.setVisible(true);
                return;
            }

            List<Member> members = MemberDataManager.loadMembers();
            boolean exists = members.stream().anyMatch(m -> m.getUsername().equals(username));
            if (exists) {
                errorLabel.setStyle("-fx-text-fill: red; -fx-background-color: #fdecea;");
                errorLabel.setText("❗ Tên đăng nhập đã tồn tại.");
                errorLabel.setVisible(true);
                return;
            }

            Member newMember = new Member(username, password, role, fullName, birthDate, email, address);
            members.add(newMember);
            MemberDataManager.saveMembers(members);

            errorLabel.setStyle("-fx-text-fill: green; -fx-background-color: #e6f4ea;");
            errorLabel.setText("✅ Đăng ký thành công!");
            errorLabel.setVisible(true);
        });

        backBtn.setOnAction(e -> new LoginView().show(stage));

        // ===== FORM =====
        VBox form = new VBox(12,
                fullNameField, birthDatePicker,
                emailField, addressField,
                new Separator(),
                usernameField, passwordField, confirmPasswordField, roleCombo
        );
        form.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(20, registerBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, title, form, buttons, errorLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);");
        layout.setMaxWidth(450);

        StackPane wrapper = new StackPane(layout);
        wrapper.setPadding(new Insets(40));
        wrapper.setStyle("-fx-background-color: linear-gradient(to bottom right, #74ebd5, #ACB6E5);");

        Scene scene = new Scene(wrapper, 750, 650);
        stage.setScene(scene);
        stage.setTitle("Thư viện - Đăng ký thành viên");
        stage.show();
    }
}
