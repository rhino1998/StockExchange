package com.stockexchange.stocks;

import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

import com.stockexchange.server.MarketSystem;
import com.stockexchange.server.StockExchange;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.util.History;

import javafx.application.Platform;

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
	private String description;
	private final Timer historian;
	private final History<StockDataPoint> history = 
			new History<StockDataPoint>(720);
	private final PriorityQueue<Order> sellOrders = new PriorityQueue<Order>();
	private final PriorityQueue<Order> buyOrders = new PriorityQueue<Order>();
	private final MarketSystem exchange;
	
	
	/**
	 * Create a stock from a snapshot of it, typically retrieved elsewhere.
	 * @param quote a quote to initialize stock state
	 */
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
		
		
		
		final Stock stock = this;
		historian = new Timer();
		historian.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				stock.bid += Math.random()*100-45;
				stock.ask += Math.random()*100-45;
				history.add(new StockDataPoint(stock));
			}
			
		}, 0, 10000);
	}
	
	
	/**
	 * Update the stock from new data
	 * @param quote A snapshot of a stock's state
	 */
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
	
	public void placeOrder(Order order){
		if (order.isBuy()){
			buyOrders.add(order);
			if (this.getBid()>this.getAsk()){
				if (sellOrders.isEmpty()){
				}
			}
				
			return;
		}
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getAsk() {
		return Math.min(
			ask, 
			sellOrders.isEmpty() || sellOrders.peek().isMarket()? 
				Double.MAX_VALUE
				:
				Double.MAX_VALUE
		);
	}

	public double getBid() {
		return Math.max(
				bid, 
				sellOrders.isEmpty() || buyOrders.peek().isMarket()? 
					0
					:
					0
			);
	}


	public double getOpen() {
		return open;
	}
	
	public double getPreviousClose() {
		return previousClose;
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
	
	public History<StockDataPoint> getHistory(){
		return this.history;
	}
	
	public MarketSystem getExchange(){
		return exchange;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean hasDescription() {
		return this.description!=null;
	}
}
