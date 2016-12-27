package com.stockexchange.client.ui;

import com.stockexchange.client.Style;
import com.stockexchange.client.ui.components.StockExchangeBorder;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeUI{
	
	
	private static int width = 150;
	private static int height = 120;
	
	private Stage stage;
	private BorderPane border;
	
	private Scene scene;
	
	private Text title;	
	private Button login;
	private Button register;
	
	
	public WelcomeUI(Stage stage){
		
		this.stage = stage;
		this.border = new StockExchangeBorder(this.stage);
		
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
		this.border.setCenter(grid);
		
		
		this.scene = new Scene(this.border, WelcomeUI.width, WelcomeUI.height);
	}


	public Scene getScene() {
		return this.scene;
	}

	
	class LoginButtonEvent implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			double oldWidth = stage.getWidth();
			double oldHeight= stage.getHeight();
			stage.setScene(Scenes.login.getScene());
			stage.sizeToScene();
			stage.setX(stage.getX()-(stage.getWidth()-oldWidth)/2);
			stage.setY(stage.getY()-(stage.getHeight()-oldHeight)/2);
		}
	}
	
	class RegisterButtonEvent implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			double oldWidth = stage.getWidth();
			double oldHeight= stage.getHeight();
			
			stage.setScene(Scenes.register.getScene());
			stage.sizeToScene();
			stage.setX(stage.getX()-(stage.getWidth()-oldWidth)/2);
			stage.setY(stage.getY()-(stage.getHeight()-oldHeight)/2);			
			
		}
	}


}
