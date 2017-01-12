package com.stockexchange.client.api;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.server.brokerages.Brokerage;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class AuthenticationAPI {
    /**
     * DOCUMENT ME!
     *
     * @param brokerage DOCUMENT ME!
     * @param cred DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Trader authenticateTrader(String brokerage, Credentials cred) {
        WebTarget target = Connection.website.path("auth").path(brokerage);

        Entity<Credentials> entity =
            Entity.entity(cred, MediaType.APPLICATION_JSON);
        Response response =
            target.request(MediaType.APPLICATION_JSON).post(entity);

        if (response.getStatus() != 200) {
            return null;
        }

        return response.readEntity(Trader.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @param trader DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Trader refresh(Trader trader) {
        WebTarget target =
            Connection.website.path("auth").path(trader.getBrokerageName())
            .path("refresh");

        Entity<String> entity =
            Entity.entity(trader.getUsername(), MediaType.APPLICATION_JSON);

        Response response =
            target.request(MediaType.APPLICATION_JSON)
            .header("token", trader.getToken()).post(entity);

        if (response.getStatus() != 200) {
            return null;
        }

        return response.readEntity(Trader.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @param trader DOCUMENT ME!
     */
    public static void logoutTrader(Trader trader) {
        WebTarget target =
            Connection.website.path("auth").path(trader.getBrokerageName())
            .path("logout");

        Entity<String> entity =
            Entity.entity(trader.getUsername(), MediaType.APPLICATION_JSON);

        target.request(MediaType.APPLICATION_JSON)
        .header("token", trader.getToken()).post(entity);
    }

    /**
     * DOCUMENT ME!
     *
     * @param brokerage DOCUMENT ME!
     * @param reg DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Trader registerTrader(String brokerage, Register reg) {
        WebTarget target =
            Connection.website.path("auth").path(brokerage).path("register");

        Entity<Register> entity =
            Entity.entity(reg, MediaType.APPLICATION_JSON);

        Response response =
            target.request(MediaType.APPLICATION_JSON).post(entity);

        // System.out.println(response.readEntity(String.class));
        if (response.getStatus() != 200) {
            return null;
        }

        return response.readEntity(Trader.class);
    }
}
