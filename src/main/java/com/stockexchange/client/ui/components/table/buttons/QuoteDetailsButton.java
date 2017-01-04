package com.stockexchange.client.ui.components.table.buttons;

import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.StockDetailsView;
import com.stockexchange.client.ui.ViewStage;
import com.stockexchange.stocks.quotes.Quote;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class QuoteDetailsButton extends ColumnButton<QuoteModel> {

    private Quote quote;

    public QuoteDetailsButton(ViewStage win, final QuoteModel item) {
        super(win, item);
        this.quote = item.getQuote();
        this.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                window.setView(new StockDetailsView(window,
                        quote.getExchange(), quote.getSymbol()));
            }
        });
        this.setText("Details");
    }
}
