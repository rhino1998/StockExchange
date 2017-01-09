package com.stockexchange.server;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.stockexchange.traders.Trader;

public class ServerState {

    private static final Cache<UUID, Trader> tokenRegistry = CacheBuilder
            .newBuilder().concurrencyLevel(4).maximumSize(10000)
            .expireAfterAccess(10, TimeUnit.MINUTES).build();

    public static Trader getTraderByToken(UUID token) {
        return tokenRegistry.getIfPresent(token);
    }

    public static void setTraderToken(Trader trader) {
        tokenRegistry.put(trader.getToken(), trader);
    }

    public static void invalidateTraderToken(UUID token) {
        tokenRegistry.invalidate(token);
    }
}