package com.stockexchange.stocks;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;

import com.stockexchange.server.MarketSystem;
import com.stockexchange.server.StockMarket;
import com.stockexchange.server.orders.ExecutableOrder;
import com.stockexchange.server.orders.comparators.BuyComparator;
import com.stockexchange.server.orders.comparators.SellComparator;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.util.History;

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

    private String description;
    private final Timer historian;
    private final History<StockDataPoint> history = new History<StockDataPoint>(
            720);
    private final PriorityBlockingQueue<ExecutableOrder> sellOrders = new PriorityBlockingQueue<ExecutableOrder>(
            100, new SellComparator());
    private final PriorityBlockingQueue<ExecutableOrder> buyOrders = new PriorityBlockingQueue<ExecutableOrder>(
            100, new BuyComparator());
    private final MarketSystem exchange;

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
        this.exchange = StockMarket.getStockExchange(quote.getExchange());

        final Stock stock = this;
        historian = new Timer();
        historian.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                double volatility = (Math.random() * 5 + 2) / 2;

                double rnd = Math.random();

                double changePercent = 2 * volatility * rnd;

                if (changePercent > volatility) {
                    changePercent -= (2 * volatility);
                }
                double changeAmount = ask * changePercent / 100;
                double newPrice = ask + changeAmount;

                // Add a ceiling and floor.
                if (newPrice < dailyLow * 0.8) {
                    newPrice += Math.abs(changeAmount) * 2;
                } else if (newPrice > dailyHigh * 1.2) {
                    newPrice -= Math.abs(changeAmount) * 2;
                }

                ask = newPrice;
                bid = ask * (0.99 - Math.random() / 70);
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

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    private void executeLoop() {
        while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            if (bid < ask && sellOrders.peek().isLimit()
                    && buyOrders.peek().isLimit()) {
                return;
            }
            executeTrade();
        }

        if (!buyOrders.isEmpty() && buyOrders.peek().isMarket()) {
            ExecutableOrder buy = buyOrders.remove();
            buy.getAccount().updatePortfolio(buy.getStock().getSymbol(),
                    buy.getQuantity());
            buy.getAccount().withdraw(ask * buy.getQuantity());
            System.out.println(ask * buy.getQuantity());
            buy.subtractShares(buy.getQuantity());
        }

        if (!sellOrders.isEmpty() && sellOrders.peek().isMarket()) {
            ExecutableOrder sell = sellOrders.remove();
            sell.getAccount().updatePortfolio(sell.getStock().getSymbol(),
                    sell.getQuantity());
            sell.getAccount().deposit(bid * sell.getQuantity());
            System.out.println(bid * sell.getQuantity());
            sell.subtractShares(sell.getQuantity());
        }
    }

    private void executeTrade() {
        ExecutableOrder buy, sell;
        try {
            buy = buyOrders.remove();
            sell = sellOrders.remove();
        } catch (NoSuchElementException e) {
            return;
        }

        if (buy == null || sell == null) {
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

    public void placeOrder(ExecutableOrder order) {

        switch(order.getTransactionType()) {
        case BUY:
            buyOrders.add(order);
            if (order.isLimit() && this.bid < order.getPrice()) {
                this.bid = Math.min(order.getPrice(), ask);
            }

            break;
        case SELL:
            sellOrders.add(order);
            if (order.isLimit() && this.ask > order.getPrice()) {
                this.ask = Math.max(order.getPrice(), bid);
            }
            break;
        }

        if (this.buyOrders.isEmpty() && this.sellOrders.isEmpty()) {
            return;
        }

        if (this.getBid() >= this.getAsk()
                || (!this.sellOrders.isEmpty() && this.sellOrders.peek()
                        .isMarket())
                || (!this.buyOrders.isEmpty() && this.buyOrders.peek()
                        .isMarket())) {
            executeLoop();
        }

    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getAsk() {
        return ask;
    }

    public double getBid() {
        return bid;
    }

    public double getOpen() {
        return open;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public double getDailyHigh() {
        return dailyHigh;
    }

    public double getDailyLow() {
        return dailyLow;
    }

    public History<StockDataPoint> getHistory() {
        return this.history;
    }

    public MarketSystem getExchange() {
        return exchange;
    }

    public String getDescription() {
        return description;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean hasDescription() {
        return this.description != null
                && !this.description.equals("Description not available");
    }
}
