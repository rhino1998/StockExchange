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
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.stocks.quotes.enums.QuoteSortBy;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Register;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StockExchangeApplication extends Application{

	
	Stage theStage;
	
	
	public static void main(String[] args){
		System.out.println("sup");
		Client client = ClientBuilder.newClient();
		Connection.website = client.target("http://localhost:8080/");
		List<Quote> quotes = StockExchangeAPI.getQuotes("NMS");
		quotes.sort(QuoteSortBy.SYMBOL);
		for (Quote quote: quotes){
			System.out.println(quote.toString());
		}
		
		Trader trader = AuthenticationAPI.registerTrader("rhino", new Register("Test", "admin", "admin"));
		System.out.println(trader.getName());
		//launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		theStage = stage;
		
		
		Client client = ClientBuilder.newClient();
		
		Connection.website = client.target("http://localhost:8080/");
		Scenes.welcome = new WelcomeUI(stage);
		Scenes.login = new LoginUI(stage);
		Scenes.register = new RegisterUI(stage);
		
		
		theStage.setScene(Scenes.welcome.getScene());
		theStage.setTitle("Stock Trader 5000");
		System.out.println("hi");
		theStage.show();
		
		
		// TODO Auto-generated method stub
		
	}

}
