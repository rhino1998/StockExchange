package com.stockexchange.client.ui.components;

import com.stockexchange.client.ui.ViewStage;
import com.stockexchange.client.ui.components.buttongroups.ManagementButtons;
import com.stockexchange.client.ui.components.buttongroups.WindowButtons;
import com.stockexchange.client.ui.components.labels.TextLabel;
import com.stockexchange.client.ui.components.text.HeaderLabel;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class ManagementBorder extends BaseBorder {

    public ManagementBorder(ViewStage win, String header) {
        super(win);
        this.left.getChildren().add(new ManagementButtons(win));
        this.center.getChildren().add(new TextLabel(header));
    }

}
