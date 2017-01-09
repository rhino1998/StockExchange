package com.stockexchange.client.api;

import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.stocks.StockDataPoint;
import com.stockexchange.stocks.quotes.Quote;

public class StockExchangeAPI {

    public static List<Quote> getQuotes(String exchange) {
        WebTarget target = Connection.website.path("exchange").path(exchange)
                .path("quotes");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200) {
            return null;
        }

        List<Quote> quotes = response
                .readEntity(new GenericType<List<Quote>>() {
                });
        return quotes;
    }

    public static Quote getQuote(String exchange, String symbol) {
        WebTarget target = Connection.website.path("exchange").path(exchange)
                .path(symbol);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200) {
            return null;
        }

        return response.readEntity(Quote.class);
    }

    public static String getStockDescription(String exchange, String symbol) {
        WebTarget target = Connection.website.path("exchange").path(exchange)
                .path(symbol).path("description");

        Response response = target.request(MediaType.TEXT_PLAIN).get();

        if (response.getStatus() != 200) {
            return "Description not available";
        }

        return response.readEntity(new GenericType<String>() {
        });
    }

    public static String getQuoteChartURL(String exchange, String symbol) {
        WebTarget target = Connection.website.path("exchange").path(exchange)
                .path(symbol).path("chart");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        return response.readEntity(String.class);
    }

    public static List<StockDataPoint> getStockHistory(String exchange,
            String symbol, long offset) {
        WebTarget target = Connection.website.path("exchange").path(exchange)
                .path(symbol).path("history").path(Long.toString(offset));

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200) {
            return null;
        }

        return response.readEntity(new GenericType<List<StockDataPoint>>() {
        });
    }

}
