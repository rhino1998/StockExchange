package com.stockexchange.stocks;

import java.util.HashMap;
import java.util.UUID;

import com.stockexchange.server.MarketSystem;
import com.stockexchange.server.StockExchange;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.quotes.Quote;

public class Stock {
	
	private final String name;
	private final String symbol;
	private double dailyHigh;
	private double dailyLow;
	private double ask;
	private double bid;
	private double open;
	private double previousClose;
	private double marketCap;
	
	
	private int volume;
	
	private final HashMap<UUID, Order> pendingOrders = new HashMap<UUID, Order>();
	private final MarketSystem exchange;
	
	public Stock(Quote quote){
		this.name = quote.getName();
		this.symbol = quote.getSymbol();
		this.ask = quote.getAsk();
		this.bid = quote.getBid();
		this.volume = quote.getVolume();
		this.open = quote.getOpen();
		this.previousClose=quote.getPreviousClose();
		this.marketCap = quote.getMarketCap();
		this.dailyHigh = quote.getDailyHigh();
		this.dailyLow = quote.getDailyLow();
		this.exchange = StockExchange.getStockExchange(quote.getExchange());
		
	}
	
	public void update(Quote quote){
		if (!this.symbol.equals(quote.getSymbol())){
			return;
		}
		
		this.ask = quote.getAsk();
		this.bid = quote.getBid();
		this.volume = quote.getVolume();
		this.open = quote.getOpen();
		this.previousClose=quote.getPreviousClose();
		this.marketCap = quote.getMarketCap();
		this.dailyHigh = quote.getDailyHigh();
		this.dailyLow = quote.getDailyLow();
	}
	
	public String getName(){
		return name;
	}
	
	public String getSymbol(){
		return symbol;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getAsk() {
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getPreviousClose() {
		return previousClose;
	}

	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}

	public double getDailyHigh() {
		return dailyHigh;
	}

	public double getDailyLow() {
		return dailyLow;
	}

	public double getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(double marketCap) {
		this.marketCap = marketCap;
	}

	public void setDailyLow(double dailyLow) {
		this.dailyLow = dailyLow;
	}

	public void setDailyHigh(double dailyHigh) {
		this.dailyHigh = dailyHigh;
	}
	
	public MarketSystem getExchange(){
		return exchange;
	}
}
