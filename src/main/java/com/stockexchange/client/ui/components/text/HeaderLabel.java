package com.stockexchange.client.ui.components.text;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class HeaderLabel extends Label{

	
	private static final String style = 
			"-fx-text-fill: #EFEFEF;"+
			"";
	
	
	public HeaderLabel(String text){
		super(text);
		this.setStyle(style);
	}
}
