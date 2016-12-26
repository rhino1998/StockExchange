package com.stockexchange.client.ui;

import com.stockexchange.client.Style;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeUI{
	
	private Stage stage;
	
	private Scene scene;
	
	private Text title;	
	private Button login;
	private Button register;
	
	
	public WelcomeUI(Stage stage){
		
		this.stage = stage;
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		
		
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		title = new Text("Welcome");
		title.setFont(Style.titleFont);
		
		grid.add(title, 0, 0 ,2 ,1);
		
		
		
		
		
		grid.setStyle("-fx-background-color: tan;-fx-padding: 10px;");
		this.login = new Button("Login");
		this.login.setOnAction(new LoginButtonEvent());
		
		this.register = new Button("Register");
		this.register.setOnAction(new RegisterButtonEvent());
		
		
		grid.add(login, 0, 2);
		grid.add(register, 1, 2);
		
		this.scene = new Scene(grid, Style.width, Style.height);
	}


	public Scene getScene() {
		return this.scene;
	}

	
	class LoginButtonEvent implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			stage.setScene(Scenes.login.getScene());
		}
	}
	
	class RegisterButtonEvent implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			System.out.println("Something");
			
			stage.setScene(Scenes.register.getScene());
			
		}
	}


}
