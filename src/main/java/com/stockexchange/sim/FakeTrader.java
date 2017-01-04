package com.stockexchange.sim;

import java.util.Timer;
import java.util.TimerTask;

import com.stockexchange.server.StockMarket;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Register;

public class FakeTrader {

    private Timer loop;
    private Trader trader;

    public FakeTrader(String name, String username, long freq) {
        trader = StockMarket.getBrokerage("FakeTraders").registerTrader(new Register(name, username, "1234"));
        loop = new Timer();
        loop.schedule(new TimerTask() {
            public void run() {
                trade();
            }
        }, (long) (Math.random() * freq), freq);

    }

    public void trade() {
        // TODO: figure out how to pretend to trade approximately reasonably
    }

    public void kill() {
        loop.cancel();
    }
}