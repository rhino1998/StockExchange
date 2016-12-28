package com.stockexchange.client.ui.components.buttongroups;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ManagementButtons extends HBox {

	private Stage stage;
	
    public ManagementButtons(Stage theStage) {
		stage = theStage;
    	
        Button closeBtn = new Button("X");
        closeBtn.setAlignment(Pos.TOP_RIGHT);
        closeBtn.setMaxWidth(25);
        closeBtn.setMinWidth(25);
        closeBtn.setPrefWidth(25);


        closeBtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
        
        Button minimizeBtn = new Button("_");
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