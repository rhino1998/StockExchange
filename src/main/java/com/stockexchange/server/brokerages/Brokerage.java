package com.stockexchange.server.brokerages;

import java.util.HashMap;
import java.util.UUID;

import com.stockexchange.traders.accounts.Account;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

public class Brokerage {

    private final String name;
    private final HashMap<UUID, Account> accounts = new HashMap<UUID, Account>();
    private final HashMap<String, Trader> traders = new HashMap<String, Trader>();

    public Brokerage(String name) {
        this.name = name;
    }

    public Account getAccount(UUID uuid) {
        return accounts.get(uuid);
    }

    public Account addAccount(Account acct) {
        return accounts.put(acct.getUUID(), acct);
    }

    public Trader authenticate(Credentials cred) {
        Trader trader = traders.get(cred.getUsername());
        if (trader != null && trader.authenticate(cred.getPassword())) {
            return trader;
        }
        return null;
    }

    public Trader registerTrader(Register reg) {
        if (traders.containsKey(reg.getUsername())) {
            return traders.get(reg.getUsername());
        }
        Trader trader = new Trader(this.name, reg);
        traders.put(reg.getUsername(), trader);
        return trader;
    }

    public Trader getTrader(String traderUsername) {
        return traders.get(traderUsername);
    }
}
