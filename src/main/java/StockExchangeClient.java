import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.client.ui.LoginView;
import com.stockexchange.client.ui.RegisterView;
import com.stockexchange.client.ui.Scenes;
import com.stockexchange.client.ui.ViewStage;
import com.stockexchange.client.ui.WelcomeView;
import com.stockexchange.client.ui.components.BaseBorder;
import com.stockexchange.stocks.quotes.Quote;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StockExchangeClient extends Application {

    ViewStage window;

    private static final double CENTER_ON_SCREEN_X_FRACTION = 1.0f / 2;
    private static final double CENTER_ON_SCREEN_Y_FRACTION = 1.0f / 2.5;

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void start(Stage theStage) throws Exception {

        theStage.getIcons().add(
                new Image(getClass().getClassLoader()
                        .getResource("icon/app.ico").toExternalForm()));
        window = new ViewStage(theStage);
        window.getStage().initStyle(StageStyle.UNDECORATED);

        Client client = ClientBuilder.newClient();

        Connection.website = client.target("http://localhost:8080/");
        Scenes.welcome = new WelcomeView(window);
        Scenes.login = new LoginView(window);
        Scenes.register = new RegisterView(window);

        window.setView(Scenes.welcome);
        window.setTitle("Stock Trader 5000");
        window.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth())
                * CENTER_ON_SCREEN_X_FRACTION);
        window.setY((primScreenBounds.getHeight() - window.getHeight())
                * CENTER_ON_SCREEN_Y_FRACTION);

        // TODO Auto-generated method stub

    }

}
