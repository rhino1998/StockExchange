package com.stockexchange.client.ui.components.table.buttons;

import com.stockexchange.client.ClientApp;

import javafx.scene.control.Button;

import javafx.stage.Stage;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  *
 * @param <B> DOCUMENT ME!
 */
public abstract class ColumnButton<B> extends Button {
    private ClientApp app;
    private B item;

    /**
     * Creates a new ColumnButton object.
     *
     * @param app DOCUMENT ME!
     * @param item DOCUMENT ME!
     */
    public ColumnButton(ClientApp app, B item) {
        this.app = app;
        this.item = item;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ClientApp getApp() {
        return app;
    }
}
