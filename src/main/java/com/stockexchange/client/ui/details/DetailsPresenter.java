package com.stockexchange.client.ui.details;

import com.stockexchange.client.ClientApp;
import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.api.TraderAPI;
import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.orders.RemoteOrder;
import com.stockexchange.client.ui.TimerViewModel;
import com.stockexchange.client.ui.components.graphs.StockPriceGraphController;
import com.stockexchange.client.ui.components.tables.PortfolioController;
import com.stockexchange.client.ui.components.tables.StockQuotesController;
import com.stockexchange.client.ui.components.toolbars.ToolBarController;
import com.stockexchange.stocks.StockDataPoint;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;
import com.stockexchange.util.DefaultHashMap;
import com.stockexchange.util.MoneyFormat;

import java.net.URI;
import java.net.URL;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.inject.Inject;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class DetailsPresenter implements Initializable {
    @Inject
    private LocalDate date;
    @Inject
    private ClientApp app;
    @Inject
    private TimerViewModel timerViewModel;
    @Inject
    private Trader trader;
    @Inject
    private Quote quote;
    @FXML
    private ToolBarController toolbarController;
    @FXML
    private StockPriceGraphController chartController;

    @FXML
    private Label descLabel;
    @FXML
    private ScrollPane descScroll;

    @FXML
    private Label nameLabel;
    @FXML
    private Label symbolLabel;
    @FXML
    private Label askLabel;
    @FXML
    private Label bidLabel;
    @FXML
    private Label highLabel;
    @FXML
    private Label lowLabel;
    @FXML
    private Label openLabel;
    @FXML
    private Label closeLabel;
    @FXML
    private Label volumeLabel;
    @FXML
    private Label capLabel;

    @FXML
    private ChoiceBox<Account> accountsChoice;

    @FXML
    private TextField limitPriceField;

    @FXML
    private TextField quantityField;

    /**
     * DOCUMENT ME!
     *
     * @param url DOCUMENT ME!
     * @param rb DOCUMENT ME!
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        descLabel.setText("\t"
                          + StockExchangeAPI.getStockDescription(quote.getSymbol())
                          .replaceAll("  ", "\n\t"));
        descLabel.setWrapText(true);

        descScroll.setFitToWidth(true);
        descScroll.setPrefSize(900, 400);
        descScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        toolbarController.setTitle(String.format("[%s] %s",
                                   quote.getSymbol(), quote.getName()));

        accountsChoice.getItems().addAll(trader.getAccounts());

        nameLabel.setText(quote.getName());
        symbolLabel.setText(quote.getSymbol());
        askLabel.setText(String.format("%.2f", quote.getAsk()));
        bidLabel.setText(String.format("%.2f", quote.getBid()));
        highLabel.setText(String.format("%.2f", quote.getDailyHigh()));
        lowLabel.setText(String.format("%.2f", quote.getDailyLow()));
        openLabel.setText(String.format("%.2f", quote.getOpen()));
        closeLabel.setText(String.format("%.2f", quote.getPreviousClose()));
        openLabel.setText(String.format("%.2f", quote.getOpen()));
        closeLabel.setText(String.format("%.2f", quote.getPreviousClose()));
        volumeLabel.setText(String.format("%.2f", quote.getVolume()));
        capLabel.setText(MoneyFormat.shortened(quote.getMarketCap()));

        final String symbol = quote.getSymbol();

        List<StockDataPoint> data =
            StockExchangeAPI.getStockHistory(quote.getSymbol(), 0);
        chartController.update(data);

        Timer refresher = new Timer();
        refresher.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        quote = StockExchangeAPI.getQuote(symbol);

                        if (quote != null) {
                            askLabel.setText(String.format("%.2f", quote.getAsk()));
                            bidLabel.setText(String.format("%.2f", quote.getBid()));
                            highLabel.setText(String.format("%.2f", quote.getDailyHigh()));
                            lowLabel.setText(String.format("%.2f", quote.getDailyLow()));
                            openLabel.setText(String.format("%.2f", quote.getOpen()));
                            closeLabel.setText(String.format("%.2f", quote.getPreviousClose()));
                            openLabel.setText(String.format("%.2f", quote.getOpen()));
                            closeLabel.setText(String.format("%.2f", quote.getPreviousClose()));
                            volumeLabel.setText(String.format("%.2f", quote.getVolume()));
                            capLabel.setText(MoneyFormat.shortened(quote.getMarketCap()));
                        }

                        List<StockDataPoint> data =
                            StockExchangeAPI.getStockHistory(quote.getSymbol(),
                                                             0);
                        chartController.update(data);
                    }
                });
            }
        }, 0, 5000);
        timerViewModel.setTimer(refresher);
    }

    @FXML
    public void onBuyButtonPressed(ActionEvent e) {

        Double price;
        Long quantity;

        try {
            price = Double.parseDouble(limitPriceField.getText());
            quantity = Long.parseLong(quantityField.getText());
        } catch (Exception ex) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(ClientApp.CSS);
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setHeaderText("Invalid Limit Price or Quantity");
            dialog.showAndWait();
            return;
        }

        RemoteOrder rem;

        if (price < 0) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(ClientApp.CSS);
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setHeaderText("Select a price greater than or equal to 0");
            dialog.showAndWait();
            return;
        }

        if (quantity <= 0) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(ClientApp.CSS);
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setHeaderText("Select a quantity greater than 0");
            dialog.showAndWait();
            return;
        }

        if (accountsChoice.getValue() == null) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(ClientApp.CSS);
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setHeaderText("Select an account");
            dialog.showAndWait();
            return;
        }

        if (price==0) {
            rem = new RemoteOrder(quote, accountsChoice.getValue(), trader, TransactionType.BUY, quantity);
        } else {
            rem = new RemoteOrder(quote, accountsChoice.getValue(), trader, quantity, TransactionType.BUY, price);
        }

        TraderAPI.submitOrder(trader, rem);

        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(ClientApp.CSS);
        dialogPane.getStyleClass().add("dialog-pane");
        dialog.setHeaderText("Buy order submitted");
        dialog.showAndWait();
    }

    @FXML
    public void onSellButtonPressed(ActionEvent e) {

        Double price;
        Long quantity;

        try {
            price = Double.parseDouble(limitPriceField.getText());
            quantity = Long.parseLong(quantityField.getText());
        } catch (Exception ex) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);

            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(ClientApp.CSS);
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setHeaderText("Invalid Limit Price or Quantity");
            dialog.showAndWait();
            return;
        }

        RemoteOrder rem;

        if (price < 0) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(ClientApp.CSS);
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setHeaderText("Select a price greater than or equal to 0");
            dialog.showAndWait();
            return;
        }

        if (quantity <= 0) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(ClientApp.CSS);
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setHeaderText("Select a quantity greater than 0");
            dialog.showAndWait();
            return;
        }

        if (accountsChoice.getValue() == null) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(ClientApp.CSS);
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setHeaderText("Select an account");
            dialog.showAndWait();
            return;
        }

        if (price==0) {
            rem = new RemoteOrder(quote, accountsChoice.getValue(), trader, TransactionType.SELL, quantity);
        } else {
            rem = new RemoteOrder(quote, accountsChoice.getValue(), trader, quantity, TransactionType.SELL, price);
        }

        List<Quote> quotes =
            TraderAPI.getPortfolio(trader, accountsChoice.getValue());

        Map<String, Long> portfolio = new DefaultHashMap<String, Long>(0l);

        for (Quote q : quotes) {
            portfolio.put(q.getSymbol(), q.getQuantity());
        }

        if (quantity>portfolio.get(quote.getSymbol())) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(ClientApp.CSS);
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setHeaderText("Invalid quantity");
            dialog.showAndWait();
            return;
        }

        TraderAPI.submitOrder(trader, rem);

        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(ClientApp.CSS);
        dialogPane.getStyleClass().add("dialog-pane");
        dialog.setHeaderText("Sell order submitted");
        dialog.showAndWait();
    }
}
