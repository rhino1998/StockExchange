package com.stockexchange.client.ui.register;

import com.stockexchange.client.ClientApp;
import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

import java.net.URI;
import java.net.URL;

import java.time.LocalDate;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.inject.Inject;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class RegisterPresenter implements Initializable {
    @Inject
    private LocalDate date;
    @Inject
    private ClientApp app;
    @FXML
    private TextField brokerageField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordConfirmationField;

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
    public void onRegisterRegisterButtonPress(ActionEvent e) {
        // Ensure password is consistent
        if (!passwordField.getText()
                .equals(passwordConfirmationField.getText())) {
            // TODO invalid password alert
            passwordField.setText("");
            passwordConfirmationField.setText("");

            return;
        }

        // Create transport object
        Register reg =
            new Register(nameField.getText(), usernameField.getText(),
                         passwordField.getText());
        Trader trader =
            AuthenticationAPI.registerTrader(brokerageField.getText(), reg);

        // Ensure registration was successful.
        if (trader == null) {
            passwordField.setText("");
            passwordConfirmationField.setText("");

            // TODO Error alert of some sort.
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
    public void onRegisterCancelButtonPress(ActionEvent e) {
        app.gotoWelcome();
    }
}
