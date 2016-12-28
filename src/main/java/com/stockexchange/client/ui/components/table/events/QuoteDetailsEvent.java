package com.stockexchange.client.ui.components.table.events;

import com.stockexchange.client.data.QuoteModel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class QuoteDetailsEvent implements EventHandler<ActionEvent>{
	
	private QuoteModel item;
	
	public QuoteDetailsEvent(QuoteModel item){
		this.item = item;
	}
	
	public void handle(ActionEvent e) {
		System.out.println(item.getQuote().getSymbol());
	}

}
