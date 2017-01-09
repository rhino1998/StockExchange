package com.stockexchange.traders.accounts;

import java.util.HashMap;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.stockexchange.traders.Trader;
import com.stockexchange.util.DefaultHashMap;

public class Account {

    @JsonProperty
    private UUID uuid;
    @JsonProperty
    private String name;
    @JsonProperty
    @JsonBackReference
    private List<Trader> owners = new ArrayList<Trader>();
    @JsonProperty
    private double balance = 0;
    @JsonProperty
    private HashMap<String, Long> portfolio = new DefaultHashMap<String, Long>(
            0l);
    @JsonProperty
    private HashMap<String, Long> reservedPortfolio = new DefaultHashMap<String, Long>(
            0l);

    @JsonCreator
    private Account() {
    }

    public Account(String name) {
        this.uuid = new UUID(System.nanoTime(), System.nanoTime());
        this.name = name;
    }

    public Account(String name, Trader... owners) {
        this.uuid = new UUID(System.nanoTime(), System.nanoTime());
        this.name = name;
        this.owners.addAll(Arrays.asList(owners));
    }

    public Account(String name, double money, Trader... owners) {
        this.uuid = new UUID(System.nanoTime(), System.nanoTime());
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
        if (qty > portfolio.get(symbol) - reservedPortfolio.get(symbol)) {
            return false;
        }
        reservedPortfolio.put(symbol, qty);
        return true;
    }

    public double updatePortfolio(String symbol, long qty) {
        if (qty < 0) {
            reservedPortfolio.put(symbol,
                    Math.max(0, reservedPortfolio.get(symbol) + qty));
        }
        portfolio.put(symbol, Math.max(0, qty + portfolio.get(symbol)));
        return portfolio.get(symbol);
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
