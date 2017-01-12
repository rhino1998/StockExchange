package com.stockexchange.client.ui.components.text;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class HeaderLabel extends Label {
    private static final String style = "-fx-text-fill: #EFEFEF;" + "";

    /**
     * Creates a new HeaderLabel object.
     *
     * @param text DOCUMENT ME!
     */
    public HeaderLabel(String text) {
        super(text);
        this.setStyle(style);
    }
}
