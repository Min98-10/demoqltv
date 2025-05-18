package com.example.quanlythuvien;

import com.example.quanlythuvien.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Khởi tạo và hiển thị giao diện đăng nhập
        LoginView loginView = new LoginView();
        loginView.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args); // Khởi động ứng dụng JavaFX
    }
}
