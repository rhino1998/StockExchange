package com.stockexchange.client.ui.components.table.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ColumnButtonEvent<E> implements EventHandler<ActionEvent>{
	
	private E item;
	
	public ColumnButtonEvent(E item){
		this.item = item;
	}
	
	public void handle(ActionEvent e) {
	}

}
