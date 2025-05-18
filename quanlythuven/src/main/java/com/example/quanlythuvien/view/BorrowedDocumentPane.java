package com.example.quanlythuvien.view;

import com.example.quanlythuvien.model.BorrowRecord;
import com.example.quanlythuvien.util.BorrowDataManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.List;

public class BorrowedDocumentPane extends VBox {

    public BorrowedDocumentPane(String username) {
        setSpacing(15);
        setPadding(new Insets(20));
        setFillWidth(true);
        VBox.setVgrow(this, Priority.ALWAYS);

        Label title = new Label("📖 Tài liệu đã mượn");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox list = new VBox(15);
        list.setPadding(new Insets(10));

        List<BorrowRecord> records = BorrowDataManager.findByUsername(username);
        for (BorrowRecord record : records) {
            if (!record.getStatus().equalsIgnoreCase("Đã trả")) {
                String status = getStatusText(record);
                list.getChildren().add(createCard(record, status));
            }
        }

        ScrollPane scrollPane = new ScrollPane(list);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent;");

        getChildren().addAll(title, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }

    private String getStatusText(BorrowRecord record) {
        LocalDate due = LocalDate.parse(record.getDueDate());
        LocalDate now = LocalDate.now();

        if (now.isAfter(due)) return "🔴 Quá hạn";
        else if (now.plusDays(3).isAfter(due)) return "🟠 Sắp đến hạn";
        else return "✅ Còn hạn";
    }

    private HBox createCard(BorrowRecord record, String status) {
        VBox info = new VBox(5);
        info.getChildren().addAll(
                new Label("📘 " + record.getDocumentTitle()),
                new Label("Ngày mượn: " + record.getBorrowDate()),
                new Label("Hạn trả: " + record.getDueDate()),
                new Label("Số lượng: " + record.getQuantity()),
                new Label("Trạng thái: " + status)
        );

        Button returnBtn = new Button("🔁 Trả tài liệu");
        Button extendBtn = new Button("📆 Gia hạn +7 ngày");

        returnBtn.setOnAction(e -> {
            record.setStatus("Đã trả");
            BorrowDataManager.save(BorrowDataManager.load());
            returnBtn.setDisable(true);
            extendBtn.setDisable(true);
            info.getChildren().set(4, new Label("Trạng thái: ✅ Đã trả"));
        });

        extendBtn.setOnAction(e -> {
            LocalDate newDue = LocalDate.parse(record.getDueDate()).plusDays(7);
            record.setDueDate(newDue.toString());
            BorrowDataManager.save(BorrowDataManager.load());
            info.getChildren().set(2, new Label("Hạn trả: " + newDue));
            info.getChildren().set(4, new Label("Trạng thái: " + getStatusText(record)));
        });

        HBox btnBox = new HBox(10, returnBtn, extendBtn);
        VBox content = new VBox(10, info, btnBox);
        content.setAlignment(Pos.CENTER_LEFT);

        HBox card = new HBox(content);
        card.setStyle("-fx-border-color: #ccc; -fx-padding: 12; -fx-background-color: #fefefe;");
        card.setAlignment(Pos.CENTER_LEFT);
        return card;
    }
}
