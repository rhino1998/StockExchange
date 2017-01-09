package com.stockexchange.client.orders;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockexchange.server.orders.ExecutableOrder;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;

public class RemoteOrder extends Order {

    @JsonProperty
    private String symbol;
    @JsonProperty
    private String traderUsername;
    @JsonProperty
    private UUID accountUUID;
    @JsonProperty
    private String brokerageName;
    @JsonProperty
    private String exchangeName;

    @JsonCreator
    private RemoteOrder() {
    }

    public RemoteOrder(ExecutableOrder order) {
        this.accountUUID = order.getAccount().getUUID();
    }

    public RemoteOrder(Quote quote, Account acct, Trader trader,
            TransactionType transType, long qty) {
        super(transType, OrderType.MARKET, qty, 0);
        this.exchangeName = quote.getExchange();
        this.symbol = quote.getSymbol();
        this.traderUsername = trader.getUsername();
        this.brokerageName = trader.getBrokerageName();
        this.accountUUID = acct.getUUID();
    }

    public RemoteOrder(Quote quote, Account acct, Trader trader, long qty,
            TransactionType transType, double price) {
        super(transType, OrderType.MARKET, qty, price);
        this.exchangeName = quote.getExchange();
        this.symbol = quote.getSymbol();
        this.traderUsername = trader.getUsername();
        this.brokerageName = trader.getBrokerageName();
        this.accountUUID = acct.getUUID();
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

    public UUID getAccountUUID() {
        return accountUUID;
    }
}
