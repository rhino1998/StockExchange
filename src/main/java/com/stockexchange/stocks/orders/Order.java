package com.stockexchange.stocks.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Order {
    @JsonProperty
    protected TransactionType transactionType;
    @JsonProperty
    protected OrderType orderType;
    @JsonProperty
    protected long qty;
    @JsonProperty("limitPrice")
    protected double limitPrice;

    @JsonCreator
    protected Order() {
    }

    protected Order(TransactionType tt, OrderType ot, long qty, double lp) {
        transactionType = tt;
        orderType = ot;
        this.qty = qty;
        limitPrice = lp;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @JsonIgnore
    public double getPrice() {
        return limitPrice;
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
    public OrderType getOrderType() {
        return orderType;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @JsonIgnore
    public boolean isMarket() {
        return this.orderType == OrderType.MARKET;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @JsonIgnore
    public boolean isLimit() {
        return this.orderType == OrderType.LIMIT;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }
}
