package com.stockexchange.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.stockexchange.server.data.YahooFinanceAPI;


import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.quotes.Quote;

import javassist.compiler.ast.Symbol;

public class MarketSystem {

    private final String name;
    private final HashMap<String, Stock> stocks = new HashMap<String, Stock>();
    private final List<String> symbols = new ArrayList<String>();

    public MarketSystem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void listStock(String symbol) throws IOException {
        Stock stock = new Stock(YahooFinanceAPI.getQuote(symbol));
        stocks.put(stock.getSymbol(), stock);
        this.symbols.add(symbol);
        //System.out.println(symbol);
    }

    public void listStocks(String... symbols) throws IOException {
        this.symbols.addAll(Arrays.asList(symbols));
        for (Quote quote : YahooFinanceAPI.getQuotes(symbols)) {
            Stock stock = new Stock(quote);
            stocks.put(stock.getSymbol(), stock);
        }
    }

    public Stock getRandomStock() {
        if (symbols.isEmpty()) {
            return null;
        }
        return stocks.get(symbols.get((int) (Math.random() * symbols.size())));
    }

    public void listStock(Stock stock) {
        stocks.put(stock.getSymbol(), stock);
        this.symbols.add(stock.getSymbol());
    }

    public Quote getQuote(String symbol) {
        return new Quote(stocks.get(symbol));
    }

    public List<Quote> getQuotes() {
        ArrayList<Quote> quotes = new ArrayList<Quote>(stocks.size());
        for (Stock stock : this.stocks.values()) {
            quotes.add(new Quote(stock));
        }
        return quotes;
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }
}
