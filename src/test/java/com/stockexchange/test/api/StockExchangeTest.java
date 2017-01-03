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
import com.stockexchange.server.StockMarket;
import com.stockexchange.server.api.StockExchangeEndpoint;
import com.stockexchange.server.data.ReutersAPI;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.StockDataPoint;
import com.stockexchange.stocks.quotes.Quote;

public class StockExchangeTest extends JerseyTest{
	@Override
	protected Application configure(){
		return new ResourceConfig(StockExchangeEndpoint.class);
	}
	
	
	@Test
	public void testGetQuote() throws IOException{
		Connection.website = target("/");
		StockMarket.listStocks(StockNames.stocks);
		
		Quote a = StockMarket.getStockExchange("NMS").getQuote("GOOG");
		Quote b = StockExchangeAPI.getQuote("NMS", "GOOG");
		assertEquals(a.getSymbol(), b.getSymbol());
	}
	
	@Test
	public void testGetQuotes() throws IOException{
		Connection.website = target("/");
		StockMarket.listStocks(StockNames.stocks);
		
		List<Quote> a = StockMarket.getStockExchange("NMS").getQuotes();
		List<Quote> b = StockExchangeAPI.getQuotes("NMS");
		
		assertTrue(a.containsAll(b));
	}
	
	@Test
	public void testGetDescription() throws IOException{
		Connection.website = target("/");
		Stock stock = StockMarket
				.getStockExchange("NMS")
				.getStock("GOOG");
		
		String c = ReutersAPI.getDescription("GOOG");
		String b = StockExchangeAPI.getStockDescription("NMS", "GOOG");
		String a = stock.getDescription();
		
		assertEquals(c,b);
		assertEquals(a,b);
	}
	
	@Test
	public void testGetHistory() throws IOException{
		Connection.website = target("/");
		StockMarket.listStocks(StockNames.stocks);
		
		
		Stock stock = StockMarket
				.getStockExchange("NMS")
				.getStock("GOOG");
		
		for (int i = 0; i<1440; i++){
			stock
			.getHistory()
			.add(new StockDataPoint(
				stock.getAsk(),
				stock.getBid()
			));
		}
		
		List<StockDataPoint> a = StockMarket
				.getStockExchange("NMS")
				.getStock("GOOG")
				.getHistory()
				.getAll();
		List<StockDataPoint> b = StockExchangeAPI.getStockHistory("NMS", "GOOG", 0);
		
		assertTrue(a.containsAll(b));
	}
	
}
