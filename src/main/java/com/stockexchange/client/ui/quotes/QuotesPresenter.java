package com.stockexchange.client.ui.quotes;

import com.stockexchange.client.ClientApp;
import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.TimerViewModel;
import com.stockexchange.client.ui.components.tables.PortfolioController;
import com.stockexchange.client.ui.components.tables.StockQuotesController;
import com.stockexchange.client.ui.components.toolbars.ToolBarController;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

import java.net.URI;
import java.net.URL;

import java.time.LocalDate;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.inject.Inject;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class QuotesPresenter implements Initializable {
    @Inject
    private LocalDate date;
    @Inject
    private ClientApp app;
    @Inject
    private TimerViewModel timerViewModel;
    @FXML
    private ToolBarController toolbarController;
    @FXML
    private StockQuotesController quoteTableController;


    /**
     * DOCUMENT ME!
     *
     * @param url DOCUMENT ME!
     * @param rb DOCUMENT ME!
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toolbarController.setTitle("Stocks");

        List<Quote> quotes = StockExchangeAPI.getQuotes();
        quoteTableController.update(quotes);

        Timer refresher = new Timer();
        refresher.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        List<Quote> quotes =
                            StockExchangeAPI.getQuotes();
                        quoteTableController.update(quotes);
                    }
                });
            }
        }, 0, 1000);
        timerViewModel.setTimer(refresher);
    }
}
