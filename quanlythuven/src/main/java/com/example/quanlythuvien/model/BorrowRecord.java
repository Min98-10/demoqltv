package com.example.quanlythuvien.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class BorrowRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    // transient để không ghi Property khi serialize
    private final transient StringProperty username;
    private final transient StringProperty documentTitle;
    private final transient StringProperty borrowDate;
    private final transient StringProperty dueDate;
    private final transient StringProperty status;
    private final transient SimpleIntegerProperty quantity;

    // Dữ liệu thực để ghi file
    private final String _username;
    private final String _documentTitle;
    private final String _borrowDate;
    private String _dueDate;
    private String _status;
    private int _quantity;

    public BorrowRecord(String username, String documentTitle, String borrowDate, String dueDate, String status, int quantity) {
        this._username = username;
        this._documentTitle = documentTitle;
        this._borrowDate = borrowDate;
        this._dueDate = dueDate;
        this._status = status;
        this._quantity = quantity;

        this.username = new SimpleStringProperty(username);
        this.documentTitle = new SimpleStringProperty(documentTitle);
        this.borrowDate = new SimpleStringProperty(borrowDate);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.status = new SimpleStringProperty(status);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    // === Property cho TableView ===
    public StringProperty usernameProperty() { return new SimpleStringProperty(_username); }
    public StringProperty documentTitleProperty() { return new SimpleStringProperty(_documentTitle); }
    public StringProperty borrowDateProperty() { return new SimpleStringProperty(_borrowDate); }
    public StringProperty dueDateProperty() { return new SimpleStringProperty(_dueDate); }
    public StringProperty statusProperty() { return new SimpleStringProperty(_status); }
    public SimpleIntegerProperty quantityProperty() { return new SimpleIntegerProperty(_quantity); }

    // === Getter logic ===
    public String getUsername() { return _username; }
    public String getDocumentTitle() { return _documentTitle; }
    public String getBorrowDate() { return _borrowDate; }
    public String getDueDate() { return _dueDate; }
    public String getStatus() { return _status; }
    public int getQuantity() { return _quantity; }

    // === Setter logic ===
    public void setDueDate(String newDate) {
        _dueDate = newDate;
    }

    public void setStatus(String newStatus) {
        _status = newStatus;
    }

    public void setQuantity(int newQuantity) {
        _quantity = newQuantity;
    }
}
