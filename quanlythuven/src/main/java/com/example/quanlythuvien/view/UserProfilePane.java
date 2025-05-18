package com.example.quanlythuvien.view;

import com.example.quanlythuvien.dao.RegisterDAO;
import com.example.quanlythuvien.model.Member;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class UserProfilePane extends VBox {

    private final TextField nameField, emailField, addressField, usernameField;
    private final PasswordField currentPasswordField, newPasswordField;
    private final DatePicker birthDatePicker;
    private final Label statusLabel;
    private String currentUsername;
    private Member loadedMember;

    public UserProfilePane(String username) {
        this.currentUsername = username;

        setSpacing(15);
        setPadding(new Insets(20));

        Label title = new Label("👤 Thông tin tài khoản");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        nameField = new TextField();
        birthDatePicker = new DatePicker();
        birthDatePicker.setStyle("-fx-font-size: 14px;");

        emailField = new TextField();
        addressField = new TextField();
        usernameField = new TextField();
        currentPasswordField = new PasswordField();
        currentPasswordField.setPromptText("Nhập mật khẩu hiện tại");
        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Mật khẩu mới (nếu muốn đổi)");

        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        Button editBtn = new Button("✏️ Chỉnh sửa");
        Button saveBtn = new Button("✅ Lưu");

        setEditable(false);

        editBtn.setOnAction(e -> setEditable(true));
        saveBtn.setOnAction(e -> {
            if (!currentPasswordField.getText().equals(loadedMember.getPassword())) {
                statusLabel.setText("❌ Sai mật khẩu hiện tại.");
                return;
            }

            String newUsername = usernameField.getText().trim();
            String newPassword = newPasswordField.getText().isBlank()
                    ? currentPasswordField.getText()
                    : newPasswordField.getText();

            boolean success = RegisterDAO.updateUserFullInfo(
                    currentUsername,
                    newUsername,
                    newPassword,
                    nameField.getText().trim(),
                    birthDatePicker.getValue(),
                    emailField.getText().trim(),
                    addressField.getText().trim()
            );

            if (success) {
                statusLabel.setText("✅ Cập nhật thành công!");
                setEditable(false);

                if (!newUsername.equals(currentUsername)) {
                    currentUsername = newUsername;
                }

                Platform.runLater(() -> {
                    this.getChildren().clear();
                    new UserProfilePane(currentUsername).showIn(this);
                });

            } else {
                statusLabel.setText("❌ Lỗi khi cập nhật!");
            }
        });

        loadUserData(currentUsername);

        getChildren().addAll(
                title,
                labelAndField("Họ và tên:", nameField),
                labelAndField("Ngày sinh:", birthDatePicker),
                labelAndField("Email:", emailField),
                labelAndField("Địa chỉ:", addressField),
                labelAndField("Tên đăng nhập:", usernameField),
                labelAndField("Mật khẩu hiện tại:", currentPasswordField),
                labelAndField("Mật khẩu mới:", newPasswordField),
                new HBox(10, editBtn, saveBtn),
                statusLabel
        );
    }

    private void setEditable(boolean b) {
        nameField.setEditable(b);
        birthDatePicker.setDisable(!b);
        emailField.setEditable(b);
        addressField.setEditable(b);
        usernameField.setEditable(b);
        currentPasswordField.setEditable(b);
        newPasswordField.setEditable(b);
    }

    private void loadUserData(String username) {
        loadedMember = RegisterDAO.getUserInfo(username);
        if (loadedMember != null) {
            nameField.setText(loadedMember.getFullName());
            birthDatePicker.setValue(loadedMember.getBirthDate());
            emailField.setText(loadedMember.getEmail());
            addressField.setText(loadedMember.getAddress());
            usernameField.setText(loadedMember.getUsername());
            currentPasswordField.setText(loadedMember.getPassword());
            newPasswordField.setText("");
            setEditable(false);
        } else {
            statusLabel.setText("Không tìm thấy người dùng.");
        }
    }

    private VBox labelAndField(String labelText, Control field) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 15px;");
        return new VBox(5, label, field);
    }

    public void showIn(VBox parent) {
        parent.getChildren().setAll(this.getChildren());
    }
}
