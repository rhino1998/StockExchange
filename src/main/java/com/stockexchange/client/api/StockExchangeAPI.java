package com.stockexchange.client.api;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.stocks.StockDataPoint;
import com.stockexchange.stocks.quotes.Quote;

import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class StockExchangeAPI {
    /**
     * DOCUMENT ME!
     *
     * @param exchange DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static List<Quote> getQuotes() {
        WebTarget target = Connection.website.path("exchange").path("quotes");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200) {
            System.out.println(response);
            return null;
        }

        List<Quote> quotes =
        response.readEntity(new GenericType<List<Quote>>() {
        });

        return quotes;
    }

    /**
     * DOCUMENT ME!
     *
     * @param exchange DOCUMENT ME!
     * @param symbol DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Quote getQuote(String symbol) {
        WebTarget target = Connection.website.path("exchange").path(symbol);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200) {
            return null;
        }

        return response.readEntity(Quote.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @param exchange DOCUMENT ME!
     * @param symbol DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String getStockDescription(String symbol) {
        WebTarget target =
            Connection.website.path("exchange").path(symbol)
            .path("description");

        Response response = target.request(MediaType.TEXT_PLAIN).get();

        if (response.getStatus() != 200) {
            return "Description not available";
        }

        return response.readEntity(new GenericType<String>() {
        });
    }

    /**
     * DOCUMENT ME!
     *
     * @param exchange DOCUMENT ME!
     * @param symbol DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String getQuoteChartURL(String exchange, String symbol) {
        WebTarget target =
            Connection.website.path("exchange").path(exchange).path(symbol)
            .path("chart");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        return response.readEntity(String.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @param exchange DOCUMENT ME!
     * @param symbol DOCUMENT ME!
     * @param offset DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static List<StockDataPoint> getStockHistory(String symbol,
            long offset) {
        WebTarget target =
            Connection.website.path("exchange").path(symbol).path("history")
            .path(Long.toString(offset));

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != 200) {
            return null;
        }

        return response.readEntity(new GenericType<List<StockDataPoint>>() {
        });
    }
}
