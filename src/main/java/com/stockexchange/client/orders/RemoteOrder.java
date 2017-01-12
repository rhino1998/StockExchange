package com.stockexchange.client.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.stockexchange.server.orders.ExecutableOrder;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;

import java.util.UUID;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class RemoteOrder extends Order {
    @JsonProperty
    private String symbol;
    @JsonProperty
    private String traderUsername;
    @JsonProperty
    private UUID accountUUID;
    @JsonProperty
    private String brokerageName;

    @JsonCreator
    private RemoteOrder() {
    }

    /**
     * Creates a new RemoteOrder object.
     *
     * @param order DOCUMENT ME!
     */
    public RemoteOrder(ExecutableOrder order) {
        this.accountUUID = order.getAccount().getUUID();
    }

    /**
     * Creates a new RemoteOrder object.
     *
     * @param quote DOCUMENT ME!
     * @param acct DOCUMENT ME!
     * @param trader DOCUMENT ME!
     * @param transType DOCUMENT ME!
     * @param qty DOCUMENT ME!
     */
    public RemoteOrder(Quote quote, Account acct, Trader trader,
                       TransactionType transType, long qty) {
        super(transType, OrderType.MARKET, qty, 0);
        this.symbol = quote.getSymbol();
        this.traderUsername = trader.getUsername();
        this.brokerageName = trader.getBrokerageName();
        this.accountUUID = acct.getUUID();
    }

    /**
     * Creates a new RemoteOrder object.
     *
     * @param quote DOCUMENT ME!
     * @param acct DOCUMENT ME!
     * @param trader DOCUMENT ME!
     * @param qty DOCUMENT ME!
     * @param transType DOCUMENT ME!
     * @param price DOCUMENT ME!
     */
    public RemoteOrder(Quote quote, Account acct, Trader trader, long qty,
                       TransactionType transType, double price) {
        super(transType, OrderType.MARKET, qty, price);
        this.symbol = quote.getSymbol();
        this.traderUsername = trader.getUsername();
        this.brokerageName = trader.getBrokerageName();
        this.accountUUID = acct.getUUID();
        this.transactionType = transType;
        this.orderType = OrderType.LIMIT;
        this.limitPrice = price;
        this.qty = qty;
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
    public String getBrokerageName() {
        return brokerageName;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getTraderUsername() {
        return traderUsername;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public UUID getAccountUUID() {
        return accountUUID;
    }
}
