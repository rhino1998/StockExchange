package com.stockexchange.client.ui;

import com.stockexchange.client.ui.styles.Style;
import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.ui.components.BaseBorder;
import com.stockexchange.client.ui.components.text.HeaderLabel;

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

public class WelcomeView extends View {
    private static final int width = 180;
    private static final int height = 150;

    private Label title;
    private Button login;
    private Button register;

    public WelcomeView(ViewStage window) {
        super(window);
        this.border = new BaseBorder(this.window);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        title = new HeaderLabel("Welcome");
        title.setFont(Style.titleFont);

        grid.add(title, 0, 0, 2, 1);

        this.login = new Button("Login");
        this.login.setOnAction(new LoginButtonEvent());

        this.register = new Button("Register");
        this.register.setOnAction(new RegisterButtonEvent());

        grid.add(login, 0, 2);
        grid.add(register, 1, 2);
        grid.getStyleClass().add("grid");

        this.border.setCenter(grid);

        this.scene = new Scene(this.border, width, height);
        scene.getStylesheets().add(
                getClass().getClassLoader().getResource("styles/style.css")
                        .toExternalForm());
    }

    class LoginButtonEvent implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            // Change View
            View view = new QuoteView(window, "Q", 500);
            window.setView(view);
        }
    }

    class RegisterButtonEvent implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            // Change View
            window.setView(Scenes.register);
        }
    }

}