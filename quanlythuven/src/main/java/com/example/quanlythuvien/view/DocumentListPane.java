package com.example.quanlythuvien.view;

import com.example.quanlythuvien.dao.DocumentFileDAO;
import com.example.quanlythuvien.model.Document;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DocumentListPane extends VBox {
    private final GridPane grid;
    private final List<Document> allDocuments;
    private final Consumer<Document> onSelected;

    public DocumentListPane(Consumer<Document> onDocumentSelected) {
        this.onSelected = onDocumentSelected;

        setPadding(new Insets(20));
        setSpacing(15);

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("📚 Danh sách tài liệu");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button addBtn = new Button("➕ Thêm tài liệu");
        addBtn.setOnAction(e -> showAddDocumentForm());

        header.getChildren().addAll(title, addBtn);

        grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        allDocuments = new ArrayList<>(DocumentFileDAO.getAll());
        reloadGrid();

        getChildren().addAll(header, grid);
    }

    private void reloadGrid() {
        grid.getChildren().clear();
        int col = 0, row = 0;

        allDocuments.clear();
        allDocuments.addAll(DocumentFileDAO.getAll());

        for (Document doc : allDocuments) {
            VBox card = createDocumentCard(doc);
            card.setOnMouseClicked((MouseEvent e) -> onSelected.accept(doc));
            grid.add(card, col, row);
            col++;
            if (col == 6) {
                col = 0;
                row++;
            }
        }
    }

    private VBox createDocumentCard(Document doc) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #ccc; -fx-background-color: white; -fx-border-radius: 8px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 4, 0, 0, 2);");
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefWidth(160);
        card.setPrefHeight(300);

        ImageView img;
        try {
            img = new ImageView(new Image(doc.getImageUrl(), 120, 160, true, true));
        } catch (Exception e) {
            img = new ImageView(new Image("https://via.placeholder.com/120x160"));
        }

        Label title = new Label(doc.getTitle());
        title.setWrapText(true);
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        Label author = new Label(doc.getAuthor());
        author.setStyle("-fx-font-size: 12px;");

        Button deleteBtn = new Button("❌ Xoá");
        deleteBtn.setStyle("-fx-font-size: 11px;");
        deleteBtn.setOnAction(e -> {
            DocumentFileDAO.remove(doc);
            reloadGrid();
        });

        card.getChildren().addAll(img, title, author, deleteBtn);
        return card;
    }

    private void showAddDocumentForm() {
        Stage popup = new Stage();
        popup.setTitle("➕ Thêm tài liệu");

        TextField titleField = new TextField();
        titleField.setPromptText("Tên tài liệu");

        TextField authorField = new TextField();
        authorField.setPromptText("Tác giả");

        TextField imageUrlField = new TextField();
        imageUrlField.setPromptText("Link ảnh bìa (URL)");

        TextField categoryField = new TextField("Lập trình");
        TextField statusField = new TextField("Còn");
        TextArea summaryArea = new TextArea("Tóm tắt nội dung...");

        Button saveBtn = new Button("Lưu");
        saveBtn.setOnAction(e -> {
            Document newDoc = new Document(
                    titleField.getText(),
                    authorField.getText(),
                    imageUrlField.getText(),
                    categoryField.getText(),
                    statusField.getText(),
                    0,
                    "01/06/2025",
                    summaryArea.getText()
            );
            DocumentFileDAO.add(newDoc);
            reloadGrid();
            popup.close();
        });

        VBox layout = new VBox(10,
                new Label("Tên tài liệu:"), titleField,
                new Label("Tác giả:"), authorField,
                new Label("Ảnh bìa (URL):"), imageUrlField,
                new Label("Thể loại:"), categoryField,
                new Label("Tình trạng:"), statusField,
                new Label("Tóm tắt:"), summaryArea,
                saveBtn
        );
        layout.setPadding(new Insets(20));
        layout.setPrefWidth(400);

        Scene scene = new Scene(layout);
        popup.setScene(scene);
        popup.show();
    }
}
