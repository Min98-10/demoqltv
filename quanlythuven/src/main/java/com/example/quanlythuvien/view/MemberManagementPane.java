package com.example.quanlythuvien.view;

import com.example.quanlythuvien.model.Member;
import com.example.quanlythuvien.util.MemberDataManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MemberManagementPane extends VBox {

    private final TableView<Member> table;
    private final ObservableList<Member> members;

    public MemberManagementPane() {
        setSpacing(15);
        setPadding(new Insets(20));

        Label title = new Label("👥 Quản lý thành viên");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Tìm kiếm tên đăng nhập hoặc họ tên");
        searchField.setStyle("-fx-font-size: 14px;");
        searchField.setMaxWidth(Double.MAX_VALUE);

        members = FXCollections.observableArrayList(MemberDataManager.loadMembers());

        FilteredList<Member> filtered = new FilteredList<>(members, p -> true);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String keyword = newVal.toLowerCase();
            filtered.setPredicate(m -> m.getUsername().toLowerCase().contains(keyword)
                    || m.getFullName().toLowerCase().contains(keyword));
        });

        table = new TableView<>(filtered);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-font-size: 14px;");

        TableColumn<Member, String> userCol = new TableColumn<>("Tên đăng nhập");
        userCol.setCellValueFactory(data -> data.getValue().usernameProperty());

        TableColumn<Member, String> nameCol = new TableColumn<>("Họ tên");
        nameCol.setCellValueFactory(data -> data.getValue().fullNameProperty());

        TableColumn<Member, String> dobCol = new TableColumn<>("Ngày sinh");
        dobCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getBirthDate() != null ? data.getValue().getBirthDate().toString() : ""));

        TableColumn<Member, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> data.getValue().emailProperty());

        TableColumn<Member, String> addressCol = new TableColumn<>("Địa chỉ");
        addressCol.setCellValueFactory(data -> data.getValue().addressProperty());

        TableColumn<Member, String> roleCol = new TableColumn<>("Vai trò");
        roleCol.setCellValueFactory(data -> data.getValue().roleProperty());

        TableColumn<Member, Void> actionCol = new TableColumn<>("Hành động");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("✏️");
            private final Button delBtn = new Button("🗑");
            private final HBox box = new HBox(5, editBtn, delBtn);

            {
                box.setAlignment(Pos.CENTER);

                editBtn.setOnAction(e -> {
                    Member m = getTableView().getItems().get(getIndex());
                    EditMemberDialog dialog = new EditMemberDialog(m);
                    dialog.showAndWait().ifPresent(updated -> {
                        members.set(getIndex(), updated);
                        MemberDataManager.saveMembers(members);
                    });
                });

                delBtn.setOnAction(e -> {
                    Member m = getTableView().getItems().get(getIndex());
                    members.remove(m);
                    MemberDataManager.saveMembers(members);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        table.getColumns().addAll(userCol, nameCol, dobCol, emailCol, addressCol, roleCol, actionCol);

        getChildren().addAll(title, searchField, table);
        VBox.setVgrow(table, Priority.ALWAYS);
    }
}
