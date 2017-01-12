package com.stockexchange.client.ui.login;

import com.stockexchange.client.ClientApp;
import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;

import java.net.URI;
import java.net.URL;

import java.time.LocalDate;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.inject.Inject;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class LoginPresenter implements Initializable {
    @Inject
    private LocalDate date;
    @Inject
    private ClientApp app;
    @FXML
    private TextField brokerageField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    /**
     * DOCUMENT ME!
     *
     * @param url DOCUMENT ME!
     * @param rb DOCUMENT ME!
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    @FXML
    public void onLoginLoginButtonPress(ActionEvent e) {
        Credentials cred =
            new Credentials(usernameField.getText(), passwordField.getText());
        Trader trader =
            AuthenticationAPI.authenticateTrader(brokerageField.getText(),
                    cred);

        if (trader == null) {
            passwordField.setText("");
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(ClientApp.CSS);
            dialogPane.getStyleClass().add("dialog-pane");
            dialog.setHeaderText("Invalid login or password");
            dialog.showAndWait();

            // TODO error alert
            return;
        }

        app.login(trader);
        app.gotoProfile();
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    @FXML
    public void onLoginCancelButtonPress(ActionEvent e) {
        app.gotoWelcome();
    }
}
