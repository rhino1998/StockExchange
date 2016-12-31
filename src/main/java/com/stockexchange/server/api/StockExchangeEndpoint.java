package com.stockexchange.server.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.stockexchange.server.StockExchange;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;


@Path("/exchange/{exchange}")
public class StockExchangeEndpoint {

		
		
		@GET
		@Path("quote/{symbol}")
		@Produces("application/json")
		public Response getQuote(@PathParam("exchange") String exchange, @PathParam("symbol") String symbol){
			Quote quote = StockExchange.getStockExchange(exchange).getQuote(symbol);
			GenericEntity<Quote> entity = new GenericEntity<Quote>(quote, Quote.class);
			return Response.ok().entity(entity).build();
		}
		
		@GET
		@Path("quotes")
		@Produces("application/json")
		public Response getQuotes(@PathParam("exchange") String exchange){
			List<Quote> quotes = StockExchange.getStockExchange(exchange).getQuotes();
			GenericEntity<List<Quote>> entity = new GenericEntity<List<Quote>>(quotes){};
			return Response.ok().entity(entity).build();
		}
		
		
		
		
}
