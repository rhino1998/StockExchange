package com.stockexchange.client.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.client.orders.RemoteOrder;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.traders.Trader;

public class TraderAPI {

    public static void getAccounts() {

    }

    public static boolean submitOrder(Trader trader, RemoteOrder remOrder) {
        WebTarget target = Connection.website.path("trade").path("order")
                .path("submit");

        Entity<RemoteOrder> entity = Entity.entity(remOrder,
                MediaType.APPLICATION_JSON);

        System.out.println(entity.toString());

        Response response = target.request(MediaType.APPLICATION_JSON)
                .header("token", trader.getToken()).post(entity);

        if (response.getStatus() != 200) {
            System.out.println(response.toString());
            return false;
        }

        response.readEntity(String.class);

        return true;
    }
}
