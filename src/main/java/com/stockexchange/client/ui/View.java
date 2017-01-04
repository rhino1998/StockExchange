package com.stockexchange.client.ui;

import com.stockexchange.client.ui.styles.Style;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public abstract class View {

    protected ViewStage window;
    protected Scene scene;
    protected BorderPane border;

    /**
     * A view in the UI;
     * 
     * @param stage
     *            The stage of the window in which the view will be rendered
     */
    public View(ViewStage window) {
        this.window = window;
    }

    public Scene getScene() {
        return this.scene;
    }

    /**
     * Base implementation does nothing, but is overridden sometimes
     */
    public void start() {
    }

    /**
     * Base implementation does nothing, but is overridden sometimes
     */
    public void stop() {
    }
}
