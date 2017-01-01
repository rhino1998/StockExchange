package com.stockexchange.client.ui;

import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.ui.components.ManagementBorder;
import com.stockexchange.client.ui.components.labels.TextLabel;
import com.stockexchange.client.ui.styles.Style;
import com.stockexchange.server.data.GoogleFinanceAPI;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.util.MoneyFormat;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StockDetailsView extends View{
	private static final int width = 850;
	private static final int height = 650;

	private GridPane grid;
	
	
	
	public StockDetailsView(ViewStage win, String exchange, String symbol){
		super(win);
		
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
		border.setCenter(grid);
		
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
		
		Label description = new Label(
			"\t" +
			StockExchangeAPI.getQuoteDescription(exchange, symbol)
			.replaceAll("  ", "\n\t")
		);
		description.setWrapText(true);
		description.setPrefWidth(750);
		ScrollPane descScroll = new ScrollPane(description);
		descScroll.setPadding(new Insets(15,15,15,15));
		descScroll.setFitToWidth(true);
		descScroll.setPrefSize(800, 400);
		descScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		Image image = new Image(
				StockExchangeAPI.getQuoteChartURL(exchange, symbol),
				600,
				600,
				true,
				true
		);
		System.out.println(image.getHeight());
		grid.add(new ImageView(image), 5, 0, 14, 3);
		grid.add(descScroll, 0, 4, 16,  8);
		grid.add(vbox1, 0, 0, 1, 4);
		grid.add(vbox2, 1, 0, 2, 4);
		
		
		
		//Size and theme
		this.scene = new Scene(border, width, height);
		scene.getStylesheets().add(Style.class.getResource("style.css").toExternalForm());
	}
}
