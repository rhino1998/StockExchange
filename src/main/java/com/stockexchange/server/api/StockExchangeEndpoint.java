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

import com.stockexchange.server.StockMarket;
import com.stockexchange.server.data.GoogleFinanceAPI;
import com.stockexchange.server.data.ReutersAPI;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.StockDataPoint;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;

@Path("/exchange/{exchange}")
public class StockExchangeEndpoint {

    @GET
    @Path("{symbol}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuote(@PathParam("exchange") String exchange, @PathParam("symbol") String symbol) {
        Quote quote = StockMarket.getStockExchange(exchange).getQuote(symbol);
        GenericEntity< Quote> entity = new GenericEntity< Quote>(quote, Quote.class);
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("quotes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuotes(@PathParam("exchange") String exchange) {
        List< Quote> quotes = StockMarket.getStockExchange(exchange).getQuotes();
        GenericEntity< List< Quote>> entity = new GenericEntity< List< Quote>>(quotes) {
        };
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{symbol}/description")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuoteDescription(@PathParam("exchange") String exchange, @PathParam("symbol") String symbol) {
        Stock stock = StockMarket.getStockExchange(exchange).getStock(symbol);
        if (!stock.hasDescription()) {
            String desc = ReutersAPI.getDescription(symbol);
            stock.setDescription(desc);
        }

        GenericEntity< String> entity = new GenericEntity< String>(stock.getDescription(), String.class);
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("{symbol}/chart")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuoteChart(@PathParam("exchange") String exchange, @PathParam("symbol") String symbol) {
        GenericEntity< String> entity = new GenericEntity< String>(GoogleFinanceAPI.getChartURL(symbol), String.class);
        return Response.ok().entity(entity).build(); // MAYBE ADD CACHING
    }

    @GET
    @Path("{symbol}/history/{offset}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuoteHistory(@PathParam("exchange") String exchange, @PathParam("symbol") String symbol,
            @PathParam("offset") long offset) {

        List< StockDataPoint> history = StockMarket.getStockExchange(exchange).getStock(symbol).getHistory().getAll();
        int i = 0;
        for (i = 0; i < history.size(); i++) {
            if (history.get(i).getTime() > offset) {
                break;
            }
        }
        GenericEntity< List< StockDataPoint>> entity = new GenericEntity< List< StockDataPoint>>(history.subList(i,
                history.size())) {
        };
        return Response.ok().entity(entity).build(); // MAYBE ADD CACHING
    }

}
