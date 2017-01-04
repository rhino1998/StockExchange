package com.stockexchange.client.ui;

import com.stockexchange.client.ui.styles.Style;
import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.connection.Connection;
import com.stockexchange.client.ui.components.BaseBorder;
import com.stockexchange.client.ui.components.text.HeaderLabel;
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

public class LoginView extends View {
    private static final int width = 300;
    private static final int height = 230;

    private Label title;

    private Label brokerageLabel;
    private TextField brokerageField;

    private Label userLabel;
    private TextField userField;

    private Label pwLabel;
    private PasswordField pwField;

    private Button login;
    private Button cancel;

    /**
     * A Login page view
     * 
     * @param stage
     *            The window stage in which to render this view
     */
    public LoginView(ViewStage win) {
        super(win);

        this.border = new BaseBorder(win);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 25, 25, 25));

        title = new HeaderLabel("Login");
        title.setFont(Style.titleFont);

        grid.add(title, 0, 0, 2, 1);

        this.brokerageLabel = new Label("Brokerage: ");
        this.brokerageField = new TextField();
        this.userLabel = new Label("Username: ");
        this.userField = new TextField();
        this.pwLabel = new Label("Password: ");
        this.pwField = new PasswordField();

        grid.add(brokerageLabel, 0, 2);
        grid.add(brokerageField, 1, 2);
        grid.add(userLabel, 0, 3);
        grid.add(userField, 1, 3);
        grid.add(pwLabel, 0, 4);
        grid.add(pwField, 1, 4);

        this.login = new Button("Login");
        this.login.setOnAction(new LoginButtonEvent());

        this.cancel = new Button("Cancel");
        this.cancel.setOnAction(new CancelButtonEvent());

        HBox btns = new HBox(10);
        btns.setAlignment(Pos.CENTER);
        btns.getChildren().addAll(this.login, this.cancel);
        grid.add(btns, 1, 5);

        this.border.setCenter(grid);
        this.scene = new Scene(this.border, width, height);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/style.css").toExternalForm());
    }

    class LoginButtonEvent implements EventHandler< ActionEvent> {
        public void handle(ActionEvent e) {

            Credentials cred = new Credentials(userField.getText(), pwField.getText());
            Trader trader = AuthenticationAPI.authenticateTrader(brokerageField.getText(), cred);
            if (trader == null) {
                pwField.setText("");
                // TODO error alert
                return;
            }

            // Establish trader
            Connection.trader = trader;

            // Create and change scene
            Scenes.profile = new ProfileView(window, trader);
            window.setView(Scenes.profile);

        }
    }

    class CancelButtonEvent implements EventHandler< ActionEvent> {
        public void handle(ActionEvent e) {
            // Change view
            window.setView(Scenes.welcome);
        }
    }

}
