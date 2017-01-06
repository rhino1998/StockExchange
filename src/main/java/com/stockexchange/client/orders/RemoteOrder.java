package com.stockexchange.client.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockexchange.server.orders.ExecutableOrder;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;

public class RemoteOrder extends Order {

    @JsonProperty
    private String symbol;
    @JsonProperty
    private String traderUsername;
    @JsonProperty
    private String accountName;
    @JsonProperty
    private String brokerageName;
    @JsonProperty
    private String exchangeName;

    @JsonCreator
    private RemoteOrder() {
    }

    public RemoteOrder(ExecutableOrder order) {
        this.accountName = order.getAccount().getName();
    }

    public RemoteOrder(Quote quote, String accountName, Trader trader,
            TransactionType transType, long qty) {
        super(transType, OrderType.MARKET, qty, 0);
        this.exchangeName = quote.getExchange();
        this.symbol = quote.getSymbol();
        this.traderUsername = trader.getUsername();
        this.brokerageName = trader.getBrokerageName();
        this.accountName = accountName;
    }

    public RemoteOrder(Quote quote, String accountName, Trader trader,
            long qty, TransactionType transType, double price) {
        super(transType, OrderType.MARKET, qty, price);
        this.exchangeName = quote.getExchange();
        this.symbol = quote.getSymbol();
        this.traderUsername = trader.getUsername();
        this.brokerageName = trader.getBrokerageName();
        this.accountName = accountName;
        this.transactionType = transType;
        this.orderType = OrderType.LIMIT;
        this.limitPrice = price;
        this.qty = qty;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getBrokerageName() {
        return brokerageName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getTraderUsername() {
        return traderUsername;
    }

    public String getAccountName() {
        return accountName;
    }
}
