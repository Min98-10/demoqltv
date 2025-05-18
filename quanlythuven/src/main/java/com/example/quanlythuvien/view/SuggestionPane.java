package com.example.quanlythuvien.view;

import com.example.quanlythuvien.dao.DocumentFileDAO;
import com.example.quanlythuvien.model.Document;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SuggestionPane extends VBox {

    private final FlowPane bookContainer = new FlowPane(15, 15);

    public SuggestionPane(Consumer<Document> onBookSelected) {
        setSpacing(10);
        setPadding(new Insets(10));

        Label titleLabel = new Label("📚 Gợi ý sách từ Open Library");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        ScrollPane scrollPane = new ScrollPane(bookContainer);
        scrollPane.setFitToWidth(true);
        bookContainer.setPadding(new Insets(10));
        bookContainer.setPrefWrapLength(1200);

        getChildren().addAll(titleLabel, scrollPane);

        fetchSuggestions("java", onBookSelected);
    }

    private void fetchSuggestions(String keyword, Consumer<Document> onBookSelected) {
        String encodedKeyword = keyword.replace(" ", "+");
        String url = "https://openlibrary.org/search.json?title=" + encodedKeyword;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        future.thenAccept(response -> {
            Platform.runLater(() -> displayBooks(response.body(), onBookSelected));
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });
    }

    private void displayBooks(String json, Consumer<Document> onBookSelected) {
        JSONObject obj = new JSONObject(json);
        JSONArray docs = obj.getJSONArray("docs");

        for (int i = 0; i < Math.min(docs.length(), 15); i++) {
            JSONObject book = docs.getJSONObject(i);
            String title = book.optString("title");
            String author = book.has("author_name") ? book.getJSONArray("author_name").optString(0) : "Unknown";
            String coverId = book.has("cover_i") ? book.get("cover_i").toString() : null;
            String imageUrl = (coverId != null)
                    ? "https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg"
                    : "https://via.placeholder.com/120x160";

            Document doc = new Document(title, author, imageUrl, "", "", 0, "", "");

            VBox bookCard = createBookCard(doc, onBookSelected);
            bookContainer.getChildren().add(bookCard);
        }
    }

    private VBox createBookCard(Document doc, Consumer<Document> onBookSelected) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #ccc; -fx-background-color: white; -fx-border-radius: 8px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 4, 0, 0, 2);");
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefWidth(160);
        card.setPrefHeight(300);

        ImageView imageView = new ImageView(new Image(doc.getImageUrl(), 120, 160, true, true));
        imageView.setCursor(javafx.scene.Cursor.HAND);

        imageView.setOnMouseClicked((MouseEvent e) -> {
            if (!DocumentFileDAO.contains(doc)) {
                DocumentFileDAO.add(doc);
            }
            onBookSelected.accept(doc); // Gọi giao diện chi tiết
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
