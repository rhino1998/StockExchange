package com.stockexchange.stocks.quotes;

import java.io.IOException;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockexchange.stocks.Stock;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;


@XmlRootElement(name="quote")
public class Quote implements Serializable {

    private static final long serialVersionUID = -5983976623649918132L;

    @JsonProperty
    @XmlElement(name="description")
    private String name;
    
    @JsonProperty
    @XmlElement(name="symbol")
    private String symbol;
    
    @JsonProperty
    @XmlElement(name="exch")
    private String exchange;
    
    @JsonProperty
    @XmlElement(name="high")
    private double dailyHigh;
    
    @JsonProperty
    @XmlElement(name="low")
    private double dailyLow;
    
    @JsonProperty
    @XmlElement(name="bid")
    private double bid;
    
    @JsonProperty
    @XmlElement(name="ask")
    private double ask;
    
    @JsonProperty
    @XmlElement(name="open")
    private double open;
    
    @JsonProperty
    @XmlElement(name="prevclose")
    private double previousClose;

    @JsonProperty
    @XmlElement(name="volume")
    private double volume;

    @JsonCreator
    protected Quote() {
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
        this.exchange = stock.getExchange().getName();
    }

    /**
     * Create a quote from network results
     * 
     * @param args
     *            the traits of the Quote in a string array.
     */
    
    public Quote(
    		String name,
    		String symbol,
    		String exchange
    	){

    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getVolume() {
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


    public String getExchange() {
        return exchange;
    }

    public String toString() {
        return String
                .format("{Symbol: %s, Exchange: %s, Name: %s, Ask: %.2f, Bid %.2f, Daily Low: %.2f, Daily High: %.2f, Open: %.2f, Previous Close: %.2f, Volume: %.2f}",
                        this.symbol, this.exchange, this.name, this.ask,
                        this.bid, this.dailyLow, this.dailyHigh, this.open,
                        this.previousClose, this.volume);
    }

    public boolean equals(Object o) {

        return o instanceof Quote
                && this.symbol.equals(((Quote) o).getSymbol());

    }
}
