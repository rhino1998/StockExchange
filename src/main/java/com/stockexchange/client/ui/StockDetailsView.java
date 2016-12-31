package com.stockexchange.client.ui;

import com.stockexchange.stocks.quotes.Quote;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StockDetailsView extends View{

	private GridPane grid;
	
	
	
	public StockDetailsView(ViewStage window, Quote quote){
		super(window);
		grid = new GridPane();
		
		
		
		
	}
}
