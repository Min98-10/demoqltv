package com.example.quanlythuvien.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDate;

public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String role;
    private String fullName;
    private LocalDate birthDate;
    private String email;
    private String address;

    public Member(String username, String password, String role,
                  String fullName, LocalDate birthDate,
                  String email, String address) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.address = address;
    }

    // ===== Getter & Setter =====
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // ===== JavaFX Properties (optional, for TableView binding) =====
    public StringProperty usernameProperty() { return new SimpleStringProperty(username); }
    public StringProperty fullNameProperty() { return new SimpleStringProperty(fullName); }
    public ObjectProperty<LocalDate> birthDateProperty() { return new SimpleObjectProperty<>(birthDate); }
    public StringProperty emailProperty() { return new SimpleStringProperty(email); }
    public StringProperty addressProperty() { return new SimpleStringProperty(address); }
    public StringProperty roleProperty() { return new SimpleStringProperty(role); }
}
