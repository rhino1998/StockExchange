package com.stockexchange.client.data;

import java.util.List;

import com.stockexchange.stocks.quotes.Quote;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyDoubleProperty;

;

public class QuoteModel {

    private final Quote quote;
    private final ReadOnlyStringProperty symbol;
    private final ReadOnlyStringProperty name;
    private final ReadOnlyDoubleProperty ask;
    private final ReadOnlyDoubleProperty bid;
    private final ReadOnlyDoubleProperty high;
    private final ReadOnlyDoubleProperty low;
    private final ReadOnlyDoubleProperty open;
    private final ReadOnlyDoubleProperty close;
    private final ReadOnlyDoubleProperty volume;

    public QuoteModel(Quote quote) {
        this.quote = quote;
        this.symbol = new SimpleStringProperty(quote.getSymbol());
        this.name = new SimpleStringProperty(quote.getName());
        this.ask = new SimpleDoubleProperty(quote.getAsk());
        this.bid = new SimpleDoubleProperty(quote.getBid());
        this.high = new SimpleDoubleProperty(quote.getDailyHigh());
        this.low = new SimpleDoubleProperty(quote.getDailyLow());
        this.open = new SimpleDoubleProperty(quote.getOpen());
        this.close = new SimpleDoubleProperty(quote.getPreviousClose());
        this.volume = new SimpleDoubleProperty(quote.getVolume());
    }

    public String getSymbol() {
        return this.symbol.getValueSafe();
    }

    public String getName() {
        return this.name.getValueSafe();
    }

    public Double getAsk() {
        return this.ask.getValue();
    }

    public Double getBid() {
        return this.bid.getValue();
    }

    public Double getLow() {
        return this.low.getValue();
    }

    public Double getHigh() {
        return this.high.getValue();
    }

    public Double getOpen() {
        return this.open.getValue();
    }

    public Double getClose() {
        return this.close.getValue();
    }

    public Double getVolume() {
        return this.volume.getValue();
    }

    public Quote getQuote() {
        return this.quote;
    }

    public static ObservableList<QuoteModel> marshallQuotes(List<Quote> quotes) {
        ObservableList<QuoteModel> quotesList = FXCollections
                .observableArrayList();
        for (Quote quote : quotes) {
            quotesList.add(new QuoteModel(quote));
        }
        return quotesList;

    }
}
