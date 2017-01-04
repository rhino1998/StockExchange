package com.stockexchange.client.ui;

import com.stockexchange.client.ui.styles.Style;
import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.connection.Connection;
import com.stockexchange.client.ui.LoginView.CancelButtonEvent;
import com.stockexchange.client.ui.LoginView.LoginButtonEvent;
import com.stockexchange.client.ui.components.BaseBorder;
import com.stockexchange.client.ui.components.text.HeaderLabel;
import com.stockexchange.traders.Trader;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterView extends View {
    private static final int width = 350;
    private static final int height = 310;

    private Label brokerageLabel;
    private TextField brokerageField;

    private Label nameLabel;
    private TextField nameField;

    private Label userLabel;
    private TextField userField;

    private Label pwLabel;
    private PasswordField pwField;
    private Label pwLabelConfirmation;
    private PasswordField pwFieldConfirmation;

    private Label title;
    private Button register;
    private Button cancel;

    public RegisterView(ViewStage window) {
        super(window);

        this.border = new BaseBorder(window);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 25, 25, 25));

        title = new HeaderLabel("Register");
        title.setFont(Style.titleFont);

        grid.add(title, 0, 0, 2, 1);

        this.brokerageLabel = new Label("Brokerage: ");
        this.brokerageField = new TextField();
        this.nameLabel = new Label("Name: ");
        this.nameField = new TextField();
        this.userLabel = new Label("Username: ");
        this.userField = new TextField();
        this.pwLabel = new Label("Password: ");
        this.pwField = new PasswordField();
        this.pwLabelConfirmation = new Label("Password Confirmation: ");
        this.pwFieldConfirmation = new PasswordField();

        grid.add(brokerageLabel, 0, 2);
        grid.add(brokerageField, 1, 2);
        grid.add(nameLabel, 0, 3);
        grid.add(nameField, 1, 3);
        grid.add(userLabel, 0, 4);
        grid.add(userField, 1, 4);
        grid.add(pwLabel, 0, 5);
        grid.add(pwField, 1, 5);
        grid.add(pwLabelConfirmation, 0, 6);
        grid.add(pwFieldConfirmation, 1, 6);

        this.register = new Button("Register");
        this.register.setOnAction(new RegisterButtonEvent());
        this.cancel = new Button("Cancel");
        this.cancel.setOnAction(new CancelButtonEvent());

        HBox btns = new HBox(10);
        btns.setAlignment(Pos.CENTER);
        btns.getChildren().addAll(this.register, this.cancel);
        grid.add(btns, 1, 7);

        this.border.setCenter(grid);

        this.scene = new Scene(this.border, width, height);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles/style.css").toExternalForm());
    }

    class RegisterButtonEvent implements EventHandler< ActionEvent> {
        public void handle(ActionEvent e) {

            // Ensure password is consistent
            if (!pwField.getText().equals(pwFieldConfirmation.getText())) {
                // TODO invalid password alert
                pwField.setText("");
                pwFieldConfirmation.setText("");
                return;
            }

            // Create transport object
            Register reg = new Register(nameField.getText(), userField.getText(), pwField.getText());
            Trader trader = AuthenticationAPI.registerTrader(brokerageField.getText(), reg);

            // Ensure registration was successful.
            if (trader == null) {
                pwField.setText("");
                pwFieldConfirmation.setText("");

                // TODO Error alert of some sort.
                return;
            }

            // Establish trader
            Connection.trader = trader;

            // Create and change View
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
