package com.javafx.demo.third;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Simple JavaFX app that displays text (label) and an image.
 * - Shows a title label at top
 * - Shows ImageView in center
 * - Shows a small control (button) that changes the label text when clicked
 *
 * Resources: put images in src/main/resources/images/
 *
 * Run: mvn clean javafx:run
 */
public class ImageTextDisplay extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Top label (big title)
        Label titleLabel = new Label("Welcome to JavaFX — Image + Text Demo");
        titleLabel.setFont(Font.font(22));
        titleLabel.setPadding(new Insets(10));

        // Load image from resources
        // Put your image at src/ImageTextDisplay/resources/images/sample.png
        // Use getResourceAsStream so it works from both IDE and packaged jar
        Image image;
        try {
            image = new Image(getClass().getResourceAsStream("/sample.jpg"));
        } catch (Exception e) {
            // fallback: if image not found, create a tiny empty image so app still runs
            image = new Image("https://via.placeholder.com/300"); // online fallback
        }

        // ImageView to show the image
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400); // scale to fit width (height auto because preserve ratio)
        imageView.setSmooth(true);

        // Bottom small text label (status)
        Label statusLabel = new Label("Click the button to change title text.");
        statusLabel.setPadding(new Insets(8));

        // Button to demonstrate interaction
        Button changeTextBtn = new Button("Change Title");
        changeTextBtn.setOnAction(evt -> {
            titleLabel.setText("Title changed at click!");
            statusLabel.setText("You clicked the button.");
        });

        // control box for bottom-right placement
        HBox controls = new HBox(10, changeTextBtn);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER_RIGHT);

        // Root layout - BorderPane is good for top/center/bottom layout
        BorderPane root = new BorderPane();
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER); // center the top label
        root.setCenter(imageView);
        root.setBottom(new HBox(statusLabel, controls)); // simple combined bottom region
        BorderPane.setMargin(imageView, new Insets(20));

        // But better: create an HBox for bottom so status on left, controls on right
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(5, 10, 10, 10)); // top,right,bottom,left
        bottom.setSpacing(10);
        Label spacer = new Label(); // spacer will grow
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        bottom.getChildren().addAll(statusLabel, spacer, changeTextBtn);

        root.setBottom(bottom);

        // Scene and stage
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("JavaFX Text + Image Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

