package com.stockexchange.stocks.quotes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.stockexchange.stocks.Stock;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;

import java.io.IOException;
import java.io.Serializable;

import java.time.OffsetDateTime;

import java.util.Date;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Quote implements Serializable {
    private static final long serialVersionUID = -5983976623649918132L;
    @JsonProperty
    private String name;
    @JsonProperty
    private String symbol;
    @JsonProperty
    private double dailyHigh;
    @JsonProperty
    private double dailyLow;
    @JsonProperty
    private long qty;
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
    private double volume;

    @JsonCreator
    private Quote() {
    }

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
    }

    /**
     * Creates a new Quote object with a quantity owned
     *
     * @param stock Stock to base quote off of
     * @param qty quantity for portfolios
     */
    public Quote(Stock stock, long qty) {
        this.name = stock.getName();
        this.symbol = stock.getSymbol();
        this.dailyHigh = stock.getDailyHigh();
        this.dailyLow = stock.getDailyLow();
        this.bid = stock.getBid();
        this.qty = qty;
        this.ask = stock.getAsk();
        this.open = stock.getOpen();
        this.previousClose = stock.getPreviousClose();
        this.volume = stock.getVolume();
        this.marketCap = stock.getMarketCap();
    }

    /**
     * Create a quote from network results
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

        double mCap = 0;

        switch (args[9].charAt(args[9].length() - 1)) {
        case 'B':
            mCap = Double.parseDouble(args[9].substring(0,
                                      args[9].length() - 1));
            mCap *= 1e9;

            break;

        case 'M':
            mCap = Double.parseDouble(args[9].substring(0,
                                      args[9].length() - 1));
            mCap *= 1e6;

            break;

        default:
            mCap = Double.parseDouble(args[9]);
        }

        this.marketCap = mCap;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getVolume() {
        return volume;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getAsk() {
        return ask;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getBid() {
        return bid;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @JsonIgnore
    public long getQuantity() {
        return qty;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getDailyLow() {
        return dailyLow;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getDailyHigh() {
        return dailyHigh;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getOpen() {
        return open;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getPreviousClose() {
        return previousClose;
    }

    /**
     * gets marketcap
     *
     * @return marketCap
     */
    public double getMarketCap() {
        return marketCap;
    }

    /**
     * basic toString
     *
     * @return String
     */
    public String toString() {
        return String.format("Symbol: %s, Name: %s, Ask: %.2f, Bid %.2f, Daily Low: %.2f, Daily High: %.2f, Open: %.2f, Previous Close: %.2f, Volume: %.2f, Market Cap: %.2f",
                             this.symbol, this.name, this.ask, this.bid, this.dailyLow,
                             this.dailyHigh, this.open, this.previousClose, this.volume,
                             this.marketCap);
    }

    /**
     * DOCUMENT ME!
     *
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean equals(Object o) {
        return o instanceof Quote &&
               this.symbol.equals(((Quote)o).getSymbol());
    }
}
