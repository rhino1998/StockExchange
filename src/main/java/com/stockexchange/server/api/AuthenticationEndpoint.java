package com.stockexchange.server.api;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.stockexchange.server.MarketSystem;
import com.stockexchange.server.StockMarket;
import com.stockexchange.server.brokerages.Brokerage;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

@Path("/auth")
public class AuthenticationEndpoint {

    @POST
    @Path("/{brokerage}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateTrader(@PathParam("brokerage") String brokerageName, Credentials cred) {
        Brokerage brokerage = StockMarket.getBrokerage(brokerageName);
        if (brokerage == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        Trader trader = brokerage.authenticate(cred);

        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }

        trader.genToken();
        GenericEntity< Trader> entity = new GenericEntity< Trader>(trader, Trader.class);
        return Response.ok().entity(entity).build();
    }

    @POST
    @Path("/{brokerage}/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response refreshTrader(@PathParam("brokerage") String brokerage, String username) {
        Trader trader = StockMarket.getBrokerage(brokerage).refresh(username);
        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }
        GenericEntity< Trader> entity = new GenericEntity< Trader>(trader, Trader.class);
        return Response.ok().entity(entity).build();
    }

    @POST
    @Path("/{brokerage}/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logoutTrader(@PathParam("brokerage") String brokerage, String username) {
        Trader trader = StockMarket.getBrokerage(brokerage).refresh(username);
        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }
        trader.logout();
        return Response.ok().build();
    }

    @POST
    @Path("/register/{brokerage}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerTrader(@PathParam("brokerage") String brokerageName, Register reg) {
        Brokerage brokerage = StockMarket.getBrokerage(brokerageName);
        if (brokerage == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        Trader trader = brokerage.registerTrader(reg);

        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }

        trader.genToken();
        GenericEntity< Trader> entity = new GenericEntity< Trader>(trader, Trader.class);
        return Response.ok().entity(entity).build();
    }

}
