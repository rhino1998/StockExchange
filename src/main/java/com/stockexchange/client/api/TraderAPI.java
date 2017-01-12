package com.stockexchange.client.api;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.client.orders.RemoteOrder;
import com.stockexchange.stocks.StockDataPoint;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class TraderAPI {
    /**
     * DOCUMENT ME!
     *
     * @param trader DOCUMENT ME!
     * @param remOrder DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static boolean submitOrder(Trader trader, RemoteOrder remOrder) {
        WebTarget target =
            Connection.website.path("trader").path("order").path("submit");

        Entity<RemoteOrder> entity =
            Entity.entity(remOrder, MediaType.APPLICATION_JSON);


        Response response =
            target.request(MediaType.APPLICATION_JSON)
            .header("token", trader.getToken()).post(entity);

        if (response.getStatus() != 200) {
            return false;
        }

        response.readEntity(String.class);

        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param trader DOCUMENT ME!
     * @param acct DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static List<Quote> getPortfolio(Trader trader, Account acct) {


        if (!trader.getAccounts().contains(acct)) {
            return null;
        }

        WebTarget target =
            Connection.website.path("trader").path("portfolio")
            .queryParam("account", acct.getUUID());

        Response response =
            target.request(MediaType.APPLICATION_JSON)
            .header("token", trader.getToken()).get();

        if (response.getStatus() != 200) {

            return null;
        }

        List<Quote> quotes =
        response.readEntity(new GenericType<List<Quote>>() {
        });

        return quotes;
    }

    public static List<String> getMessages(Trader trader, Account acct) {


        if (!trader.getAccounts().contains(acct)) {
            return null;
        }

        WebTarget target =
            Connection.website.path("trader").path("messages")
            .queryParam("account", acct.getUUID());

        Response response =
            target.request(MediaType.APPLICATION_JSON)
            .header("token", trader.getToken()).get();

        if (response.getStatus() != 200) {

            return null;
        }

        List<String> messages =
        response.readEntity(new GenericType<List<String>>() {
        });

        return messages;
    }
}
