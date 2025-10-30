/**
 * 
 */
package com.javafx.demo.sixth;

import java.util.HashMap;


Java FX GUI: Java FX Scene Builder, 
Java FX App Window Structure,
displaying text and
image, event handling, laying out nodes in scene graph, mouse events
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Pradeep
 *
 */
public class MyPortal extends Application {
	
	static Map<String, String> userIdPwd;

	@Override
	public void init() {
		System.out.println("In init");
		userIdPwd=new HashMap<String, String>();
		userIdPwd.put("admin", "admin123");
		userIdPwd.put("hema", "hema123");
		userIdPwd.put("suma", "suma123");
		System.out.println("Size of the map is "+userIdPwd.size());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/com/javafx/demo/sixth/Login.fxml"));
		Scene sc = new Scene(root);
		primaryStage.setScene(sc);
		primaryStage.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch();
	}

}
