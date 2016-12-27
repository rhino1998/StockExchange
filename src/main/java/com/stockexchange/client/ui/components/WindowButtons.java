package com.stockexchange.client.ui.components;

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
    	
        Button closeBtn = new Button("X");
        closeBtn.setAlignment(Pos.TOP_RIGHT);

        closeBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
        
        Button minimizeBtn = new Button("_");
        minimizeBtn.setAlignment(Pos.TOP_RIGHT);

        minimizeBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                stage.setIconified(true);
            }
        });
        this.getChildren().addAll(minimizeBtn, closeBtn);
    }
}