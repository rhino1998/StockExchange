package com.stockexchange.client.ui.portfolio;

import com.stockexchange.client.ClientApp;
import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.api.TraderAPI;
import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.TimerViewModel;
import com.stockexchange.client.ui.components.tables.PortfolioController;
import com.stockexchange.client.ui.components.tables.StockQuotesController;
import com.stockexchange.client.ui.components.toolbars.ToolBarController;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;
import com.stockexchange.util.MoneyFormat;

import java.net.URI;
import java.net.URL;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
public class PortfolioPresenter implements Initializable {
    @Inject
    private LocalDate date;
    @Inject
    private ClientApp app;
    @Inject
    private TimerViewModel timerViewModel;
    @Inject
    private Trader trader;
    @FXML
    private ChoiceBox<Account> accountsChoice;
    @FXML
    private ToolBarController toolbarController;
    @FXML
    private PortfolioController portfolioTableController;
    @FXML
    private ListView<String> messages;
    @FXML
    private Label nameLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label uuidLabel;
    private Account activeAcct;


    /**
     * DOCUMENT ME!
     *
     * @param url DOCUMENT ME!
     * @param rb DOCUMENT ME!
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Trader temp = AuthenticationAPI.refresh(trader);

        if (temp !=null) {
            app.login(trader);
            trader = temp;
        } else {
            System.out.println("FAIL");
        }

        toolbarController.setTitle(trader.getName());

        portfolioTableController.update(new ArrayList<Quote>());


        accountsChoice.getItems().addAll(trader.getAccounts());

        Timer refresher = new Timer();
        refresher.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {

                        Trader temp = AuthenticationAPI.refresh(trader);

                        if (temp !=null) {
                            app.login(trader);
                            trader = temp;
                        } else {
                        }


                        if (activeAcct == null) {
                            return;
                        }

                        List<String> msgs =  TraderAPI.getMessages(trader, activeAcct);

                        if (msgs != null) {
                            messages.getItems().clear();
                            messages.getItems().addAll(msgs);
                        }


                        List<Quote> quotes =
                            TraderAPI.getPortfolio(trader, activeAcct);
                        portfolioTableController.update(quotes);
                    }
                });
            }
        }, 0, 5000);
        timerViewModel.setTimer(refresher);



        accountsChoice.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<Account>() {
            @Override
            public void changed(ObservableValue<?extends Account> arg0,
                                Account old, Account acct) {
                activeAcct = acct;
                List<Quote> quotes =
                    TraderAPI.getPortfolio(trader, activeAcct);

                portfolioTableController.update(quotes);

                List<String> msgs =  TraderAPI.getMessages(trader, activeAcct);

                if (msgs != null) {
                    messages.getItems().clear();
                    messages.getItems().addAll(msgs);
                }

                nameLabel.setText(acct.getName());
                balanceLabel.setText(MoneyFormat.shortened(acct.getBalance()));
            }
        });
    }


}
