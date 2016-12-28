package com.stockexchange.client.ui.components.table.buttons;

import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.components.table.events.QuoteDetailsEvent;

import javafx.scene.control.Button;

public class QuoteDetailsButton extends ColumnButton<QuoteModel>{
	
	
	public QuoteDetailsButton(){
		super();
		this.setText("Details");
	}
	
	public void setItem(QuoteModel item){
		this.setOnAction(new QuoteDetailsEvent(item));
	}
}
