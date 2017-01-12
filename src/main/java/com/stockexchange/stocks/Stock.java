package com.stockexchange.stocks;

import com.stockexchange.server.StockMarket;
import com.stockexchange.server.orders.ExecutableOrder;
import com.stockexchange.server.orders.comparators.BuyComparator;
import com.stockexchange.server.orders.comparators.SellComparator;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.util.History;

import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.PriorityBlockingQueue;

import javafx.application.Platform;

public class Stock {
    private final String name;
    private final String symbol;
    private double dailyHigh;
    private double dailyLow;
    private double ask;
    private double bid;
    private double open;
    private double previousClose;
    private double marketCap;
    private double volume;
    private double qty;
    private double vol;
    private String description;
    private final Timer historian;
    private final History<StockDataPoint> history =
        new History<StockDataPoint>(720);
    private final PriorityBlockingQueue<ExecutableOrder> sellOrders =
        new PriorityBlockingQueue<ExecutableOrder>(100, new SellComparator());
    private final PriorityBlockingQueue<ExecutableOrder> buyOrders =
        new PriorityBlockingQueue<ExecutableOrder>(100, new BuyComparator());

    /**
     * Create a stock from a snapshot of it, typically retrieved elsewhere.
     *
     * @param quote
     *            a quote to initialize stock state
     */
    public Stock(Quote quote) {
        this.name = quote.getName();
        this.symbol = quote.getSymbol();
        this.ask = quote.getAsk();
        this.bid = quote.getBid();
        this.volume = quote.getVolume();
        this.open = quote.getOpen();
        this.previousClose = quote.getPreviousClose();
        this.dailyHigh = quote.getDailyHigh();
        this.dailyLow = quote.getDailyLow();
        this.marketCap = quote.getMarketCap();

        this.qty = marketCap / bid;
        this.vol = volume / bid;

        final Stock stock = this;
        historian = new Timer();
        historian.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double volatility = ((Math.random() * 5) + 2) / 2;

                double rnd = Math.random();

                double changePercent = 2 * volatility * rnd;

                if (changePercent > volatility) {
                    changePercent -= (2 * volatility);
                }

                double changeAmount = (ask * changePercent) / 100;
                double newPrice = ask + changeAmount;

                // Add a ceiling and floor.
                if (newPrice < (dailyLow * 0.8)) {
                    newPrice += (Math.abs(changeAmount) * 2);
                } else if (newPrice > (dailyHigh * 1.2)) {
                    newPrice -= (Math.abs(changeAmount) * 2);
                }

                ask = newPrice;
                bid = ask * (0.99 - (Math.random() / 70));
                marketCap = bid * qty;

                if (bid > dailyHigh) {
                    dailyHigh = bid;
                }

                if (bid < dailyLow) {
                    dailyLow = bid;
                }

                volume += ((bid + ask) / 2 * (int)((Math.random() * vol) / 250));

                history.add(new StockDataPoint(stock));
            }
        }, 0, 1000);
    }

    /**
     * Update the stock from new data
     *
     * @param quote
     *            A snapshot of a stock's state
     */
    public void update(Quote quote) {
        if (!this.symbol.equals(quote.getSymbol())) {
            return;
        }

        this.ask = quote.getAsk();
        this.bid = quote.getBid();
        this.volume = quote.getVolume();
        this.open = quote.getOpen();
        this.previousClose = quote.getPreviousClose();
        this.dailyHigh = quote.getDailyHigh();
        this.dailyLow = quote.getDailyLow();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getSymbol() {
        return symbol;
    }

    private void executeLoop() {
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            if ((bid < ask) && sellOrders.peek().isLimit() &&
                    buyOrders.peek().isLimit()) {
                return;
            }

            executeTrade();
        }
        while (!buyOrders.isEmpty() && buyOrders.peek().isMarket()) {
            ExecutableOrder buy = buyOrders.remove();
            buy.getAccount()
            .updatePortfolio(buy.getStock().getSymbol(), buy.getQuantity());
            buy.getAccount().withdraw(ask * buy.getQuantity());
            System.out.println(ask * buy.getQuantity());
            buy.subtractShares(buy.getQuantity());
        }

        while (!sellOrders.isEmpty() && sellOrders.peek().isMarket()) {
            ExecutableOrder sell = sellOrders.remove();
            sell.getAccount()
            .updatePortfolio(sell.getStock().getSymbol(),
                             -sell.getQuantity());
            sell.getAccount().deposit(bid * sell.getQuantity());
            System.out.println(bid * sell.getQuantity());
            sell.subtractShares(sell.getQuantity());
        }
    }

    private void executeTrade() {
        ExecutableOrder buy;
        ExecutableOrder sell;

        try {
            buy = buyOrders.remove();
            sell = sellOrders.remove();
        } catch (NoSuchElementException e) {
            return;
        }

        if ((buy == null) || (sell == null)) {
            return;
        }

        double price = ask;

        if (sell.isMarket() && buy.isMarket()) {
            price = (ask + bid) / 2;
        } else if (sell.isMarket()) {
            price = bid;
        }

        System.out.println(price);

        long qty = Math.min(sell.getQuantity(), buy.getQuantity());
        sell.subtractShares(qty);
        buy.subtractShares(qty);

        sell.getAccount().updatePortfolio(symbol, -qty);
        sell.getAccount().deposit(price * qty);

        buy.getAccount().updatePortfolio(symbol, qty);
        buy.getAccount().withdraw(price * qty);

        if (buy.getQuantity() > 0) {
            buyOrders.add(buy);
        } else {
            if (!buyOrders.isEmpty()) {
                bid = buyOrders.peek().getPrice();
            }
        }

        if (sell.getQuantity() > 0) {
            sellOrders.add(sell);
        } else {
            if (!sellOrders.isEmpty()) {
                ask = sellOrders.peek().getPrice();
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param order DOCUMENT ME!
     */
    public void placeOrder(ExecutableOrder order) {
        switch (order.getTransactionType()) {
        case BUY:
            buyOrders.add(order);

            if (order.isLimit() && (this.bid < order.getPrice())) {
                this.bid = Math.min(order.getPrice(), ask);
            }

            break;

        case SELL:
            sellOrders.add(order);

            if (order.isLimit() && (this.ask > order.getPrice())) {
                this.ask = Math.max(order.getPrice(), bid);
            }

            break;
        }

        if (this.buyOrders.isEmpty() && this.sellOrders.isEmpty()) {
            return;
        }

        if ((this.getBid() >= this.getAsk()) ||
                (!this.sellOrders.isEmpty() &&
                 this.sellOrders.peek().isMarket()) ||
                (!this.buyOrders.isEmpty() &&
                 this.buyOrders.peek().isMarket())) {
            executeLoop();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getVolume() {
        return volume;
    }

    /**
     * DOCUMENT ME!
     *
     * @param volume DOCUMENT ME!
     */
    public void setVolume(double volume) {
        this.volume = volume;
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
    public double getBid() {
        return bid;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getOpen() {
        return open;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getPreviousClose() {
        return previousClose;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getDailyHigh() {
        return dailyHigh;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getDailyLow() {
        return dailyLow;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public History<StockDataPoint> getHistory() {
        return this.history;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getDescription() {
        return description;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getMarketCap() {
        return marketCap;
    }

    /**
     * DOCUMENT ME!
     *
     * @param description DOCUMENT ME!
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean hasDescription() {
        return (this.description != null) &&
               !this.description.equals("Description not available");
    }
}
