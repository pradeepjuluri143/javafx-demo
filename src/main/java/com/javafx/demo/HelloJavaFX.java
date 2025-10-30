/**
 * 
 */
package com.javafx.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Pradeep
 *
 */
public class HelloJavaFX extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create a button - this is a Node
        Button btn = new Button("नमस्ते JavaFX!");
        btn.setOnAction(e -> System.out.println("Button clicked!"));
        
        // Create a layout - also a Node
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        // Create a Scene - contains the node tree/Graph
        Scene scene = new Scene(root, 300, 250);
        
        // Set up the Stage - your application window
        primaryStage.setTitle("My First JavaFX App");
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Own App");
        primaryStage.setResizable(false);
        
        primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
