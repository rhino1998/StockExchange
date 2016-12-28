package com.stockexchange.client.ui.components.table;

import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.components.table.buttons.ColumnButton;
import com.stockexchange.stocks.quotes.Quote;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ButtonColumn<S,T,B extends ColumnButton<S>> extends TableColumn<S, T>{
	
	
	public ButtonColumn(
			Stage stage,
			final Class<B> btnType,
			String text
		){
		super(text);
		
		
		Callback<TableColumn<S, T>, TableCell<S, T>> cellFactory =
			new Callback<TableColumn<S, T>, TableCell<S, T>>()
			{
				public TableCell<S, T> call(TableColumn<S, T> param){
					TableCell<S, T> cell;
					try {
						cell = new TableCell<S, T>(){
							final ColumnButton<S> btn = btnType.newInstance();
							public void updateItem(T item, boolean empty){
								super.updateItem(item, empty);
								if (empty){
									setGraphic(null);
									setText(null);
								}else{
									btn.setItem(getTableView().getItems().get(getIndex()));
									setGraphic(btn);
									setText(null);
								}
							}
						};
						cell.setPadding(new Insets(1,0,1,5));
						return cell;
					} catch (InstantiationException e) {
					} catch (IllegalAccessException e) {
					}
					setGraphic(null);
					setText(null);
					return null;
				}
			
			};
		this.setCellFactory(cellFactory);
	}
}