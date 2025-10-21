/**
 * 
 */
package com.javafx.demo.first;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Pradeep
 *
 */
public class HelloJavaFX extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Initializing Node/UI Element/Control Element
		Label lbl = new Label("Hello World");
		Label lbl1 = new Label("Hello World");
		Label lbl2 = new Label("Hello World");
		Label lbl3 = new Label("Hello World");
		Label lbl4 = new Label("Hello World");
		Label lbl5 = new Label("Hello World");
		Label lbl6 = new Label("Hello World");
		Label lbl7 = new Label("Hello World");
		Label lbl8 = new Label("Hello World");
		Label lbl9 = new Label("Hello World");
		Label lbl10 = new Label("Hello World");
		Label lbl11 = new Label("Hello World");
		Label lbl12 = new Label("Hello World");
		Label lbl13 = new Label("Hello World");
		Label lbl14 = new Label("Hello World");
		Label lbl15 = new Label("Hello World");
		//Initializing Layout and adding nodes to layout
		//Just replace HBox with Vbox and FlowPane to see the difference
		//layout behaviors
		FlowPane root = new FlowPane();
		//To Set spacing between each node
//		root.setSpacing(10);
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(lbl3);
		root.getChildren().add(lbl4);
		root.getChildren().add(lbl5);
		root.getChildren().add(lbl6);
		root.getChildren().add(lbl7);
		root.getChildren().add(lbl8);
		root.getChildren().add(lbl9);
		root.getChildren().add(lbl10);
		root.getChildren().add(lbl11);
		root.getChildren().add(lbl12);
		root.getChildren().add(lbl13);
		root.getChildren().add(lbl14);
		root.getChildren().add(lbl15);
		//Creating Scene and Adding Layout to Scene
		Scene sc = new Scene(root);
		//creating stage and adding scene to stage
		primaryStage.setScene(sc);
		primaryStage.setTitle("My First Hello World FX App");
		primaryStage.setHeight(500);
		primaryStage.setWidth(500);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch();
	}

}
