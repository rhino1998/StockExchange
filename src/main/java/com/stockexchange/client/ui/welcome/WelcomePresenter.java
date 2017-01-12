package com.stockexchange.client.ui.welcome;

import com.stockexchange.client.ClientApp;

import java.net.URI;
import java.net.URL;

import java.time.LocalDate;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.inject.Inject;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class WelcomePresenter implements Initializable {
    @Inject
    private LocalDate date;
    @Inject
    private ClientApp app;

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
    public void onWelcomeLoginButtonPress(ActionEvent e) {
        app.gotoLogin();
        ;
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    @FXML
    public void onWelcomeRegisterButtonPress(ActionEvent e) {
        app.gotoRegister();
    }
}
