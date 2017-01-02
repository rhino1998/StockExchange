package com.stockexchange.client.ui;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.components.ManagementBorder;
import com.stockexchange.client.ui.components.chart.StockPriceChart;
import com.stockexchange.client.ui.components.labels.TextLabel;
import com.stockexchange.client.ui.styles.Style;
import com.stockexchange.server.data.GoogleFinanceAPI;
import com.stockexchange.stocks.StockDataPoint;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.util.MoneyFormat;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StockDetailsView extends View{
	private static final int width = 950;
	private static final int height = 650;

	private GridPane grid;
	private StockPriceChart chart;
	
	
	private Timer refresher;	
	
	private final String exchange;
	private final String symbol;
	private final long refreshRate = 10000;
	
	public StockDetailsView(ViewStage win, String exchange, String symbol){
		super(win);
		this.exchange = exchange;
		this.symbol = symbol;
		
		
		Quote quote = StockExchangeAPI.getQuote(exchange, symbol);
		
		this.border = new ManagementBorder(
			window,
			String.format(
				"[%s] %s",
				quote.getSymbol(),
				quote.getName()
			)
		);
		grid = new GridPane();
		//border.setCenter(grid);
		
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		
		//Boxes to hold the lists of profile info
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		
		//First column, global names of following data
		vbox1.getChildren().add(new TextLabel(String.format("Name:")));
		vbox1.getChildren().add(new TextLabel(String.format("Symbol:")));
		vbox1.getChildren().add(new TextLabel(String.format("Bid:")));
		vbox1.getChildren().add(new TextLabel(String.format("Ask:")));
		vbox1.getChildren().add(new TextLabel(String.format("Open:")));
		vbox1.getChildren().add(new TextLabel(String.format("Close")));
		vbox1.getChildren().add(new TextLabel(String.format("Volume")));
		vbox1.getChildren().add(new TextLabel(String.format("Cap")));
		
		
		
		//Second column, with user-specific data
		vbox2.getChildren().add(new TextLabel(quote.getName()));
		vbox2.getChildren().add(new TextLabel(quote.getSymbol()));
		vbox2.getChildren().add(new TextLabel(
				MoneyFormat.shortened(quote.getBid()))
		);
		vbox2.getChildren().add(new TextLabel(
				MoneyFormat.shortened(quote.getAsk()))
		);
		vbox2.getChildren().add(new TextLabel(
				MoneyFormat.shortened(quote.getOpen()))
		);
		vbox2.getChildren().add(new TextLabel(
				MoneyFormat.shortened(quote.getPreviousClose()))
		);
		vbox2.getChildren().add(new TextLabel(
				MoneyFormat.shortened(quote.getVolume()))
		);
		vbox2.getChildren().add(new TextLabel(
				MoneyFormat.shortened(quote.getMarketCap()))
		);
		
		vbox1.setPrefSize(130,270);
		vbox2.setPrefSize(200,270);
		vbox1.setMaxSize(130, 270);
		vbox2.setMaxSize(200, 270);
		vbox1.setPadding(new Insets(15,5,0,15));
		vbox2.setPadding(new Insets(15,5,0,15));
		
		Label description = new Label(
			"\t" +
			StockExchangeAPI.getStockDescription(exchange, symbol)
			.replaceAll("  ", "\n\t")
		);
		description.setWrapText(true);
		ScrollPane descScroll = new ScrollPane(description);
		descScroll.setPadding(new Insets(15,15,15,15));
		descScroll.setFitToWidth(true);
		descScroll.setPrefSize(900, 400);
		descScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	
		chart = new StockPriceChart(
				exchange,
				symbol,
				StockExchangeAPI
				.getStockHistory(
						exchange, 
						symbol,
						0
				)
		);
		chart.setPrefSize(300, 260);
		chart.setMaxSize(300, 260);
		
		HBox chartbox = new HBox(chart);
		chartbox.setPadding(new Insets(15,15,0,0));
		chartbox.setStyle("-fx-border-color: #EFEFEF;");
		chartbox.setMaxSize(340, 260);
		chartbox.setMaxSize(340, 260);
		
		HBox tablebox = new HBox(vbox1, vbox2);
		tablebox.setStyle("-fx-border-color: #EFEFEF;");
		tablebox.setPrefSize(290, 260);
		tablebox.setMaxSize(290, 260);
		
		HBox buttonbox = new HBox();
		buttonbox.setStyle("-fx-border-color: #EFEFEF;");
		buttonbox.setPrefSize(290, 260);
		buttonbox.setMaxSize(290, 260);
		
		HBox layer1 = new HBox(tablebox, chartbox, buttonbox);
		layer1.setPrefSize(900, 400);
		layer1.setSpacing(10);
		
		
		VBox layers = new VBox(layer1, descScroll);
		layers.setPadding(new Insets(25,25,25,25));
	
		border.setCenter(layers);
		
		
		//Size and theme
		this.scene = new Scene(border, width, height);
		scene.getStylesheets().add(Style.class.getResource("style.css").toExternalForm());
	}
	
	public void start(){
		super.start();
		this.setupRefresh();
	}
	
	public void stop(){
		super.stop();
		refresher.cancel();
	}
	
	
	/**
	 * Automatically refreshes the Quote Display every Connection.refreshRate seconds.
	 */
	private void setupRefresh(){
		refresher = new Timer();
		refresher.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				Platform.runLater(new Runnable(){
					public void run() {
						update(StockExchangeAPI
								.getStockHistory(
										exchange,
										symbol,
										chart.getEnd()
								)
						);
					}
				});
			}
			
		}, 0, refreshRate);
		
	}
	
	public void update(List<StockDataPoint> data){
		this.chart.update(data);
	}
}
