package com.example.quanlythuvien.view;

import com.example.quanlythuvien.dao.DocumentFileDAO;
import com.example.quanlythuvien.model.Document;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DocumentDetailPane extends VBox {

    private final Label titleLabel;
    private final Label authorLabel;
    private final Label categoryLabel;
    private final Label statusLabel;
    private final Label viewsLabel;
    private final Label updatedLabel;
    private final ImageView imageView;
    private Document currentDocument;

    public DocumentDetailPane() {
        setPadding(new Insets(20));
        setSpacing(20);

        titleLabel = new Label("TÊN TÀI LIỆU MẪU");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titleLabel.setWrapText(true);

        imageView = new ImageView("https://via.placeholder.com/160x220");
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(220);

        VBox imageBox = new VBox(imageView);
        imageBox.setAlignment(Pos.TOP_CENTER);
        imageBox.setPadding(new Insets(10));
        HBox.setHgrow(imageBox, Priority.ALWAYS);

        VBox infoBox = new VBox(10);
        infoBox.setPadding(new Insets(10));
        infoBox.setStyle("-fx-font-size: 15px;");

        updatedLabel = new Label();
        authorLabel = new Label();
        categoryLabel = new Label();
        statusLabel = new Label();
        viewsLabel = new Label();

        HBox actionBox = new HBox(20);
        actionBox.setAlignment(Pos.CENTER_LEFT);
        Button editBtn = new Button("Sửa");
        actionBox.getChildren().addAll(editBtn);

        editBtn.setOnAction(e -> openEditPopup());

        infoBox.getChildren().addAll(updatedLabel, authorLabel, categoryLabel, statusLabel, viewsLabel, actionBox);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        HBox topSection = new HBox(30, imageBox, infoBox);
        topSection.setAlignment(Pos.CENTER);
        HBox.setHgrow(topSection, Priority.ALWAYS);

        Label commentLabel = new Label("💬 Bình luận");
        commentLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Viết bình luận tại đây...");
        commentArea.setPrefHeight(100);
        commentArea.setWrapText(true);

        VBox commentBox = new VBox(10, commentLabel, commentArea);
        commentBox.setPadding(new Insets(10));

        getChildren().addAll(titleLabel, topSection, commentBox);
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    public void setData(Document doc) {
        this.currentDocument = doc;
        titleLabel.setText(doc.getTitle());
        authorLabel.setText("✍ Tác giả: " + (doc.getAuthor().isEmpty() ? "Không rõ" : doc.getAuthor()));
        categoryLabel.setText("📂 Thể loại: " + (doc.getCategory().isEmpty() ? "Không rõ" : doc.getCategory()));
        statusLabel.setText("📦 Tình trạng: " + (doc.getStatus().isEmpty() ? "Không rõ" : doc.getStatus()));
        viewsLabel.setText("👁️ Lượt mượn: " + doc.getViewCount());
        updatedLabel.setText("🕒 Cập nhật: " + (doc.getUpdatedAt().isEmpty() ? "Không rõ" : doc.getUpdatedAt()));

        try {
            imageView.setImage(new Image(doc.getImageUrl()));
        } catch (Exception e) {
            imageView.setImage(new Image("https://via.placeholder.com/160x220"));
        }
    }

    private void openEditPopup() {
        if (currentDocument == null) return;

        Stage popup = new Stage();
        popup.setTitle("Sửa thông tin tài liệu");

        TextField titleField = new TextField(currentDocument.getTitle());
        TextField authorField = new TextField(currentDocument.getAuthor());
        TextField categoryField = new TextField(currentDocument.getCategory());
        TextField statusField = new TextField(currentDocument.getStatus());
        TextField viewsField = new TextField(String.valueOf(currentDocument.getViewCount()));
        TextField imageUrlField = new TextField(currentDocument.getImageUrl());

        Button saveBtn = new Button("Lưu");
        saveBtn.setOnAction(e -> {
            currentDocument.setTitle(titleField.getText());
            currentDocument.setAuthor(authorField.getText());
            currentDocument.setCategory(categoryField.getText());
            currentDocument.setStatus(statusField.getText());
            currentDocument.setViewCount(Integer.parseInt(viewsField.getText()));
            currentDocument.setImageUrl(imageUrlField.getText());

            DocumentFileDAO.update(currentDocument);
            setData(currentDocument);
            popup.close();
        });

        VBox layout = new VBox(10,
                new Label("Tiêu đề:"), titleField,
                new Label("Tác giả:"), authorField,
                new Label("Thể loại:"), categoryField,
                new Label("Tình trạng:"), statusField,
                new Label("Lượt mượn:"), viewsField,
                new Label("Ảnh URL:"), imageUrlField,
                saveBtn
        );
        layout.setPadding(new Insets(20));
        layout.setPrefWidth(450);
        layout.setPrefHeight(520);

        popup.setScene(new Scene(layout));
        popup.show();
    }
}
