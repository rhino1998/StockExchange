package com.stockexchange.server;

import java.util.HashMap;

import com.stockexchange.server.brokerages.Brokerage;
import com.stockexchange.stocks.Stock;
import com.stockexchange.traders.Credentials;
import com.stockexchange.traders.Trader;

public class StockExchange {

	
	private static final HashMap<String, Brokerage> brokerages = new HashMap<String, Brokerage>();
	private static final HashMap<String, Stock> stocks = new HashMap<String, Stock>();
	
	
	
	public static void addBrokerage(String name){
		if (!brokerages.containsKey(name)){
			//TODO Error of some sort;
		}
		brokerages.put(name, new Brokerage());
	}
	
	public static Brokerage getBrokerage(String name){
		return brokerages.get(name);
	}
	
	public static void listStock(String symbol, String name, double price, long quantity){
		if (!stocks.containsKey(symbol)){
			//TODO error of some sort
		}
		stocks.put(symbol, new Stock(symbol,name,price, quantity));
	}
}
