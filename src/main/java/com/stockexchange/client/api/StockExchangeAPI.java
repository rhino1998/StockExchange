package com.stockexchange.client.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.dummies.QuoteDummy;

public class StockExchangeAPI {

	public static List<Quote> getQuotes(String exchange){
		WebTarget target = Connection.website.path(String.format("/exchange/%s/quotes", exchange));
		List<QuoteDummy> quoteDummies = target.request().get().readEntity(new GenericType<List<QuoteDummy>>(){});
		List<Quote> quotes = new ArrayList<Quote>(quoteDummies.size());
		for (QuoteDummy quoteDummy : quoteDummies){
			quotes.add(new Quote(quoteDummy));
		}
		return quotes;	}
	
	public static Quote getQuote(String exchange, String symbol){
		WebTarget target = Connection.website.path(String.format("/exchange/%s/quote/%s", exchange,symbol));
		return new Quote(target.request().get().readEntity(QuoteDummy.class));
	}
}
