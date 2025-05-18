package com.example.quanlythuvien.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {

    // Cập nhật thông tin kết nối của bạn tại đây
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=librarydb;encrypt=false";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "your_password_here";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
