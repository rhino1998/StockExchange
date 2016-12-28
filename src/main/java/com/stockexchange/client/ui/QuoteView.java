package com.stockexchange.client.ui;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.connection.Connection;
import com.stockexchange.client.data.QuoteFilterChangeListener;
import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.components.table.MoneyColumn;
import com.stockexchange.client.ui.components.table.MoneyShortColumn;
import com.stockexchange.client.ui.components.ManagementBorder;
import com.stockexchange.client.ui.components.table.ButtonColumn;
import com.stockexchange.client.ui.components.table.buttons.QuoteDetailsButton;
import com.stockexchange.client.ui.styles.Style;
import com.stockexchange.stocks.quotes.Quote;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class QuoteView {

	private final String exchange;
			
	private Timer refresher;
	
	private Stage stage;
	private Scene scene;
	private BorderPane border;
	private GridPane grid;
	private ObservableList<QuoteModel> data;
	
	
	private TextField filter;
	private TableView<QuoteModel> table;
	
	@SuppressWarnings("unchecked")
	public QuoteView(Stage theStage, List<Quote> quotes, String exchange){
		this.exchange = exchange;
		stage = theStage;
		table = new TableView<QuoteModel>();
		border = new ManagementBorder(stage, String.format("%s Stock Quotes", exchange));
		grid = new GridPane();
		filter = new TextField();
		
		
		grid.setPadding(new Insets(25,25,25,25));
		TableColumn<QuoteModel, String> symbolColumn = 
				new TableColumn<QuoteModel, String>("SYM");
		symbolColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, String>("symbol")
				);
		symbolColumn.setMinWidth(50);
		symbolColumn.setPrefWidth(50);
		
		
		TableColumn<QuoteModel, String> nameColumn = 
				new TableColumn<QuoteModel, String>("Corporation");
		nameColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, String>("name")
				);
		nameColumn.setMinWidth(175);
		nameColumn.setPrefWidth(175);
		
		
		TableColumn<QuoteModel, Double> askColumn = 
				new MoneyColumn("ASK");
		askColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("ask")
				);
		askColumn.setMinWidth(50);
		askColumn.setPrefWidth(50);
		
		
		TableColumn<QuoteModel, Double> bidColumn = 
				new MoneyColumn("BID");
		bidColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("bid")
				);
		bidColumn.setMinWidth(50);
		bidColumn.setPrefWidth(50);
		

		TableColumn<QuoteModel, Double> lowColumn = 
				new MoneyColumn("LOW");
		lowColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("low")
				);
		lowColumn.setMinWidth(50);
		lowColumn.setPrefWidth(50);
		

		TableColumn<QuoteModel, Double> highColumn = 
				new MoneyColumn("HIGH");
		highColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("high")
				);
		highColumn.setMinWidth(50);
		highColumn.setPrefWidth(50);
		
		
		TableColumn<QuoteModel, Double> openColumn = 
				new MoneyColumn("OPEN");
		openColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("open")
				);
		openColumn.setMinWidth(50);
		openColumn.setPrefWidth(50);
		

		TableColumn<QuoteModel, Double> closeColumn = 
				new MoneyColumn("CLOSE");
		closeColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("close")
				);
		closeColumn.setMinWidth(50);
		closeColumn.setPrefWidth(50);
		

		TableColumn<QuoteModel, Double> volumeColumn = 
				new MoneyShortColumn("VOLUME");
		volumeColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("volume")
				);
		volumeColumn.setMinWidth(80);
		volumeColumn.setPrefWidth(80);
		

		TableColumn<QuoteModel, Double> marketCapColumn = 
				new MoneyShortColumn("CAP");
		marketCapColumn.setCellValueFactory(
				new PropertyValueFactory<QuoteModel, Double>("marketCap")
				);
		marketCapColumn.setMinWidth(70);
		marketCapColumn.setPrefWidth(70);
		
		
		TableColumn<QuoteModel, String> orderColumn = 
				new ButtonColumn<QuoteModel, String, QuoteDetailsButton>(stage, QuoteDetailsButton.class, "ORDER");
		orderColumn.setMinWidth(60);
		orderColumn.setPrefWidth(60);
		
		table.setEditable(false);
		data = QuoteModel.marshallQuotes(quotes);
		FilteredList<QuoteModel> filterData = new FilteredList<QuoteModel>(data);
		
		
		filter.textProperty().addListener(new QuoteFilterChangeListener(filterData));
		
		SortedList<QuoteModel> sorted = new SortedList<QuoteModel>(filterData);
		
		sorted.comparatorProperty().bind(table.comparatorProperty());
		
		table.setItems(sorted);
		
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
		table.setPrefWidth(750);
		grid.add(filter, 0, 0);
		grid.add(table, 0, 1);
		border.setCenter(grid);
		scene = new Scene(border, 800, 400);
		scene.getStylesheets().add(Style.class.getResource("style.css").toExternalForm());
	}
	
	public Scene getScene(){
		return this.scene;
	}
	
	public void start(){
		this.setupRefresh();
	}
	
	public void stop(){
		refresher.cancel();
	}
	
	private void setupRefresh(){
		refresher = new Timer();
		refresher.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				Platform.runLater(new Runnable(){
					public void run() {
						List<Quote> quotes = StockExchangeAPI.getQuotes(exchange);
						update(quotes);
					}
				});
			}
			
		}, 0, Connection.refreshRate);
		
	}
	
	public void update(List<Quote> quotes){
		this.data.clear();
		this.data.addAll(QuoteModel.marshallQuotes(quotes));
	}
}
