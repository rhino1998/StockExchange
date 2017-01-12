package com.stockexchange.client.ui.components.buttongroups;

import com.stockexchange.client.ClientApp;
import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.traders.Trader;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;

import javax.inject.Inject;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ManagementButtonsController implements Initializable {
    @Inject
    private Stage stage;
    @Inject
    private ClientApp app;
    @Inject
    private Trader trader;
    @FXML
    private Button close;

    /**
     * DOCUMENT ME!
     *
     * @param actionEvent DOCUMENT ME!
     */
    @FXML
    public void onProfileButtonPress(ActionEvent actionEvent) {
        app.gotoProfile();
    }

    @FXML
    public void onQuotesButtonPress(ActionEvent actionEvent) {
        app.gotoQuotes();
    }

    @FXML
    public void onLogoutButtonPress(ActionEvent actionEvent) {
        AuthenticationAPI.logoutTrader(trader);
        app.gotoWelcome();
    }

    /**
     * DOCUMENT ME!
     *
     * @param fxmlLocation DOCUMENT ME!
     * @param resources DOCUMENT ME!
     */
    @Override
    public void initialize(URL fxmlLocation, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }
}
