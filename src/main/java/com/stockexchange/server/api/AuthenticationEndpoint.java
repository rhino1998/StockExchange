package com.stockexchange.server.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.stockexchange.server.StockExchange;
import com.stockexchange.traders.Credentials;
import com.stockexchange.traders.Trader;

@Path("/auth/{brokerage}")
public class AuthenticationEndpoint {

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response authenticateTrader(@PathParam("brokerage") String brokerage, Credentials cred){
		Trader trader = StockExchange.getBrokerage(brokerage).authenticate(cred);
		if (trader==null){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.status(Status.ACCEPTED).entity(Entity.json(trader)).build();
	}
	
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response registerTrader(@PathParam("brokerage") String brokerage, Register reg){
		Trader trader = StockExchange.getBrokerage(brokerage).registerTrader(reg);;
		if (trader==null){
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.status(Status.ACCEPTED).build();
	}
	
}
 