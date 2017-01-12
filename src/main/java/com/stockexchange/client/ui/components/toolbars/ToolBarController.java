package com.stockexchange.client.ui.components.toolbars;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
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
public class ToolBarController implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    @Inject
    private Stage stage;
    @FXML
    private ToolBar toolbar;
    @FXML
    private Label toolbarTitle;

    /**
     * DOCUMENT ME!
     *
     * @param title DOCUMENT ME!
     */
    public void setTitle(String title) {
        toolbarTitle.setText(title);
    }

    /**
     * DOCUMENT ME!
     *
     * @param fxmlLocation DOCUMENT ME!
     * @param resources DOCUMENT ME!
     */
    @Override
    public void initialize(URL fxmlLocation, ResourceBundle resources) {
        toolbar.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        toolbar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
    }
}
