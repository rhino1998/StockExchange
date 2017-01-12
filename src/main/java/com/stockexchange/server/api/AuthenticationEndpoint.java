package com.stockexchange.server.api;

import com.stockexchange.server.ServerState;
import com.stockexchange.server.StockMarket;
import com.stockexchange.server.brokerages.Brokerage;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
@Path("/auth")
public class AuthenticationEndpoint {
    /**
     * DOCUMENT ME!
     *
     * @param brokerageName DOCUMENT ME!
     * @param cred DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @POST
    @Path("/{brokerage}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateTrader(
        @PathParam("brokerage")

        String brokerageName, Credentials cred) {
        Brokerage brokerage = StockMarket.getBrokerage(brokerageName);

        if (brokerage == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        Trader trader = brokerage.authenticate(cred);

        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }

        trader.genToken();

        GenericEntity<Trader> entity =
            new GenericEntity<Trader>(trader, Trader.class);

        return Response.ok().entity(entity).build();
    }

    /**
     * DOCUMENT ME!
     *
     * @param brokerage DOCUMENT ME!
     * @param token DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @POST
    @Path("/{brokerage}/refresh")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response refreshTrader(@PathParam("brokerage")
                                  String brokerage, @HeaderParam("token")
                                  UUID token) {
        Trader trader = ServerState.getTraderByToken(token);

        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }

        GenericEntity<Trader> entity =
            new GenericEntity<Trader>(trader, Trader.class);

        return Response.ok().entity(entity).build();
    }

    /**
     * DOCUMENT ME!
     *
     * @param brokerage DOCUMENT ME!
     * @param token DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @POST
    @Path("/{brokerage}/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logoutTrader(@PathParam("brokerage")
                                 String brokerage, @HeaderParam("token")
                                 UUID token) {
        Trader trader = ServerState.getTraderByToken(token);

        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }

        trader.logout();

        return Response.ok().build();
    }

    /**
     * DOCUMENT ME!
     *
     * @param brokerageName DOCUMENT ME!
     * @param reg DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @POST
    @Path("/{brokerage}/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerTrader(
        @PathParam("brokerage")
        String brokerageName, Register reg) {
        Brokerage brokerage = StockMarket.getBrokerage(brokerageName);

        if (brokerage == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        Trader trader = brokerage.registerTrader(reg);

        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }

        trader.genToken();

        GenericEntity<Trader> entity =
            new GenericEntity<Trader>(trader, Trader.class);

        return Response.ok().entity(entity).build();
    }
}
