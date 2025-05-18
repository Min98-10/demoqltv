package com.example.quanlythuvien.view;

import com.example.quanlythuvien.dao.DocumentFileDAO;
import com.example.quanlythuvien.model.Document;
import com.example.quanlythuvien.util.MemberDataManager;
import com.example.quanlythuvien.util.BorrowDataManager;
import com.example.quanlythuvien.model.BorrowRecord;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.List;

public class StatisticsPane extends VBox {

    public StatisticsPane() {
        setSpacing(20);
        setPadding(new Insets(30));
        setAlignment(Pos.TOP_LEFT);

        Label title = new Label("📊 Thống kê thư viện");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        int docCount = DocumentFileDAO.getAll().size();
        int userCount = MemberDataManager.loadMembers().size();
        int borrowCount = BorrowDataManager.load().size();

        String mostBorrowedTitle = "Không có dữ liệu";
        int maxBorrows = 0;
        List<Document> docs = DocumentFileDAO.getAll();
        if (!docs.isEmpty()) {
            Document topDoc = docs.stream()
                    .max(Comparator.comparingInt(Document::getViewCount))
                    .orElse(null);
            if (topDoc != null) {
                mostBorrowedTitle = topDoc.getTitle() + " (" + topDoc.getViewCount() + " lượt)";
                maxBorrows = topDoc.getViewCount();
            }
        }

        Label totalDocs = new Label("📚 Tổng tài liệu: " + docCount);
        Label totalUsers = new Label("👥 Tổng thành viên: " + userCount);
        Label totalBorrows = new Label("📖 Tổng lượt mượn: " + borrowCount);
        Label mostPopular = new Label("🔥 Tài liệu được mượn nhiều nhất: " + mostBorrowedTitle);

        totalDocs.setStyle("-fx-font-size: 15px;");
        totalUsers.setStyle("-fx-font-size: 15px;");
        totalBorrows.setStyle("-fx-font-size: 15px;");
        mostPopular.setStyle("-fx-font-size: 15px;");

        getChildren().addAll(title, totalDocs, totalUsers, totalBorrows, mostPopular);
        VBox.setVgrow(this, Priority.ALWAYS);
    }
}
