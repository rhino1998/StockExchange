package com.stockexchange.client.ui.components.buttongroups;

import com.stockexchange.client.ui.Scenes;
import com.stockexchange.client.ui.ViewStage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ManagementButtons extends HBox {

    private ViewStage window;

    public ManagementButtons(ViewStage win) {
        window = win;

        Button profileBtn = new Button("P");
        profileBtn.setAlignment(Pos.TOP_LEFT);
        profileBtn.setMaxWidth(25);
        profileBtn.setMinWidth(25);
        profileBtn.setPrefWidth(25);

        profileBtn.setOnAction(new EventHandler< ActionEvent>() {

            public void handle(ActionEvent actionEvent) {
                window.setView(Scenes.profile);
            }
        });
        this.getChildren().addAll(profileBtn);
    }
}