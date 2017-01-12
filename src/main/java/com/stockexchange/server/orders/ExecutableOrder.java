package com.stockexchange.server.orders;

import com.stockexchange.client.orders.RemoteOrder;
import com.stockexchange.server.StockMarket;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ExecutableOrder extends Order {
    private final Trader trader;
    private final Account account;
    private final Stock stock;

    /**
     * Creates a new ExecutableOrder object.
     *
     * @param order DOCUMENT ME!
     * @param trader DOCUMENT ME!
     */
    public ExecutableOrder(RemoteOrder order, Trader trader) {
        super(order.getTransactionType(), order.getOrderType(),
              order.getQuantity(), order.getPrice());

        this.trader = trader;
        this.account = StockMarket.getBrokerage(order.getBrokerageName())
                       .getAccount(order.getAccountUUID());
        this.stock = StockMarket.getStock(order.getSymbol());
    }

    /**
     * DOCUMENT ME!
     */
    public void submit() {
        stock.placeOrder(this);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getPrice() {
        switch (this.getTransactionType()) {
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

    /**
     * DOCUMENT ME!
     *
     * @param qty DOCUMENT ME!
     */
    public void subtractShares(long qty) {
        this.qty -= qty;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Stock getStock() {
        return stock;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Trader getTrader() {
        return trader;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Account getAccount() {
        return account;
    }
}
