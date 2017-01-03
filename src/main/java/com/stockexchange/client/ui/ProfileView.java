package com.stockexchange.client.ui;

import com.stockexchange.client.ui.components.ManagementBorder;
import com.stockexchange.client.ui.components.labels.TextLabel;
import com.stockexchange.client.ui.styles.Style;
import com.stockexchange.traders.Trader;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ProfileView extends View{
	
	private static final int width = 300;
	private static final int height = 300;

	private GridPane grid;
	
	
	
	
	/**
	 * A profile window displaying basic information about the trader and serving as a dashboard
	 * @param win The primary window stage
	 * @param trader The trader whose profile this is.
	 */
	public ProfileView(ViewStage win, Trader trader){
		super(win);
		this.border = new ManagementBorder(window, String.format("Profile"));
		grid = new GridPane();
		border.setCenter(grid);
		
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(10,10,25,10));
		
		
		//Boxes to hold the lists of profile info
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		
		//First column, global names of following data
		vbox1.getChildren().add(new TextLabel(String.format("Name:")));
		vbox1.getChildren().add(new TextLabel(String.format("Username:")));
		vbox1.getChildren().add(new TextLabel(String.format("Brokerage:")));
		
		//Second column, with user-specific data
		vbox2.getChildren().add(new TextLabel(trader.getName()));
		vbox2.getChildren().add(new TextLabel(trader.getUsername()));
		vbox2.getChildren().add(new TextLabel(trader.getBrokerageName()));
		
		grid.add(new Rectangle(60,60), 0, 0);
		grid.add(vbox1, 1, 0);
		grid.add(vbox2, 2, 0);
		
		
		
		//Size and theme
		this.scene = new Scene(border, width, height);
		scene.getStylesheets().add(
			getClass()
			.getClassLoader()
			.getResource("styles/style.css")
			.toExternalForm()
		);
	}


}
