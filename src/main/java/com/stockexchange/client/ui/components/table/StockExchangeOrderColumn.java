package com.stockexchange.client.ui.components.table;

import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.components.buttons.StockExchangeButton;
import com.stockexchange.stocks.quotes.Quote;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

public class StockExchangeOrderColumn extends TableColumn<QuoteModel, String>{
	
	
	public StockExchangeOrderColumn(Stage stage, String text){
		super(text);
		
		
		Callback<TableColumn<QuoteModel, String>, TableCell<QuoteModel, String>> cellFactory =
			new Callback<TableColumn<QuoteModel, String>, TableCell<QuoteModel, String>>()
			{
				public TableCell<QuoteModel, String> call(TableColumn<QuoteModel, String> param) {
					final TableCell<QuoteModel, String> cell = new TableCell<QuoteModel, String>(){
						final Button btn = new StockExchangeButton("ORDER");
						public void updateItem(String item, boolean empty){
							super.updateItem(item, empty);
							if (empty){
								setGraphic(null);
								setText(null);
							}else{
								btn.setOnAction(new EventHandler<ActionEvent>(){

									public void handle(ActionEvent arg0) {
										Quote quote = getTableView().getItems().get(getIndex()).getQuote();
										System.out.println(quote.getSymbol());
									}
								});
								setGraphic(btn);
								setText(null);
							}
						}
					};
					
					return cell;
				}
			
			};
		this.setCellFactory(cellFactory);
	}
}
