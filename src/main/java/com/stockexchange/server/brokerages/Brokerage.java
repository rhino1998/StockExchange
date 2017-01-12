package com.stockexchange.server.brokerages;

import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Brokerage {
    private final String name;
    private final Map<UUID, Account> accounts =
        new ConcurrentHashMap<UUID, Account>();
    private final Map<String, Trader> traders =
        new ConcurrentHashMap<String, Trader>();

    /**
     * Creates a new Brokerage object.
     *
     * @param name name of the brokerage
     */
    public Brokerage(String name) {
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @param uuid DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Account getAccount(UUID uuid) {
        return accounts.get(uuid);
    }

    /**
     * DOCUMENT ME!
     *
     * @param acct DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Account addAccount(Account acct) {
        return accounts.put(acct.getUUID(), acct);
    }

    /**
     * DOCUMENT ME!
     *
     * @param cred DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Trader authenticate(Credentials cred) {
        Trader trader = traders.get(cred.getUsername());

        if ((trader != null) && trader.authenticate(cred.getPassword())) {
            return trader;
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param reg DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Trader registerTrader(Register reg) {
        if (traders.containsKey(reg.getUsername())) {
            return traders.get(reg.getUsername());
        }

        Trader trader = new Trader(this.name, reg);
        traders.put(reg.getUsername(), trader);

        return trader;
    }

    /**
     * DOCUMENT ME!
     *
     * @param traderUsername DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Trader getTrader(String traderUsername) {
        return traders.get(traderUsername);
    }
}
