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
import com.stockexchange.client.ui.components.table.buttons.factory.ColumnButtonFactory;
import com.stockexchange.client.ui.styles.Style;
import com.stockexchange.stocks.quotes.Quote;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class QuoteView extends View {
    private static final int width = 800;
    private static final int height = 500;

    private final long refreshRate;
    private final String exchange;

    private Timer refresher;

    private GridPane grid;
    private ObservableList<QuoteModel> data;

    private TextField filter;
    private TableView<QuoteModel> table;

    @SuppressWarnings( "unchecked")
    /**
     * A view containing a searchable, filterable table of stock quotes
     * @param stage The window stage in which to render this view
     * @param exchange The exchange the quotes belong to.
     * @param refreshRate How often to autorefresh the table.
     */
    public QuoteView(ViewStage win, String exchange, long refreshRate) {
        super(win);

        this.refreshRate = refreshRate;
        this.exchange = exchange;

        // Retrieve quotes from the stock server.
        List<Quote> quotes = StockExchangeAPI.getQuotes(exchange);

        // Create table and formatting containers.
        table = new TableView<QuoteModel>();
        border = new ManagementBorder(window, String.format("%s Stock Quotes",
                exchange));
        grid = new GridPane();
        filter = new TextField();

        grid.setPadding(new Insets(25, 25, 25, 25));

        // Create table format
        TableColumn<QuoteModel, String> symbolColumn = new TableColumn<QuoteModel, String>(
                "SYM");
        symbolColumn
                .setCellValueFactory(new PropertyValueFactory<QuoteModel, String>(
                        "symbol"));
        symbolColumn.setMinWidth(50);
        symbolColumn.setPrefWidth(50);

        TableColumn<QuoteModel, String> nameColumn = new TableColumn<QuoteModel, String>(
                "Corporation");
        nameColumn
                .setCellValueFactory(new PropertyValueFactory<QuoteModel, String>(
                        "name"));
        nameColumn.setMinWidth(175);
        nameColumn.setPrefWidth(175);

        TableColumn<QuoteModel, Double> askColumn = new MoneyShortColumn("ASK");
        askColumn
                .setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                        "ask"));
        askColumn.setMinWidth(50);
        askColumn.setPrefWidth(50);

        TableColumn<QuoteModel, Double> bidColumn = new MoneyShortColumn("BID");
        bidColumn
                .setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                        "bid"));
        bidColumn.setMinWidth(50);
        bidColumn.setPrefWidth(50);

        TableColumn<QuoteModel, Double> lowColumn = new MoneyShortColumn("LOW");
        lowColumn
                .setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                        "low"));
        lowColumn.setMinWidth(50);
        lowColumn.setPrefWidth(50);

        TableColumn<QuoteModel, Double> highColumn = new MoneyShortColumn(
                "HIGH");
        highColumn
                .setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                        "high"));
        highColumn.setMinWidth(50);
        highColumn.setPrefWidth(50);

        TableColumn<QuoteModel, Double> openColumn = new MoneyShortColumn(
                "OPEN");
        openColumn
                .setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                        "open"));
        openColumn.setMinWidth(50);
        openColumn.setPrefWidth(50);

        TableColumn<QuoteModel, Double> closeColumn = new MoneyShortColumn(
                "CLOSE");
        closeColumn
                .setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                        "close"));
        closeColumn.setMinWidth(50);
        closeColumn.setPrefWidth(50);

        TableColumn<QuoteModel, Double> volumeColumn = new MoneyShortColumn(
                "VOLUME");
        volumeColumn
                .setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                        "volume"));
        volumeColumn.setMinWidth(80);
        volumeColumn.setPrefWidth(80);

        TableColumn<QuoteModel, Double> marketCapColumn = new MoneyShortColumn(
                "CAP");
        marketCapColumn
                .setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                        "marketCap"));
        marketCapColumn.setMinWidth(70);
        marketCapColumn.setPrefWidth(70);

        TableColumn<QuoteModel, String> orderColumn = new ButtonColumn<QuoteModel, String>(
                window, new ColumnButtonFactory<QuoteModel>(
                        QuoteDetailsButton.class), "ORDER");
        orderColumn.setMinWidth(60);
        orderColumn.setPrefWidth(60);

        table.setEditable(false);

        // Wrap quotes as queryable objects in a list
        data = QuoteModel.marshallQuotes(quotes);
        FilteredList<QuoteModel> filterData = new FilteredList<QuoteModel>(data);

        // Enable searching
        filter.textProperty().addListener(
                new QuoteFilterChangeListener(filterData));
        // Enable sorting
        SortedList<QuoteModel> sorted = new SortedList<QuoteModel>(filterData);
        sorted.comparatorProperty().bind(table.comparatorProperty());

        // Populate table
        table.setItems(sorted);

        // Format table
        table.getColumns().addAll(symbolColumn, nameColumn, askColumn,
                bidColumn, lowColumn, highColumn, openColumn, closeColumn,
                volumeColumn, marketCapColumn, orderColumn);

        // Size table
        table.setPrefWidth(750);
        grid.add(filter, 0, 0);
        grid.add(table, 0, 1);
        border.setCenter(grid);

        scene = new Scene(border, width, height);
        scene.getStylesheets().add(
                getClass().getClassLoader().getResource("styles/style.css")
                        .toExternalForm());
    }

    public void start() {
        super.start();
        this.setupRefresh();
    }

    public void stop() {
        super.stop();
        refresher.cancel();
    }

    /**
     * Automatically refreshes the Quote Display every Connection.refreshRate seconds.
     */
    private void setupRefresh() {
        refresher = new Timer();
        refresher.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        // Grab new stock quotes
                        List<Quote> quotes = StockExchangeAPI
                                .getQuotes(exchange);
                        update(quotes);
                    }
                });
            }

        }, 0, refreshRate);

    }

    public void update(List<Quote> quotes) {
        this.data.clear();
        this.data.addAll(QuoteModel.marshallQuotes(quotes));
    }
}
