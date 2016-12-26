package com.stockexchange.server.api;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stockexchange.stocks.orders.Order;

@Path("/trader")
public class TraderEndpoint {

	
	
	
	@POST
	@Path("order")
	@Consumes("application/json")
	@Produces("application/json")
	public Response submitOrder(Order order){
		return null;
	}
	
	@GET
	@Path("orders")
	@Produces("application/json")
	public Response getActiveOrders(){
		return null;
	}
	
	
	
	
}
