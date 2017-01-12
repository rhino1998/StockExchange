package com.stockexchange.test.api;

import com.stockexchange.StockNames;
import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.connection.Connection;
import com.stockexchange.server.StockMarket;
import com.stockexchange.server.api.StockExchangeEndpoint;
import com.stockexchange.server.data.ReutersAPI;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.StockDataPoint;
import com.stockexchange.stocks.quotes.Quote;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTestNg;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.io.IOException;

import java.util.List;

import javax.ws.rs.core.Application;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class StockExchangeTest extends JerseyTestNg.ContainerPerClassTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(StockExchangeEndpoint.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     */
    @Test(priority = 1)
    public void testGetQuote() throws IOException {
        Connection.website = target("/");
        StockMarket.listStocks(StockNames.stocks);

        Quote a = StockMarket.getQuote("GOOG");
        Quote b = StockExchangeAPI.getQuote("GOOG");
        assertEquals(a.getSymbol(), b.getSymbol());
    }

    /**
     * DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     */
    @Test(priority = 1)
    public void testGetQuotes() throws IOException {
        Connection.website = target("/");
        StockMarket.listStocks(StockNames.stocks);

        List<Quote> a = StockMarket.getQuotes();
        List<Quote> b = StockExchangeAPI.getQuotes();

        //System.out.println(a);
        //System.out.println(b);

        assertTrue(a.containsAll(b));
    }

    /**
     * DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     */
    @Test(priority = 2)
    public void testGetDescription() throws IOException {
        Connection.website = target("/");

        Stock stock = StockMarket.getStock("GOOG");

        String c = ReutersAPI.getDescription("GOOG");
        String b = StockExchangeAPI.getStockDescription("GOOG");
        String a = stock.getDescription();

        assertEquals(c, b);
        assertEquals(a, b);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     */
    @Test(priority = 2)
    public void testGetHistory() throws IOException {
        Connection.website = target("/");
        StockMarket.listStocks(StockNames.stocks);

        Stock stock = StockMarket.getStock("GOOG");

        for (int i = 0; i < 1440; i++) {
            stock.getHistory()
            .add(new StockDataPoint(stock.getAsk(), stock.getBid()));
        }

        List<StockDataPoint> a =
            StockMarket.getStock("GOOG").getHistory().getAll();
        List<StockDataPoint> b = StockExchangeAPI.getStockHistory("GOOG", 0L);

        assertTrue(a.containsAll(b));
    }
}
