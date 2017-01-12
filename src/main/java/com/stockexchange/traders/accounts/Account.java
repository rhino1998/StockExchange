package com.stockexchange.traders.accounts;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.stockexchange.server.StockMarket;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.util.DefaultConcurrentHashMap;
import com.stockexchange.util.DefaultHashMap;
import com.stockexchange.util.History;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Account {
    @JsonProperty
    private UUID uuid;
    @JsonProperty
    private String name;
    @JsonProperty
    @JsonBackReference
    private Trader owner;
    @JsonIgnore
    private List<Trader> owners = new ArrayList<Trader>();
    @JsonProperty
    private double balance = 0;
    @JsonIgnore
    private Map<String, Long> portfolio =
        new DefaultConcurrentHashMap<String, Long>(0L);
    @JsonIgnore
    private Map<String, Long> reservedPortfolio =
        new DefaultConcurrentHashMap<String, Long>(0L);

    @JsonIgnore
    private History<String> hist = new History<String>(20);

    @JsonCreator
    private Account() {
    }

    /**
     * Creates a new Account object.
     *
     * @param name DOCUMENT ME!
     */
    public Account(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    /**
     * Creates a new Account object.
     *
     * @param name DOCUMENT ME!
     */
    public Account(String name, Trader... owners) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.owners.addAll(Arrays.asList(owners));
    }

    public Account(String name, double money, Trader... owners) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.owners.addAll(Arrays.asList(owners));
        this.balance = money;
    }

    public void addOwner(Trader trader) {
        this.owners.add(trader);
    }

    public double deposit(double money) {
        balance += money;

        return balance;
    }

    public double withdraw(double money) {
        balance -= money;

        return balance;
    }

    public boolean reservePortfolio(String symbol, long qty) {
        if (qty > (portfolio.get(symbol) - reservedPortfolio.get(symbol))) {
            return false;
        }

        reservedPortfolio.put(symbol, qty);

        return true;
    }

    public long updatePortfolio(String symbol, long qty) {

        hist.add(String.format("%s : %s", symbol, qty));

        if (qty < 0) {
            reservedPortfolio.put(symbol,
                                  Math.max(0, reservedPortfolio.get(symbol) + qty));
        }

        portfolio.put(symbol, Math.max(0, qty + portfolio.get(symbol)));

        if (portfolio.get(symbol) == 0) {
            portfolio.remove(symbol);
        }

        return portfolio.get(symbol);
    }

    public UUID getUUID() {
        return uuid;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        if (o instanceof Account) {
            return ((Account) o).uuid.equals(this.uuid);
        }
        return false;
    }

    @JsonIgnore
    public List<Quote> getPortfolio() {
        List<Quote> quotes = new ArrayList<Quote>();

        for (String symbol : portfolio.keySet()) {
            quotes.add(new Quote(StockMarket.getStock(symbol),
                                 portfolio.get(symbol)));
        }

        return quotes;
    }

    @JsonIgnore
    public List<String> getMessages() {
        List<String> messages = hist.getAll();

        return messages;
    }

    public String toString() {
        return String.format("%s", name);
    }
}
