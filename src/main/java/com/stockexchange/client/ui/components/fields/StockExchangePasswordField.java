package com.stockexchange.client.ui.components.fields;

import javafx.scene.control.PasswordField;

public class StockExchangePasswordField extends PasswordField{

	
	private static final String style = 
			"-fx-background-color:  linear-gradient(to top, #232323, #232323);"+
			"-fx-text-fill: #EFEFEF;"+
			"";
	
	
	public StockExchangePasswordField(){
		super();
		this.setStyle(style);
	}
}
