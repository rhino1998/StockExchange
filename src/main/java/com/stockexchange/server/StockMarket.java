package com.stockexchange.server;

import com.stockexchange.server.brokerages.Brokerage;
import com.stockexchange.server.data.YahooFinanceAPI;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.quotes.Quote;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class StockMarket {
    private static final Map<String, Brokerage> brokerages =
        new ConcurrentHashMap<String, Brokerage>();
    private static final Map<String, Stock> stocks =
        new ConcurrentHashMap<String, Stock>();

    /**
     * Adds a new brokerage to the market
     *
     * @param name DOCUMENT ME!
     */
    public static void addBrokerage(String name) {
        if (brokerages.containsKey(name)) {
            // TODO Error of some sort;
        }

        brokerages.put(name, new Brokerage(name));
    }

    /**
     * Retrieves a Brokerage from the map of brokerages
     *
     * @param name Name of the brokerage to get
     *
     * @return Brokerage requested from brokerages
     */
    public static Brokerage getBrokerage(String name) {
        return brokerages.get(name);
    }


    /**
     * Adds a stock from YahooFinanceAPI to the market based on symbol
     *
     * @param symbol the symbol of the stock to list
     *
     * @throws IOException when YahooFinanceAPI.getQuotes fails.
     */
    public static void listStock(String symbol) throws IOException {
        Quote quote = YahooFinanceAPI.getQuote(symbol);
        stocks.put(quote.getSymbol(), new Stock(quote));
    }

    /**
     * Adds stocks from YahooFinanceAPI to the market based on symbols
     *
     * @throws IOException when YahooFinanceAPI.getQuotes fails.
     */
    public static void listStocks(String... symbols) throws IOException {
        for (Quote quote : YahooFinanceAPI.getQuotes(symbols)) {
            if (quote.getAsk() > 0.0) {
                stocks.put(quote.getSymbol(), new Stock(quote));
            }
        }
    }

    public static Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public static Quote getQuote(String symbol) {
        return new Quote(stocks.get(symbol));
    }

    public static List<Quote> getQuotes() {
        ArrayList<Quote> quotes = new ArrayList<Quote>();

        for (Stock stock : stocks.values()) {
            quotes.add(new Quote(stock));
        }

        return quotes;
    }
}
