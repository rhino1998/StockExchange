package com.stockexchange.client.ui.components.table.events;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  *
 * @param <E> DOCUMENT ME!
 */
public class ColumnButtonEvent<E> implements EventHandler<ActionEvent> {
    private E item;

    /**
     * Creates a new ColumnButtonEvent object.
     *
     * @param item DOCUMENT ME!
     */
    public ColumnButtonEvent(E item) {
        this.item = item;
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void handle(ActionEvent e) {
    }
}
