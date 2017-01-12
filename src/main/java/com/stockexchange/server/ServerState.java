package com.stockexchange.server;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.stockexchange.traders.Trader;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ServerState {
    private static final Cache<UUID, Trader> tokenRegistry =
        CacheBuilder.newBuilder().concurrencyLevel(4).maximumSize(10000)
        .expireAfterAccess(10, TimeUnit.MINUTES).build();

    /**
     * DOCUMENT ME!
     *
     * @param token DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Trader getTraderByToken(UUID token) {
        return tokenRegistry.getIfPresent(token);
    }

    /**
     * DOCUMENT ME!
     *
     * @param trader DOCUMENT ME!
     */
    public static void setTraderToken(Trader trader) {
        tokenRegistry.put(trader.getToken(), trader);
    }

    /**
     * DOCUMENT ME!
     *
     * @param token DOCUMENT ME!
     */
    public static void invalidateTraderToken(UUID token) {
        tokenRegistry.invalidate(token);
    }
}
