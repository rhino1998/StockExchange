import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.connection.Connection;
import com.stockexchange.client.ui.LoginUI;
import com.stockexchange.client.ui.RegisterUI;
import com.stockexchange.client.ui.Scenes;
import com.stockexchange.client.ui.WelcomeUI;
import com.stockexchange.client.ui.components.BaseBorder;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.stocks.quotes.enums.QuoteSortBy;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Register;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StockExchangeClient extends Application{

	
	Stage stage;
	
	private static final double CENTER_ON_SCREEN_X_FRACTION = 1.0f / 2;
	private static final double CENTER_ON_SCREEN_Y_FRACTION = 1.0f / 2.5;

	
	public static void main(String[] args){
		launch(args);
	}
	
	public void start(Stage theStage) throws Exception {
		stage = theStage;
		stage.initStyle(StageStyle.UNDECORATED);

        
		
		
		Client client = ClientBuilder.newClient();
		
		Connection.website = client.target("http://localhost:8080/");
		Scenes.welcome = new WelcomeUI(stage);
		Scenes.login = new LoginUI(stage);
		Scenes.register = new RegisterUI(stage);
		
		stage.setScene(Scenes.welcome.getScene());
		stage.setTitle("Stock Trader 5000");
		stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getBounds();
        stage.setX((primScreenBounds.getWidth()-stage.getWidth()) * CENTER_ON_SCREEN_X_FRACTION);
        stage.setY((primScreenBounds.getHeight()-stage.getHeight()) * CENTER_ON_SCREEN_Y_FRACTION);
		System.out.println("hi");		
		
		// TODO Auto-generated method stub
		
	}

}
