package com.stockexchange.client.ui.components.labels;

import javafx.scene.control.Label;

public class TextLabel extends Label {

    public TextLabel(String text) {
        super(text);
        this.getStyleClass().add("text-label");
    }

}
