package com.stockexchange.stocks.quotes;

import java.io.IOException;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockexchange.stocks.Stock;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;

public class Quote implements Serializable {

    private static final long serialVersionUID = -5983976623649918132L;

    @JsonProperty
    private String name;
    @JsonProperty
    private String symbol;
    @JsonProperty
    private String exchange;
    @JsonProperty
    private double dailyHigh;
    @JsonProperty
    private double dailyLow;
    @JsonProperty
    private double bid;
    @JsonProperty
    private double ask;
    @JsonProperty
    private double open;
    @JsonProperty
    private double previousClose;
    @JsonProperty
    private double marketCap;
    @JsonProperty
    private int volume;

    public Quote() {
    };

    /**
     * A snapshot of a stock's state
     * 
     * @param stock
     *            The stock of which to make a snapshot
     */
    public Quote(Stock stock) {
        this.name = stock.getName();
        this.symbol = stock.getSymbol();
        this.dailyHigh = stock.getDailyHigh();
        this.dailyLow = stock.getDailyLow();
        this.bid = stock.getBid();
        this.ask = stock.getAsk();
        this.open = stock.getOpen();
        this.previousClose = stock.getPreviousClose();
        this.volume = stock.getVolume();
        this.marketCap = stock.getMarketCap();
        this.exchange = stock.getExchange().getName();
    }

    /**
     * 
     * @param args
     *            the traits of the Quote in a string array.
     */
    public Quote(String[] args) {

        this.symbol = args[0];
        this.name = args[1];
        this.ask = Double.parseDouble(args[2]);
        this.bid = Double.parseDouble(args[3]);

        this.dailyLow = Double.parseDouble(args[4]);
        this.dailyHigh = Double.parseDouble(args[5]);
        this.volume = Integer.parseInt(args[6]);
        this.open = Double.parseDouble(args[7]);
        this.previousClose = Double.parseDouble(args[8]);
        this.exchange = args[10];

        double mCap = 0;
        switch (args[9].charAt(args[9].length() - 1)) {
        case 'B':
            mCap = Double.parseDouble(args[9].substring(0, args[9].length() - 1));
            mCap *= 1000000000;
            break;
        case 'M':
            mCap = Double.parseDouble(args[9].substring(0, args[9].length() - 1));
            mCap *= 1000000;
            break;
        default:
            mCap = Double.parseDouble(args[9]);
        }

        this.marketCap = mCap;

    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getVolume() {
        return volume;
    }

    public double getAsk() {
        return ask;
    }

    public double getBid() {
        return bid;
    }

    public double getDailyLow() {
        return dailyLow;
    }

    public double getDailyHigh() {
        return dailyHigh;
    }

    public double getOpen() {
        return open;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public String getExchange() {
        return exchange;
    }

    public String toString() {
        return String
                .format("Symbol: %s, Exchange: %s, Name: %s, Ask: %.2f, Bid %.2f, Daily Low: %.2f, Daily High: %.2f, Open: %.2f, Previous Close: %.2f, Volume: %d, Market Cap: %.2f",
                        this.symbol, this.exchange, this.name, this.ask, this.bid, this.dailyLow, this.dailyHigh,
                        this.open, this.previousClose, this.volume, this.marketCap);
    }

    public boolean equals(Object o) {

        return o instanceof Quote && this.symbol.equals(((Quote) o).getSymbol());

    }
}
