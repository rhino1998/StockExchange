package com.stockexchange.client.api;

import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.stocks.StockDataPoint;
import com.stockexchange.stocks.quotes.Quote;

public class StockExchangeAPI {

    public static List<Quote> getQuotes(String exchange) {
        WebTarget target = Connection.website.path(String.format(
                "/exchange/%s/quotes", exchange));
        Response response = target.request().get();

        if (response.getStatus() != 200) {
            return null;
        }

        List<Quote> quotes = response
                .readEntity(new GenericType<List<Quote>>() {
                });
        return quotes;
    }

    public static Quote getQuote(String exchange, String symbol) {
        WebTarget target = Connection.website.path(String.format(
                "/exchange/%s/%s", exchange, symbol));
        Response response = target.request().get();

        if (response.getStatus() != 200) {
            return null;
        }

        return response.readEntity(Quote.class);
    }

    public static String getStockDescription(String exchange, String symbol) {
        WebTarget target = Connection.website.path(String.format(
                "/exchange/%s/%s/description", exchange, symbol));
        Response response = target.request().get();

        if (response.getStatus() != 200) {
            return "Description not available";
        }

        return response.readEntity(String.class);
    }

    public static String getQuoteChartURL(String exchange, String symbol) {
        WebTarget target = Connection.website.path(String.format(
                "/exchange/%s/%s/chart", exchange, symbol));

        return target.request().get().readEntity(String.class);
    }

    public static List<StockDataPoint> getStockHistory(String exchange,
            String symbol, long offset) {
        WebTarget target = Connection.website.path(String.format(
                "/exchange/%s/%s/history/%s", exchange, symbol, offset));
        Response response = target.request().get();

        if (response.getStatus() != 200) {
            return null;
        }

        return response.readEntity(new GenericType<List<StockDataPoint>>() {
        });
    }

}
