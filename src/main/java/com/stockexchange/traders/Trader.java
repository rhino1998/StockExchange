package com.stockexchange.traders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.stockexchange.server.ServerState;
import com.stockexchange.server.StockMarket;
import com.stockexchange.server.brokerages.Brokerage;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.Register;

import org.glassfish.jersey.message.internal.Token;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.Serializable;

import java.util.ArrayList;
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
public class Trader implements Comparable<Trader>, Serializable {
    private static final transient long timeout = (long)3e11;
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
        if ((token != null) && (ServerState.getTraderByToken(token) != null)) {
            return;
        }

        token = UUID.randomUUID();
        ServerState.setTraderToken(this);
    }

    /**
     * Checks that a session is not invalid
     *
     */
    public boolean verifyToken(UUID token) {
        // Verify within 5 minutes

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
        if (System.nanoTime() < (this.tokenTimeout + timeout)) {
            this.tokenTimeout = System.nanoTime();

            return true;
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return this.name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param order DOCUMENT ME!
     */
    public void submitOrder(Order order) {
        return;
    }

    /**
     * DOCUMENT ME!
     *
     * @param other DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean equals(Object other) {
        return other instanceof Trader &&
               this.verifyToken(((Trader)other).token);
    }

    /**
     * DOCUMENT ME!
     *
     * @param symbol DOCUMENT ME!
     */
    public void getQuote(String symbol) {
        // TODO
    }

    /**
     * DOCUMENT ME!
     *
     * @param o DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int compareTo(Trader o) {
        return this.compareTo(o);
    }

    /**
     * DOCUMENT ME!
     *
     * @param pw DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean authenticate(String pw) {
        return BCrypt.checkpw(pw, this.pwHash);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getBrokerageName() {
        return brokerageName;
    }

    /**
     * DOCUMENT ME!
     *
     * @param acct DOCUMENT ME!
     */
    public void addAccount(Account acct) {
        acct.addOwner(this);
        this.accounts.add(acct);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * DOCUMENT ME!
     *
     * @param acctID DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @JsonIgnore
    public Account getAccount(UUID acctID) {
        return brokerage.getAccount(acctID);
    }
}
