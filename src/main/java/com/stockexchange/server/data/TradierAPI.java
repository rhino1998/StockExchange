package com.stockexchange.server.data;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stockexchange.stocks.quotes.Quote;

public class TradierAPI {

	
	private static final Client client = ClientBuilder.newClient();
	private static final WebTarget site = client.target("https://sandbox.tradier.com/v1");
	private static final String apiKey = "UnhzCDQyHMhuPODa5RAt1OO0GHtI";
	
	public static List<Quote> getQuotes(String... symbols){
		String syms = Arrays.toString(symbols);
		
		WebTarget target = site.path("/markets/quotes")
				.queryParam(
						"symbols",
						syms
						.substring(1, syms.length()-1)
						.replaceAll(" ", "")
				);
		
		List<Quote> quotes = target
				.request()
				.header("Authorization", String.format("Bearer %s", apiKey))
				.accept(MediaType.APPLICATION_XML)
				.get(new GenericType<List<Quote>>() {});
		
		
		//System.out.println(response.readEntity(String.class));
		return quotes;
		
		
	}
}