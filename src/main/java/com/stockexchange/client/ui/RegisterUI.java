package com.stockexchange.client.ui;

import com.stockexchange.client.Style;
import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.ui.LoginUI.CancelButtonEvent;
import com.stockexchange.client.ui.LoginUI.LoginButtonEvent;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterUI{
	
	private Stage stage;
	
	private Scene scene;
	
	private Label brokerageLabel;
	private TextField brokerageField;
	
	private Label nameLabel;
	private TextField nameField;
	
	private Label userLabel;
	private TextField userField;
	
	private Label pwLabel;
	private PasswordField pwField;
	
	private Text title;	
	private Button register;
	private Button cancel;
	
	
	public RegisterUI(Stage stage){
		
		this.stage = stage;
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		
		
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		title = new Text("Register");
		title.setFont(Style.titleFont);
		
		grid.add(title, 0, 0 ,2 ,1);
		
		this.brokerageLabel = new Label("Brokerage: ");
		this.brokerageField = new TextField();
		this.nameLabel = new Label("Name: ");
		this.nameField = new TextField();
		this.userLabel = new Label("Username: ");
		this.userField = new TextField();
		this.pwLabel = new Label("Password: ");
		this.pwField = new PasswordField();
		
		
		grid.add(brokerageLabel, 0, 2);
		grid.add(brokerageField,1, 2);
		grid.add(nameLabel, 0, 3);
		grid.add(nameField,1, 3);
		grid.add(userLabel, 0, 4);
		grid.add(userField,1, 4);
		grid.add(pwLabel, 0, 5);
		grid.add(pwField,1, 5);
		
		
		this.register = new Button("Register");
		this.register.setOnAction(new RegisterButtonEvent());
		this.cancel = new Button("Cancel");
		this.cancel.setOnAction(new CancelButtonEvent());
		
		HBox btns = new HBox(10);
		btns.setAlignment(Pos.CENTER);
		btns.getChildren().addAll( this.register, this.cancel);
		grid.add(btns, 1, 6);
		
		
		grid.setStyle("-fx-background-color: tan;-fx-padding: 10px;");
		this.scene = new Scene(grid, Style.width, Style.height);
	}


	public Scene getScene() {
		return this.scene;
	}

	
	class RegisterButtonEvent implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			Register reg =  new Register(nameField.getText(), userField.getText(), pwField.getText());
			System.out.println(reg.getName());
			AuthenticationAPI.registerTrader(brokerageField.getText(),reg);
			stage.setScene(Scenes.register.getScene());
		}
	}
	
	class CancelButtonEvent implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			stage.setScene(Scenes.welcome.getScene());
		}
	}


}
