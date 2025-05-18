package com.example.quanlythuvien.view;

import com.example.quanlythuvien.dao.DocumentFileDAO;
import com.example.quanlythuvien.model.Document;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Consumer;

public class DocumentListPaneUser extends VBox {
    private final GridPane grid;

    public DocumentListPaneUser(Consumer<Document> onDocumentSelected) {
        setPadding(new Insets(20));
        setSpacing(15);

        Label title = new Label("📚 Danh sách tài liệu");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        List<Document> documents = DocumentFileDAO.getAll();

        int col = 0, row = 0;
        for (Document doc : documents) {
            VBox card = createDocumentCard(doc);
            card.setOnMouseClicked((MouseEvent e) -> onDocumentSelected.accept(doc));
            grid.add(card, col, row);
            col++;
            if (col == 5) {
                col = 0;
                row++;
            }
        }

        getChildren().addAll(title, grid);
    }

    private VBox createDocumentCard(Document doc) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #ccc; -fx-background-color: white; -fx-border-radius: 8px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 4, 0, 0, 2);");
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefWidth(160);
        card.setPrefHeight(300);

        ImageView imageView = new ImageView();
        if (doc.getImageUrl() != null && !doc.getImageUrl().isEmpty()) {
            imageView.setImage(new Image(doc.getImageUrl(), 120, 160, true, true));
        }

        Label titleLabel = new Label(doc.getTitle());
        titleLabel.setWrapText(true);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Label authorLabel = new Label("Tác giả: " + doc.getAuthor());
        authorLabel.setStyle("-fx-font-size: 12px;");

        card.getChildren().addAll(imageView, titleLabel, authorLabel);
        return card;
    }
}
