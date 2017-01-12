package com.stockexchange.client.ui.components.labels;

import javafx.scene.control.Label;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class TextLabel extends Label {
    /**
     * Creates a new TextLabel object.
     *
     * @param text DOCUMENT ME!
     */
    public TextLabel(String text) {
        super(text);
        this.getStyleClass().add("text-label");
    }
}
