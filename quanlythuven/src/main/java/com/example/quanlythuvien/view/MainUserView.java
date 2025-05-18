package com.example.quanlythuvien.view;

import com.example.quanlythuvien.dao.DocumentFileDAO;
import com.example.quanlythuvien.model.Document;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainUserView {
    private final String username;

    public MainUserView(String username) {
        this.username = username;
    }

    public void show(Stage stage) {
        Label logo = new Label("📚 Thư viện");
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Tìm kiếm tài liệu...");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        Label userLabel = new Label(username);
        Button logoutBtn = new Button("Đăng xuất");
        logoutBtn.setOnAction(_ -> new LoginView().show(stage));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(15, logo, searchField, spacer, userLabel, logoutBtn);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("header-bar");

        VBox sidebar = new VBox();
        sidebar.setPrefWidth(180);
        sidebar.setPadding(new Insets(10));
        sidebar.getStyleClass().add("sidebar");

        VBox centerBox = new VBox();
        centerBox.setPadding(new Insets(15));
        centerBox.setSpacing(10);
        centerBox.getStyleClass().add("content-pane");
        VBox.setVgrow(centerBox, Priority.ALWAYS);

        String[] functions = {
                "Trang chủ", "Tài liệu có sẵn", "Tài liệu đã mượn",
                "Lịch sử mượn trả", "Thông tin tài khoản"
        };

        for (String name : functions) {
            Button btn = new Button(name);
            btn.setPrefHeight(60);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.getStyleClass().add("sidebar-button");
            VBox.setVgrow(btn, Priority.ALWAYS);

            switch (name) {
                case "Trang chủ" -> {
                    SuggestionPaneUser suggestionPane = new SuggestionPaneUser(username);
                    VBox.setVgrow(suggestionPane, Priority.ALWAYS);
                    btn.setOnAction(_ -> centerBox.getChildren().setAll(suggestionPane));
                }

                case "Tài liệu có sẵn" -> {
                    btn.setOnAction(e -> {
                        DocumentListPaneUser list = new DocumentListPaneUser(doc -> {
                            Document fullDoc = DocumentFileDAO.getByTitle(doc.getTitle());
                            if (fullDoc != null) {
                                DocumentDetailPaneUser detail = new DocumentDetailPaneUser(fullDoc, username);
                                VBox.setVgrow(detail, Priority.ALWAYS);
                                centerBox.getChildren().setAll(detail);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Không tìm thấy dữ liệu sách.");
                                alert.show();
                            }
                        });
                        VBox.setVgrow(list, Priority.ALWAYS);
                        centerBox.getChildren().setAll(list);
                    });
                }

                case "Tài liệu đã mượn" -> {
                    btn.setOnAction(e -> {
                        BorrowedDocumentPane borrowed = new BorrowedDocumentPane(username);
                        VBox.setVgrow(borrowed, Priority.ALWAYS);
                        centerBox.getChildren().setAll(borrowed);
                    });
                }

                case "Lịch sử mượn trả" -> {
                    btn.setOnAction(e -> {
                        BorrowHistoryPane history = new BorrowHistoryPane(username);
                        VBox.setVgrow(history, Priority.ALWAYS);
                        centerBox.getChildren().setAll(history);
                    });
                }

                case "Thông tin tài khoản" -> {
                    btn.setOnAction(e -> {
                        UserProfilePane profile = new UserProfilePane(username);
                        VBox.setVgrow(profile, Priority.ALWAYS);
                        centerBox.getChildren().setAll(profile);
                    });
                }

                default -> btn.setOnAction(e -> centerBox.getChildren().setAll(new Label("Bạn đã chọn: " + name)));
            }

            sidebar.getChildren().add(btn);
        }

        centerBox.getChildren().add(new Label("Chọn chức năng để bắt đầu"));

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setLeft(sidebar);
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 1400, 800);
        scene.getStylesheets().add(getClass()
                .getResource("/com/example/quanlythuvien/style.css")
                .toExternalForm());

        stage.setTitle("Thư viện - Người dùng");
        stage.setScene(scene);
        stage.show();
    }
}