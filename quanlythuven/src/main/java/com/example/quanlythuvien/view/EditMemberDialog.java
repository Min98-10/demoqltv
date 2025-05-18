package com.example.quanlythuvien.view;

import com.example.quanlythuvien.model.Member;
import com.example.quanlythuvien.util.MemberDataManager;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.List;

public class EditMemberDialog extends Dialog<Member> {

    public EditMemberDialog(Member original) {
        setTitle("Chỉnh sửa thành viên");
        setHeaderText("Cập nhật thông tin cho: " + original.getUsername());

        ButtonType saveButtonType = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField fullNameField = new TextField(original.getFullName());
        DatePicker birthDatePicker = new DatePicker(original.getBirthDate());
        TextField emailField = new TextField(original.getEmail());
        TextField addressField = new TextField(original.getAddress());
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Để trống nếu không đổi");

        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("user", "admin");
        roleCombo.setValue(original.getRole());

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Họ tên:"), 0, 0);
        grid.add(fullNameField, 1, 0);
        grid.add(new Label("Ngày sinh:"), 0, 1);
        grid.add(birthDatePicker, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Địa chỉ:"), 0, 3);
        grid.add(addressField, 1, 3);
        grid.add(new Label("Mật khẩu mới:"), 0, 4);
        grid.add(passwordField, 1, 4);
        grid.add(new Label("Vai trò:"), 0, 5);
        grid.add(roleCombo, 1, 5);
        grid.add(errorLabel, 0, 6, 2, 1);

        getDialogPane().setContent(grid);

        final Button saveButton = (Button) getDialogPane().lookupButton(saveButtonType);
        saveButton.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            String fullName = fullNameField.getText().trim();
            LocalDate birthDate = birthDatePicker.getValue();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String role = roleCombo.getValue();

            if (fullName.isEmpty() || birthDate == null || email.isEmpty() || address.isEmpty() || role == null) {
                errorLabel.setText("❗ Vui lòng điền đầy đủ thông tin.");
                event.consume();
                return;
            }

            if (!email.equalsIgnoreCase(original.getEmail())) {
                List<Member> others = MemberDataManager.loadMembers();
                boolean emailExists = others.stream()
                        .anyMatch(m -> !m.getUsername().equals(original.getUsername())
                                && m.getEmail().equalsIgnoreCase(email));
                if (emailExists) {
                    errorLabel.setText("❗ Email đã tồn tại.");
                    event.consume();
                }
            }
        });

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String newPassword = passwordField.getText().isBlank()
                        ? original.getPassword()
                        : passwordField.getText();

                return new Member(
                        original.getUsername(),
                        newPassword,
                        roleCombo.getValue(),
                        fullNameField.getText().trim(),
                        birthDatePicker.getValue(),
                        emailField.getText().trim(),
                        addressField.getText().trim()
                );
            }
            return null;
        });
    }
}
