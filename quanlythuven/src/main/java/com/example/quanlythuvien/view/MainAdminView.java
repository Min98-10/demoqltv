package com.example.quanlythuvien.view;

import com.example.quanlythuvien.model.Document;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainAdminView {

    public void show(Stage stage) {
        // ===== HEADER =====
        Label logo = new Label("📚 Thư viện");

        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Tìm kiếm tài liệu...");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        Label userLabel = new Label("Admin");
        Button logoutBtn = new Button("Đăng xuất");
        logoutBtn.setOnAction(_ -> new LoginView().show(stage));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(15, logo, searchField, spacer, userLabel, logoutBtn);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("header-bar");

        // ===== SIDEBAR =====
        VBox sidebar = new VBox();
        sidebar.setPadding(new Insets(10));
        sidebar.setPrefWidth(180);
        sidebar.getStyleClass().add("sidebar");

        // ===== CENTER CONTENT =====
        VBox centerBox = new VBox();
        centerBox.setPadding(new Insets(15));
        centerBox.setSpacing(10);
        centerBox.getStyleClass().add("content-pane");
        VBox.setVgrow(centerBox, Priority.ALWAYS);

        // ===== MENU =====
        String[] functions = {
                "Trang chủ",
                "Quản lý tài liệu",
                "Quản lý thành viên",
                "Quản lý mượn/trả",
                "Thống kê"
        };

        for (String name : functions) {
            Button btn = new Button(name);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(60);
            btn.getStyleClass().add("sidebar-button");
            VBox.setVgrow(btn, Priority.ALWAYS);

            switch (name) {
                case "Trang chủ" -> btn.setOnAction(_ -> {
                    SuggestionPane suggestionPane = new SuggestionPane(doc -> {
                        if (!com.example.quanlythuvien.dao.DocumentFileDAO.contains(doc)) {
                            com.example.quanlythuvien.dao.DocumentFileDAO.add(doc);
                        }
                        DocumentDetailPane detailPane = new DocumentDetailPane();
                        detailPane.setData(doc);
                        centerBox.getChildren().setAll(detailPane);
                    });
                    VBox.setVgrow(suggestionPane, Priority.ALWAYS);
                    centerBox.getChildren().setAll(suggestionPane);
                });

                case "Quản lý tài liệu" -> btn.setOnAction(_ -> {
                    DocumentListPane listPane = new DocumentListPane(doc -> {
                        DocumentDetailPane detailPane = new DocumentDetailPane();
                        detailPane.setData(doc);
                        centerBox.getChildren().setAll(detailPane);
                    });
                    VBox.setVgrow(listPane, Priority.ALWAYS);
                    centerBox.getChildren().setAll(listPane);
                });

                case "Quản lý thành viên" -> btn.setOnAction(_ -> {
                    MemberManagementPane memberPane = new MemberManagementPane();
                    VBox.setVgrow(memberPane, Priority.ALWAYS);
                    centerBox.getChildren().setAll(memberPane);
                });

                case "Quản lý mượn/trả" -> btn.setOnAction(_ -> {
                    BorrowManagementPane borrowPane = new BorrowManagementPane();
                    VBox.setVgrow(borrowPane, Priority.ALWAYS);
                    centerBox.getChildren().setAll(borrowPane);
                });

                case "Thống kê" -> btn.setOnAction(_ -> {
                    StatisticsPane statsPane = new StatisticsPane();
                    VBox.setVgrow(statsPane, Priority.ALWAYS);
                    centerBox.getChildren().setAll(statsPane);
                });

                default -> btn.setOnAction(_ -> centerBox.getChildren().setAll(new Label("Chức năng đang phát triển.")));
            }

            sidebar.getChildren().add(btn);
        }

        // ===== TRANG CHỦ MẶC ĐỊNH =====
        SuggestionPane defaultPane = new SuggestionPane(doc -> {
            if (!com.example.quanlythuvien.dao.DocumentFileDAO.contains(doc)) {
                com.example.quanlythuvien.dao.DocumentFileDAO.add(doc);
            }
            DocumentDetailPane detailPane = new DocumentDetailPane();
            detailPane.setData(doc);
            centerBox.getChildren().setAll(detailPane);
        });
        VBox.setVgrow(defaultPane, Priority.ALWAYS);
        centerBox.getChildren().add(defaultPane);

        // ===== ROOT GIAO DIỆN =====
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setLeft(sidebar);
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 1400, 800);
        scene.getStylesheets().add(getClass()
                .getResource("/com/example/quanlythuvien/style.css")
                .toExternalForm());

        stage.setTitle("Thư viện - Admin");
        stage.setScene(scene);
        stage.show();
    }
}
