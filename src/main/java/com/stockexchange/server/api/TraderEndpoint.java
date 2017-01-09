package com.stockexchange.server.api;

import java.util.UUID;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response.Status;

import com.stockexchange.server.ServerState;
import com.stockexchange.traders.Trader;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.client.orders.RemoteOrder;
import com.stockexchange.server.orders.ExecutableOrder;

@Path("/trade")
public class TraderEndpoint {

    @POST
    @Path("/order/submit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitOrder(@HeaderParam("token") UUID token,
            RemoteOrder remOrder) {

        System.out.println(token);

        Trader trader = ServerState.getTraderByToken(token);
        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }

        if (!trader.getUsername().equals(remOrder.getTraderUsername())) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        ExecutableOrder execOrder = new ExecutableOrder(remOrder, trader);

        System.out.println(execOrder.getAccount());

        if (execOrder.getAccount() == null) {
            return Response.status(Status.CONFLICT).build();
        }

        execOrder.submit();
        return Response.ok().build();
    }

    @GET
    @Path("orders")
    @Produces("application/json")
    public Response getActiveOrders(@HeaderParam("token") UUID token) {
        return null;
    }

}
