package com.stockexchange.client.ui.components;

import com.stockexchange.client.ui.components.buttongroups.WindowButtons;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class BaseBorder extends BorderPane{
	
	private Stage stage;
    private double xOffset = 0 ;
    private double yOffset = 0;
    
    protected HBox left, center, right;
	
	public BaseBorder(Stage theStage){
		super();
		stage = theStage;
		ToolBar toolBar = new ToolBar();
		
		int height = 28;
        toolBar.setPrefHeight(height);
        toolBar.setMinHeight(height);
        toolBar.setMaxHeight(height);
        toolBar.setPadding(new Insets(0, 1, 1, 1));

        left = new HBox();
        center = new HBox();
        right = new WindowButtons(stage);
        
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(center, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);
        left.setAlignment(Pos.CENTER_LEFT);
        center.setAlignment(Pos.CENTER);
        right.setAlignment(Pos.CENTER_RIGHT);
        
        toolBar.getItems().addAll(left, center, right);

        this.setTop(toolBar);
        
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        
        toolBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        
        toolBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
	}
	
}