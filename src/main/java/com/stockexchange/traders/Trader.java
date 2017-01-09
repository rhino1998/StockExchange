package com.stockexchange.traders;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.UUID;

import org.glassfish.jersey.message.internal.Token;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.stockexchange.server.brokerages.Brokerage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.stockexchange.server.StockMarket;
import com.stockexchange.server.ServerState;
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
    @JsonManagedReference
    private List<Account> accounts;

    @JsonProperty
    private Map<UUID, Order> pendingOrders;

    @JsonProperty
    private Map<String, Long> portfolio;

    @JsonProperty
    private UUID token;

    @JsonProperty
    private String brokerageName;

    @JsonIgnore
    private transient String pwHash;
    @JsonIgnore
    private transient Brokerage brokerage;
    @JsonIgnore
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
        this.accounts = new ArrayList<Account>();
        this.pendingOrders = new ConcurrentHashMap<UUID, Order>();
        this.portfolio = new ConcurrentHashMap<String, Long>();
        this.pwHash = BCrypt.hashpw(reg.getPassword(), BCrypt.gensalt());

        Account acct = new Account("Personal");
        acct.deposit(10000);
        this.brokerage.addAccount(acct);
        this.addAccount(acct);
    }

    /**
     * Create a new session token for a trader Returns an existing one if the session is not invalid yet
     */
    public void genToken() {
        if (token != null && ServerState.getTraderByToken(token) != null) {
            return;
        }
        token = UUID.randomUUID();
        ServerState.setTraderToken(this);
    }

    /**
     * Checks that a session is not invalid
     *
     */
    public boolean verifyToken(UUID token) {// Verify within 5 minutes
        return username.equals(ServerState.getTraderByToken(token).username);
    }

    /**
     * Invalidates a session
     */
    public void logout() {
        ServerState.invalidateTraderToken(token);
        token = null;
    }

    /**
     * Get a session token (preexisting)
     *
     */
    public UUID getToken() {
        return token;
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
        this.accounts.add(acct);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

}
