package com.stockexchange.stocks;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockDataPoint {

    @JsonProperty
    private long time;
    @JsonProperty
    private double ask;
    @JsonProperty
    private double bid;

    public StockDataPoint() {
    }

    protected StockDataPoint(Stock stock) {
        this.time = System.nanoTime();
        this.ask = stock.getAsk();
        this.bid = stock.getBid();
    }

    public StockDataPoint(double ask, double bid) {
        this.time = System.nanoTime();
        this.bid = bid;
        this.ask = ask;
    }

    public boolean equals(Object o) {
        if (o instanceof StockDataPoint) {
            StockDataPoint e = (StockDataPoint) o;
            return this.time == e.time && this.bid == e.bid && this.ask == e.ask;
        }
        return false;
    }

    public double getBid() {
        return bid;
    }

    public double getAsk() {
        return ask;
    }

    public long getTime() {
        return time;
    }

}
