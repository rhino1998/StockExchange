package com.stockexchange.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.stockexchange.server.brokerages.Brokerage;
import com.stockexchange.server.data.YahooFinanceAPI;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.stocks.quotes.enums.QuoteSortBy;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;

public class MarketSystem {

	private final String name;
	private final HashMap<String, Stock> stocks = new HashMap<String, Stock>();
	
	public MarketSystem(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void listStock(String symbol) throws IOException{
		Stock stock = new Stock(YahooFinanceAPI.getQuote(symbol));
		stocks.put(stock.getSymbol(), stock);
	}
	
	public void listStocks(String... symbols) throws IOException{
		for (Quote quote : YahooFinanceAPI.getQuotes(symbols)){
			Stock stock = new Stock(quote);
			stocks.put(stock.getSymbol(), stock);
		}
	}
	
	public void listStock(Stock stock){
		stocks.put(stock.getSymbol(), stock);
	}
	
	public Quote getQuote(String symbol){
		return new Quote(stocks.get(symbol));
	}
	
	public List<Quote> getQuotes(){
		ArrayList<Quote> quotes = new ArrayList<Quote>(stocks.size());
		for (Stock stock: this.stocks.values()){
			quotes.add(new Quote(stock));
		}
		return quotes;
	}

	public Stock getStock(String symbol) {
		return stocks.get(symbol);
	}
}
