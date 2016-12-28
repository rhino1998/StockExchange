package com.stockexchange.client.ui.components.buttons;

import javafx.scene.control.Button;

public class ToolbarButton extends Button{
	
	
	public ToolbarButton(String text){
		super(text);
		this.getStyleClass().add("toolbar_button");
	}
}
