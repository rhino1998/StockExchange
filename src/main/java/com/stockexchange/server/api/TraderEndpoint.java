package com.stockexchange.server.api;

import com.stockexchange.client.orders.RemoteOrder;
import com.stockexchange.server.ServerState;
import com.stockexchange.server.orders.ExecutableOrder;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
@Path("/trader")
public class TraderEndpoint {
    /**
     * DOCUMENT ME!
     *
     * @param token DOCUMENT ME!
     * @param acctID DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @GET
    @Path("/portfolio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountPortfolio(@HeaderParam("token")
                                        UUID token, @QueryParam("account")
                                        UUID acctID) {
        Trader trader = ServerState.getTraderByToken(token);

        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }

        Account acct = trader.getAccount(acctID);

        GenericEntity<List<Quote>> entity =
        new GenericEntity<List<Quote>>(acct.getPortfolio()) {
        };

        return Response.ok().entity(entity).build();
    }

    /**
     * DOCUMENT ME!
     *
     * @param token DOCUMENT ME!
     * @param remOrder DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @POST
    @Path("/order/submit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitOrder(@HeaderParam("token")
                                UUID token, RemoteOrder remOrder) {

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

    /**
     * DOCUMENT ME!
     *
     * @param token DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @GET
    @Path("messages")
    @Produces("application/json")
    public Response getMessages(@HeaderParam("token")
                                UUID token, @QueryParam("account")
                                UUID acctID) {
        Trader trader = ServerState.getTraderByToken(token);

        if (trader == null) {
            return Response.status(Status.FORBIDDEN).build();
        }

        Account acct = trader.getAccount(acctID);

        GenericEntity<List<String>> entity =
        new GenericEntity<List<String>>(acct.getMessages()) {
        };

        return Response.ok().entity(entity).build();
    }
}
