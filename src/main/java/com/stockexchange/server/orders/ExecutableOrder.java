package com.stockexchange.server.orders;

import com.stockexchange.client.orders.RemoteOrder;
import com.stockexchange.server.StockMarket;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;

public class ExecutableOrder extends Order {

    private final Trader trader;
    private final Account account;
    private final Stock stock;

    public ExecutableOrder(RemoteOrder order) {
        this.transactionType = order.getTransactionType();
        this.orderType = order.getOrderType();

        this.trader = StockMarket.getBrokerage(order.getBrokerageName())
                .getTrader(order.getTraderUsername());
        this.account = trader.getAccount(order.getAccountName());
        this.stock = StockMarket.getStockExchange(order.getExchangeName())
                .getStock(order.getSymbol());
        this.qty = order.getQuantity();
        this.limitPrice = order.getPrice();
    }

    public void submit() {
        stock.placeOrder(this);
    }

    public double getPrice() {
        switch(this.getTransactionType()) {
        case BUY:
            if (this.getOrderType() == OrderType.MARKET) {
                return this.stock.getBid();
            }
            break;
        case SELL:
            if (this.getOrderType() == OrderType.MARKET) {
                return this.stock.getAsk();
            }
            break;
        }
        return super.getPrice();
    }

    public void subtractShares(long qty) {
        this.qty -= qty;
    }

    public Stock getStock() {
        return stock;
    }

    public Trader getTrader() {
        return trader;
    }

    public Account getAccount() {
        return account;
    }

}
