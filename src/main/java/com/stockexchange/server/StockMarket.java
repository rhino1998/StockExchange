package com.stockexchange.server;

import java.io.IOException;
import java.util.HashMap;

import com.stockexchange.server.brokerages.Brokerage;
import com.stockexchange.server.data.YahooFinanceAPI;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.quotes.Quote;

public class StockMarket {

    private static final HashMap< String, MarketSystem> exchanges = new HashMap< String, MarketSystem>();
    private static final HashMap< String, Brokerage> brokerages = new HashMap< String, Brokerage>();

    public static void addBrokerage(String name) {
        if (brokerages.containsKey(name)) {
            // TODO Error of some sort;
        }
        brokerages.put(name, new Brokerage(name));
    }

    public static Brokerage getBrokerage(String name) {
        return brokerages.get(name);
    }

    public static MarketSystem registerStockExchange(String name) {
        if (exchanges.containsKey(name)) {
            return exchanges.get(name);
        }
        MarketSystem exchange = new MarketSystem(name);
        exchanges.put(name, exchange);
        return exchange;
    }

    public static MarketSystem getStockExchange(String name) {
        return exchanges.get(name);
    }

    public static void listStock(String symbol) throws IOException {
        Quote quote = YahooFinanceAPI.getQuote(symbol);
        registerStockExchange(quote.getExchange()).listStock(new Stock(quote));
    }

    public static void listStocks(String... symbols) throws IOException {
        for (Quote quote : YahooFinanceAPI.getQuotes(symbols)) {
            registerStockExchange(quote.getExchange()).listStock(new Stock(quote));
        }
    }
}
