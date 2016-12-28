package com.stockexchange.client.ui.components;

import com.stockexchange.client.ui.components.buttons.ToolbarButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

class WindowButtons extends HBox {

	private Stage stage;
	
    public WindowButtons(Stage theStage) {
		stage = theStage;
    	
        Button closeBtn = new ToolbarButton("X");
        closeBtn.setAlignment(Pos.TOP_RIGHT);
        closeBtn.setMaxWidth(25);
        closeBtn.setMinWidth(25);
        closeBtn.setPrefWidth(25);


        closeBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
        
        Button minimizeBtn = new ToolbarButton("_");
        minimizeBtn.setAlignment(Pos.TOP_RIGHT);
        minimizeBtn.setMaxWidth(25);
        minimizeBtn.setMinWidth(25);
        minimizeBtn.setPrefWidth(25);


        minimizeBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                stage.setIconified(true);
            }
        });
        this.getChildren().addAll(minimizeBtn, closeBtn);
    }
}