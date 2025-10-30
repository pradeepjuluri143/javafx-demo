/**
 * 
 */
package com.javafx.demo.sixth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.prism.paint.Paint;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Pradeep
 *
 */
public class LoginController {
	
	
	
	@FXML
	private TextField userName;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Label feedBack;
	
	@FXML
	public void login(ActionEvent event) throws IOException {
		String loginId = userName.getText();
		String pwd = password.getText();
		System.out.println("loginId is "+loginId);
		//This is for demo purpose only never print passwords in log
		System.out.println("password is "+pwd);
		String pwdOfUser = MyPortal.userIdPwd.get(loginId);
		if(pwd.equals(pwdOfUser)) {
			System.out.println("Login Successful");
			feedBack.setText("Login Successful, redirecting to dashboard");
			feedBack.setTextFill(Color.GREEN);
			Parent dashboard = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
			Scene dashboardScene = new Scene(dashboard);
			//Want to display Dashboard FX Scene here
			Stage currentStage = (Stage) feedBack.getScene().getWindow();
			currentStage.setTitle("Welcome - "+loginId);
			currentStage.setScene(dashboardScene);
			currentStage.show();
		}else {
			System.out.println("Login Failed, try again");
			feedBack.setText("Login Failed, Try again with valid userName and Password");
			feedBack.setTextFill(Color.RED);
		}
	}
	
	@FXML
	public void reset(ActionEvent event) {
		System.out.println("In Reset");
		userName.setText(null);
		password.setText(null);
	}
	
	

}
