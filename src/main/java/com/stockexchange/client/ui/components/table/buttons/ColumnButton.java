package com.stockexchange.client.ui.components.table.buttons;

import com.stockexchange.client.ui.ViewStage;

import javafx.scene.control.Button;

public abstract class ColumnButton<B> extends Button {

    protected ViewStage window;
    private B item;

    public ColumnButton(ViewStage win, B item) {
        window = win;
        this.item = item;
    }
}
