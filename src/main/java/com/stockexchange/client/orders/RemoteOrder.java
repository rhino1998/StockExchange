package com.stockexchange.client.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockexchange.server.orders.ExecutableOrder;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;

public class RemoteOrder extends Order {

    @JsonProperty
    private TransactionType transactionType;
    @JsonProperty
    private OrderType orderType;

    @JsonProperty
    private String symbol;
    @JsonProperty
    private String traderUsername;
    @JsonProperty
    private String brokerageName;
    @JsonProperty
    private String exchangeName;

    @JsonProperty
    private int limitPrice;
    @JsonProperty
    private int volume;

    public RemoteOrder() {
    }

    public RemoteOrder(ExecutableOrder order) {
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
}
