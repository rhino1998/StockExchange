package com.stockexchange.stocks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class StockDataPoint {
    @JsonProperty
    private long time;
    @JsonProperty
    private double ask;
    @JsonProperty
    private double bid;

    @JsonCreator
    private StockDataPoint() {
    }

    protected StockDataPoint(Stock stock) {
        this.time = System.nanoTime();
        this.ask = stock.getAsk();
        this.bid = stock.getBid();
    }

    /**
     * Creates a new StockDataPoint object.
     *
     * @param ask DOCUMENT ME!
     * @param bid DOCUMENT ME!
     */
    public StockDataPoint(double ask, double bid) {
        this.time = System.nanoTime();
        this.bid = bid;
        this.ask = ask;
    }

    /**
     * DOCUMENT ME!
     *
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean equals(Object o) {
        if (o instanceof StockDataPoint) {
            StockDataPoint e = (StockDataPoint)o;

            return (this.time == e.time) && (this.bid == e.bid) &&
                   (this.ask == e.ask);
        }

        return false;
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
    public double getAsk() {
        return ask;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public long getTime() {
        return time;
    }
}
