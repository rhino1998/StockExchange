package com.stockexchange.server.api;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
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
import com.stockexchange.server.data.GoogleFinanceAPI;
import com.stockexchange.server.data.ReutersAPI;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;


@Path("/exchange/{exchange}")
public class StockExchangeEndpoint {

		private static final HashMap<String, String> descriptionCache = new HashMap<String, String>();
		
		
		@GET
		@Path("quote/{symbol}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getQuote(@PathParam("exchange") String exchange, @PathParam("symbol") String symbol){
			Quote quote = StockExchange.getStockExchange(exchange).getQuote(symbol);
			GenericEntity<Quote> entity = new GenericEntity<Quote>(quote, Quote.class);
			return Response.ok().entity(entity).build();
		}
		
		@GET
		@Path("quotes")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getQuotes(@PathParam("exchange") String exchange){
			List<Quote> quotes = StockExchange.getStockExchange(exchange).getQuotes();
			GenericEntity<List<Quote>> entity = new GenericEntity<List<Quote>>(quotes){};
			return Response.ok().entity(entity).build();
		}
		
		@GET
		@Path("quote/{symbol}/description")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getQuoteDescription(
			@PathParam("exchange") String exchange,
			@PathParam("symbol") String symbol
		){
			String desc = "No description available";
			if (descriptionCache.containsKey(exchange+symbol)){
				desc = descriptionCache.get(exchange+symbol);
			}else{
				desc = ReutersAPI.getDescription(symbol);
				descriptionCache.put(exchange+symbol, desc);
			}

			GenericEntity<String> entity = new GenericEntity<String>(desc, String.class);
			return Response.ok().entity(entity).build();
		}
		
		@GET
		@Path("quote/{symbol}/chart")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getQuoteChart(
			@PathParam("exchange") String exchange,
			@PathParam("symbol") String symbol
		){
			GenericEntity<String> entity = new GenericEntity<String>(
					GoogleFinanceAPI.getChartURL(symbol),
					String.class
			);
			return Response.ok().entity(entity).build(); //MAYBE ADD CACHING
		}
		
		
		
		
}
