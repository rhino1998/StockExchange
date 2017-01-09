package com.stockexchange.stocks.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;

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

    @JsonIgnore
    public double getPrice() {
        return limitPrice;
    }

    @JsonIgnore
    public long getQuantity() {
        return qty;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @JsonIgnore
    public boolean isMarket() {
        return this.orderType == OrderType.MARKET;
    }

    @JsonIgnore
    public boolean isLimit() {
        return this.orderType == OrderType.LIMIT;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
