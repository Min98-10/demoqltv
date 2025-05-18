package com.example.quanlythuvien.view;

import com.example.quanlythuvien.dao.DocumentFileDAO;
import com.example.quanlythuvien.model.Document;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class SuggestionPaneUser extends VBox {

    private final FlowPane bookContainer = new FlowPane(15, 15);
    private final String username;

    // ✅ Constructor đúng chuẩn
    public SuggestionPaneUser(String username) {
        this.username = username;

        setSpacing(10);
        setPadding(new Insets(10));

        Label titleLabel = new Label("📚 Gợi ý sách nổi bật");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ScrollPane scrollPane = new ScrollPane(bookContainer);
        scrollPane.setFitToWidth(true);
        bookContainer.setPadding(new Insets(10));
        bookContainer.setPrefWrapLength(1200);

        getChildren().addAll(titleLabel, scrollPane);

        loadSuggestionsFromFile();
    }

    private void loadSuggestionsFromFile() {
        List<Document> documents = DocumentFileDAO.getAll();
        for (Document doc : documents) {
            VBox bookCard = createBookCard(doc);
            bookContainer.getChildren().add(bookCard);
        }
    }

    private VBox createBookCard(Document doc) {
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

        imageView.setCursor(javafx.scene.Cursor.HAND);
        imageView.setOnMouseClicked(e -> {
            DocumentDetailPaneUser detailPane = new DocumentDetailPaneUser(doc, username);
            VBox parent = (VBox) this.getParent();
            parent.getChildren().setAll(detailPane);
        });

        Label titleLabel = new Label(doc.getTitle());
        titleLabel.setWrapText(true);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Label authorLabel = new Label("Tác giả: " + doc.getAuthor());
        authorLabel.setStyle("-fx-font-size: 12px;");

        card.getChildren().addAll(imageView, titleLabel, authorLabel);
        return card;
    }
}
