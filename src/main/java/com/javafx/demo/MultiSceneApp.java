/**
 * 
 */
package com.javafx.demo;



import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MultiSceneApp extends Application {
    
    private Stage primaryStage;
    private Scene loginScene, dashboardScene, settingsScene;
    
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        
        // Create Scene 1: Login
        VBox loginLayout = new VBox(10);
        loginLayout.setAlignment(Pos.CENTER);
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> showDashboard());
        loginLayout.getChildren().addAll(username, password, loginBtn);
        loginScene = new Scene(loginLayout, 600, 400);
        
        // Create Scene 2: Dashboard
        BorderPane dashLayout = new BorderPane();
        Label welcomeLabel = new Label("Welcome to Dashboard!");
        Button settingsBtn = new Button("Go to Settings");
        settingsBtn.setOnAction(e -> showSettings());
        dashLayout.setCenter(welcomeLabel);
        dashLayout.setBottom(settingsBtn);
        dashboardScene = new Scene(dashLayout, 600, 400);
        
        // Create Scene 3: Settings
        VBox settingsLayout = new VBox(15);
        CheckBox notifications = new CheckBox("Enable Notifications");
        Button backBtn = new Button("Back to Dashboard");
        backBtn.setOnAction(e -> showDashboard());
        settingsLayout.getChildren().addAll(notifications, backBtn);
        settingsScene = new Scene(settingsLayout, 600, 400);
        
        // Start with login scene
        primaryStage.setTitle("Multi-Scene Application");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
    
    private void showDashboard() {
        primaryStage.setScene(dashboardScene);
    }
    
    private void showSettings() {
        primaryStage.setScene(settingsScene);
    }
    
    public static void main(String[] args) {
    	launch(args);
    }
}
