package com.stockexchange.client.ui.components.buttongroups;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class WindowButtonsController implements Initializable {

    @Inject
    private Stage stage;


    @FXML
    public void onCloseButtonPress( ActionEvent actionEvent ) {
        Platform.exit(  );
        System.exit( 0 );
    }

    @FXML
    public void onMinimizeButtonPress( ActionEvent actionEvent ) {
        stage.setIconified( true );
    }

    @Override
    public void initialize(URL fxmlLocation, ResourceBundle resources) {
    }

}
