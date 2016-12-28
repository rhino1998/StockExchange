package com.stockexchange.client.ui.components.fields;

import javafx.scene.control.TextField;

public class StockExchangeTextField extends TextField{

	
	private static final String style = 
			"-fx-background-color:  linear-gradient(to top, #232323, #232323);"+
			"-fx-text-fill: #EFEFEF;"+
			"";
	
	
	public StockExchangeTextField(){
		super();
		this.setStyle(style);
	}
}
