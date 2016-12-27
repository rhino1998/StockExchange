package com.stockexchange.client.ui;

import com.stockexchange.client.Style;
import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.ui.components.StockExchangeBorder;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginUI{
	
	private Stage stage;
	
	private BorderPane border;
	
	private Scene scene;
	
	private Text title;	
	
	private Label brokerageLabel;
	private TextField brokerageField;
	
	private Label userLabel;
	private TextField userField;
	
	private Label pwLabel;
	private PasswordField pwField;
	
	
	private Button login;
	private Button cancel;
	
	
	public LoginUI(Stage stage){
		
		this.stage = stage;
		this.border = new StockExchangeBorder(stage);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		
		
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		title = new Text("Login");
		title.setFont(Style.titleFont);
		
		grid.add(title, 0, 0 ,2 ,1);
		
		
		this.brokerageLabel = new Label("Brokerage: ");
		this.brokerageField = new TextField();
		this.userLabel = new Label("Username: ");
		this.userField = new TextField();
		this.pwLabel = new Label("Password: ");
		this.pwField = new PasswordField();
		
		grid.add(brokerageLabel, 0, 2);
		grid.add(brokerageField,1, 2);
		grid.add(userLabel, 0, 3);
		grid.add(userField,1, 3);
		grid.add(pwLabel, 0, 4);
		grid.add(pwField,1, 4);
		
		grid.setStyle("-fx-background-color: tan;-fx-padding: 10px;");
		
		
		
		
		this.login = new Button("Login");
		this.login.setOnAction(new LoginButtonEvent());
		this.cancel = new Button("Cancel");
		this.cancel.setOnAction(new CancelButtonEvent());
		
		HBox btns = new HBox(10);
		btns.setAlignment(Pos.CENTER);
		btns.getChildren().addAll( this.login, this.cancel);
		grid.add(btns, 1, 5);

		this.border.setCenter(grid);
		this.scene = new Scene(this.border, Style.width, Style.height);
	}


	public Scene getScene() {
		return this.scene;
	}

	
	class LoginButtonEvent implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			double oldWidth = stage.getWidth();
			double oldHeight= stage.getHeight();
			
			Credentials cred =  new Credentials(userField.getText(), pwField.getText());
			Trader trader = AuthenticationAPI.authenticateTrader(brokerageField.getText(),cred);
			if (trader == null){
				pwField.setText("");
				//TODO error alert
				return;
			}
			System.out.println(trader.getBrokerageName() + trader.getName());
			System.out.println(trader.getToken());
			
			//TODO Scene change
			stage.setScene(Scenes.login.getScene());
			stage.sizeToScene();
			stage.setX(stage.getX()-(stage.getWidth()-oldWidth)/2);
			stage.setY(stage.getY()-(stage.getHeight()-oldHeight)/2);
			
		}
	}
	
	class CancelButtonEvent implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e) {
			double oldWidth = stage.getWidth();
			double oldHeight= stage.getHeight();
			
			stage.setScene(Scenes.welcome.getScene());
			stage.sizeToScene();
			stage.setX(stage.getX()-(stage.getWidth()-oldWidth)/2);
			stage.setY(stage.getY()-(stage.getHeight()-oldHeight)/2);
			
		}
	}


}
