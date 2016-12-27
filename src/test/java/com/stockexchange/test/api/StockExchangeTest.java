package com.stockexchange.test.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.stockexchange.StockNames;
import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.connection.Connection;
import com.stockexchange.server.StockExchangeRegistry;
import com.stockexchange.server.api.StockExchangeEndpoint;
import com.stockexchange.stocks.quotes.Quote;

public class StockExchangeTest extends JerseyTest{
	@Override
	protected Application configure(){
		return new ResourceConfig(StockExchangeEndpoint.class);
	}
	
	
	@Test
	public void testGetQuote() throws IOException{
		Connection.website = target("/");
		StockExchangeRegistry.listStocks(StockNames.stocks);
		
		Quote a = StockExchangeRegistry.getStockExchange("NMS").getQuote("GOOG");
		Quote b = StockExchangeAPI.getQuote("NMS", "GOOG");
		assertEquals(a.getSymbol(), b.getSymbol());
	}
	
	@Test
	public void testGetQuotes() throws IOException{
		Connection.website = target("/");
		StockExchangeRegistry.listStocks(StockNames.stocks);
		
		List<Quote> a = StockExchangeRegistry.getStockExchange("NMS").getQuotes();
		List<Quote> b = StockExchangeAPI.getQuotes("NMS");
		
		assertTrue(a.containsAll(b));
	}
	
}
