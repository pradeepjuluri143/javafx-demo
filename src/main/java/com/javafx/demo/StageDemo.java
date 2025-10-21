/**
 * 
 */
package com.javafx.demo;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Pradeep
 *
 */
public class StageDemo extends Application {
    
    // Main entry point for JavaFX application
    @Override
    public void start(Stage primaryStage) {
        // Set stage title (appears in window title bar)
        primaryStage.setTitle("Railway Reservation System");
        
        // Configure stage dimensions
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        
        // Control window resizability
        primaryStage.setResizable(true);
        
        // Set minimum size constraints
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);
        
      // Make stage visible
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
