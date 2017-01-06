package com.stockexchange.traders;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import org.glassfish.jersey.message.internal.Token;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.stockexchange.server.brokerages.Brokerage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import com.stockexchange.server.StockMarket;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.Register;

public class Trader implements Comparable<Trader>, Serializable {

    private transient final static long timeout = (long) 3e11;

    @JsonProperty
    private String username;
    @JsonProperty
    private String name;
    @JsonProperty
    private HashMap<String, Account> accounts;
    @JsonProperty
    private HashMap<UUID, Order> pendingOrders;
    @JsonProperty
    private HashMap<String, Long> portfolio;
    @JsonProperty
    private UUID token;
    @JsonProperty
    private String brokerageName;

    private transient String pwHash;
    private transient Brokerage brokerage;
    private transient long tokenTimeout;

    // private transient Token token;

    /**
     * Trader emtpy constructor purely for json transport
     */
    @JsonCreator
    private Trader() {
    }

    /**
     * Create a new trader in a brokerage from a registration token
     * 
     * @param brokerage
     *            The name of an existing Brokerage
     * @param reg
     *            A registration token
     */
    public Trader(String brokerage, Register reg) {
        this.brokerageName = brokerage;
        this.brokerage = StockMarket.getBrokerage(brokerage);
        this.username = reg.getUsername();
        this.name = reg.getName();
        this.accounts = new HashMap<String, Account>();
        this.pendingOrders = new HashMap<UUID, Order>();

        this.pwHash = BCrypt.hashpw(reg.getPassword(), BCrypt.gensalt());
    }

    /**
     * Create a new session token for a trader Returns an existing one if the session is not invalid yet
     */
    public void genToken() {
        if (this.token == null || System.nanoTime() > this.tokenTimeout + 3e11) {
            this.token = new UUID(System.nanoTime(), System.nanoTime());
        }
        this.tokenTimeout = System.nanoTime();
    }

    /**
     * Checks that a session is not invalid
     * 
     */
    public boolean verifyToken(UUID token) {// Verify within 5 minutes
        if (System.nanoTime() < this.tokenTimeout + timeout
                && this.token.compareTo(token) == 0) {
            this.tokenTimeout = System.nanoTime();
            return true;
        }
        this.token = null;
        return false;
    }

    /**
     * Invalidates a session
     */
    public void logout() {
        this.token = null;
        this.tokenTimeout = 0;
    }

    /**
     * Get a session token (preexisting)
     * 
     */
    public UUID getToken() {
        return this.token;
    }

    /**
     * Refreshes a session token
     */
    public boolean renewToken() {
        if (System.nanoTime() < this.tokenTimeout + timeout) {
            this.tokenTimeout = System.nanoTime();
            return true;
        }
        return false;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public void submitOrder(Order order) {
        return;
    }

    public boolean equals(Object other) {
        return other instanceof Trader
                && this.verifyToken(((Trader) other).token);
    }

    public void getQuote(String symbol) {
        // TODO
    }

    public int compareTo(Trader o) {
        return this.compareTo(o);
    }

    public boolean authenticate(String pw) {

        return BCrypt.checkpw(pw, this.pwHash);
    }

    public String getBrokerageName() {
        return brokerageName;
    }

    public void addAccount(Account acct) {
        acct.addOwner(this);
        this.accounts.put(acct.getName(), acct);
    }

    public Account getAccount(String name) {
        return accounts.get(name);
    }
}
