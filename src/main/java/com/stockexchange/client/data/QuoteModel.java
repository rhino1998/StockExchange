package com.stockexchange.client.data;

import com.stockexchange.stocks.quotes.Quote;

import java.util.List;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class QuoteModel {
    private final Quote quote;
    private final ReadOnlyStringProperty symbol;
    private final ReadOnlyStringProperty name;
    private final ReadOnlyLongProperty qty;
    private final ReadOnlyDoubleProperty ask;
    private final ReadOnlyDoubleProperty bid;
    private final ReadOnlyDoubleProperty high;
    private final ReadOnlyDoubleProperty low;
    private final ReadOnlyDoubleProperty open;
    private final ReadOnlyDoubleProperty close;
    private final ReadOnlyDoubleProperty volume;
    private final ReadOnlyDoubleProperty marketCap;

    /**
     * Creates a new QuoteModel object.
     *
     * @param quote DOCUMENT ME!
     */
    public QuoteModel(Quote quote) {
        this.quote = quote;
        this.symbol = new SimpleStringProperty(quote.getSymbol());
        this.name = new SimpleStringProperty(quote.getName());
        this.qty = new SimpleLongProperty(quote.getQuantity());
        this.ask = new SimpleDoubleProperty(quote.getAsk());
        this.bid = new SimpleDoubleProperty(quote.getBid());
        this.high = new SimpleDoubleProperty(quote.getDailyHigh());
        this.low = new SimpleDoubleProperty(quote.getDailyLow());
        this.open = new SimpleDoubleProperty(quote.getOpen());
        this.close = new SimpleDoubleProperty(quote.getPreviousClose());
        this.volume = new SimpleDoubleProperty(quote.getVolume());
        this.marketCap = new SimpleDoubleProperty(quote.getMarketCap());
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getSymbol() {
        return this.symbol.getValueSafe();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return this.name.getValueSafe();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Double getAsk() {
        return this.ask.getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Double getBid() {
        return this.bid.getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Double getLow() {
        return this.low.getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Double getHigh() {
        return this.high.getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Double getOpen() {
        return this.open.getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Double getClose() {
        return this.close.getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Long getQty() {
        return this.qty.getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Double getVolume() {
        return this.volume.getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Double getMarketCap() {
        return this.marketCap.getValue();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Quote getQuote() {
        return this.quote;
    }

    /**
     * DOCUMENT ME!
     *
     * @param quotes DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static ObservableList<QuoteModel> marshallQuotes(
        List<Quote> quotes) {
        ObservableList<QuoteModel> quotesList =
            FXCollections.observableArrayList();

        for (Quote quote : quotes) {
            quotesList.add(new QuoteModel(quote));
        }

        return quotesList;
    }
}
