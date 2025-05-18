package com.example.quanlythuvien.view;

import com.example.quanlythuvien.model.BorrowRecord;
import com.example.quanlythuvien.util.BorrowDataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BorrowManagementPane extends VBox {

    private final TableView<BorrowRecord> table;
    private final ObservableList<BorrowRecord> data;

    public BorrowManagementPane() {
        setSpacing(15);
        setPadding(new Insets(20));

        Label title = new Label("📚 Quản lý mượn/trả");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        List<BorrowRecord> records = BorrowDataManager.load();
        data = FXCollections.observableArrayList(records);

        table = new TableView<>(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-font-size: 14px;");

        TableColumn<BorrowRecord, String> userCol = new TableColumn<>("Người mượn");
        userCol.setCellValueFactory(c -> c.getValue().usernameProperty());

        TableColumn<BorrowRecord, String> docCol = new TableColumn<>("Tài liệu");
        docCol.setCellValueFactory(c -> c.getValue().documentTitleProperty());

        TableColumn<BorrowRecord, String> borrowCol = new TableColumn<>("Ngày mượn");
        borrowCol.setCellValueFactory(c -> c.getValue().borrowDateProperty());

        TableColumn<BorrowRecord, String> returnCol = new TableColumn<>("Hạn trả");
        returnCol.setCellValueFactory(c -> c.getValue().dueDateProperty());

        TableColumn<BorrowRecord, String> statusCol = new TableColumn<>("Trạng thái");
        statusCol.setCellValueFactory(c -> c.getValue().statusProperty());

        TableColumn<BorrowRecord, Integer> qtyCol = new TableColumn<>("Số lượng");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<BorrowRecord, Void> actionCol = new TableColumn<>("Hành động");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button markReturned = new Button("✅ Trả");
            private final Button extend = new Button("📆 Gia hạn");
            private final HBox box = new HBox(5, markReturned, extend);

            {
                box.setAlignment(Pos.CENTER);

                markReturned.setOnAction(e -> {
                    BorrowRecord rec = getTableView().getItems().get(getIndex());
                    rec.setStatus("Đã trả");
                    BorrowDataManager.save(table.getItems());
                    table.refresh();
                });

                extend.setOnAction(e -> {
                    BorrowRecord rec = getTableView().getItems().get(getIndex());
                    try {
                        LocalDate currentDue = LocalDate.parse(rec.getDueDate());
                        LocalDate newDue = currentDue.plusDays(7);
                        rec.setDueDate(newDue.toString());

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Gia hạn");
                        alert.setHeaderText("Đã gia hạn thêm 7 ngày cho: " + rec.getDocumentTitle());
                        alert.setContentText("Hạn mới: " + newDue.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        alert.show();

                        BorrowDataManager.save(table.getItems());
                        table.refresh();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        table.getColumns().addAll(userCol, docCol, borrowCol, returnCol, statusCol, qtyCol, actionCol);

        getChildren().addAll(title, table);
        VBox.setVgrow(table, Priority.ALWAYS);
    }
}
