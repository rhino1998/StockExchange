package com.stockexchange.client.ui.components.table.buttons;

import com.stockexchange.client.ClientApp;
import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.stocks.quotes.Quote;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Button;

import javafx.stage.Stage;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class QuoteDetailsButton extends ColumnButton<QuoteModel> {
    private Quote quote;

    /**
     * Creates a new QuoteDetailsButton object.
     *
     * @param app DOCUMENT ME!
     * @param item DOCUMENT ME!
     */
    public QuoteDetailsButton(final ClientApp app, final QuoteModel item) {
        super(app, item);
        this.quote = item.getQuote();
        this.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                app.gotoStockDetails(quote);
            }
        });
        this.setText("Details");
    }
}
