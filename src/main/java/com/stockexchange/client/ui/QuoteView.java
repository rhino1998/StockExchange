package com.stockexchange.client.ui;

import java.util.List;

import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.components.StockExchangeManagementBorder;
import com.stockexchange.client.ui.components.table.StockExchangeOrderColumn;
import com.stockexchange.client.ui.components.table.StockExchangeTableColumn;
import com.stockexchange.client.ui.components.table.StockExchangeTableView;
import com.stockexchange.client.ui.styles.Style;
import com.stockexchange.stocks.quotes.Quote;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class QuoteView {

	private Stage stage;
	private Scene scene;
	private BorderPane border;
	
	private TableView<QuoteModel> table;
	
	@SuppressWarnings("unchecked")
	public QuoteView(Stage theStage, List<Quote> quotes, String exchange){
		stage = theStage;
		table = new StockExchangeTableView<QuoteModel>();
		border = new StockExchangeManagementBorder(stage, String.format("%s Stock Quotes", exchange));

		
		
		table.setPadding(new Insets(25,25,25,25));
		TableColumn<QuoteModel, String> symbolColumn = 
				new StockExchangeTableColumn<QuoteModel, String>("SYM");
		symbolColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, String>("symbol")
				);
		symbolColumn.setMinWidth(50);
		symbolColumn.setPrefWidth(50);
		
		
		TableColumn<QuoteModel, String> nameColumn = 
				new StockExchangeTableColumn<QuoteModel, String>("Corporation");
		nameColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, String>("name")
				);
		nameColumn.setMinWidth(175);
		nameColumn.setPrefWidth(175);
		
		
		TableColumn<QuoteModel, Double> askColumn = 
				new StockExchangeTableColumn<QuoteModel, Double>("ASK");
		askColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("ask")
				);
		askColumn.setMinWidth(50);
		askColumn.setPrefWidth(50);
		
		
		TableColumn<QuoteModel, Double> bidColumn = 
				new StockExchangeTableColumn<QuoteModel, Double>("BID");
		bidColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("bid")
				);
		bidColumn.setMinWidth(50);
		bidColumn.setPrefWidth(50);
		

		TableColumn<QuoteModel, Double> lowColumn = 
				new StockExchangeTableColumn<QuoteModel, Double>("LOW");
		lowColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("low")
				);
		lowColumn.setMinWidth(50);
		lowColumn.setPrefWidth(50);
		

		TableColumn<QuoteModel, Double> highColumn = 
				new StockExchangeTableColumn<QuoteModel, Double>("HIGH");
		highColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("high")
				);
		highColumn.setMinWidth(50);
		highColumn.setPrefWidth(50);
		
		
		TableColumn<QuoteModel, Double> openColumn = 
				new StockExchangeTableColumn<QuoteModel, Double>("OPEN");
		openColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("open")
				);
		openColumn.setMinWidth(50);
		openColumn.setPrefWidth(50);
		

		TableColumn<QuoteModel, Double> closeColumn = 
				new StockExchangeTableColumn<QuoteModel, Double>("CLOSE");
		closeColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("close")
				);
		closeColumn.setMinWidth(50);
		closeColumn.setPrefWidth(50);
		

		TableColumn<QuoteModel, Double> volumeColumn = 
				new StockExchangeTableColumn<QuoteModel, Double>("VOLUME");
		volumeColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("volume")
				);
		volumeColumn.setMinWidth(80);
		volumeColumn.setPrefWidth(80);
		

		TableColumn<QuoteModel, Double> marketCapColumn = 
				new StockExchangeTableColumn<QuoteModel, Double>("MARKET CAP");
		marketCapColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("marketCap")
				);
		marketCapColumn.setMinWidth(70);
		marketCapColumn.setPrefWidth(70);
		
		
		TableColumn<QuoteModel, String> orderColumn = 
				new StockExchangeOrderColumn(stage, "ORDER");
		orderColumn.setMinWidth(63);
		orderColumn.setPrefWidth(63);
		
		table.setEditable(false);
		table.setItems(QuoteModel.marshallQuotes(quotes));
		table.getColumns().addAll(
				symbolColumn,
				nameColumn,
				askColumn,
				bidColumn,
				lowColumn,
				highColumn,
				openColumn,
				closeColumn,
				volumeColumn,
				marketCapColumn,
				orderColumn
		);
		
		border.setCenter(table);
		scene = new Scene(border, 804, 400);
		scene.getStylesheets().add(Style.class.getResource("style.css").toExternalForm());
		
	}
	
	public Scene getScene(){
		return this.scene;
	}
}
