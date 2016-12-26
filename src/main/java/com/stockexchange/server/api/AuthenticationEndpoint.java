package com.stockexchange.server.api;

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

import com.stockexchange.server.StockExchange;
import com.stockexchange.server.StockExchangeRegistry;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Password;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;


@Path("/auth")
public class AuthenticationEndpoint {

	
	
	@POST
	@Path("/{brokerage}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateTrader(@PathParam("brokerage") String brokerage, Credentials cred){
		Trader trader = StockExchangeRegistry.getBrokerage(brokerage).authenticate(cred);
		GenericEntity<Trader> entity = new GenericEntity<Trader>(trader, Trader.class);
		return Response.ok().entity(entity).build();
	}
	
	
	
	//@Consumes(MediaType.APPLICATION_JSON)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response auth(){
		Password pass = new Password("admin");
		Trader trader = StockExchangeRegistry.getBrokerage("rhino").registerTrader(new Register("test", "admin", "admin"));
				//StockExchangeRegistry.getBrokerage("rhino").authenticate(new Credentials("admin", "admin"));
		GenericEntity<Trader> entity = new GenericEntity<Trader>(trader, Trader.class);
		return Response.ok().entity(entity).build();
	}
	
	@POST
	@Path("/register/{brokerage}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerTrader(@PathParam("brokerage") String brokerage, Register reg){
		Trader trader = StockExchangeRegistry.getBrokerage(brokerage).registerTrader(reg);
		System.out.println(trader.getName());
		GenericEntity<Trader> entity = new GenericEntity<Trader>(trader, Trader.class);
		return Response.ok().entity("test").build();
	}
}
 