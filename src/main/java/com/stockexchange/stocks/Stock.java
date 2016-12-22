package com.stockexchange.stocks;

import java.util.HashMap;
import java.util.UUID;

import com.stockexchange.stocks.orders.Order;

public class Stock {
	
	private final String name;
	private final String symbol;
	private double price;
	private double dailyHigh;
	private double dailyLow;
	private double lowAsk;
	private double highBid;
	
	
	private int volume;
	private int unclaimedQuantity;
	private final long totalQuantity;
	
	private final HashMap<UUID, Order> pendingOrders = new HashMap<UUID, Order>();
	
	public Stock(String symbol, String name, double price, long quantity){
		this.name = name;
		this.symbol = symbol;
		this.price = price;
		this.totalQuantity = quantity;
	}
	
	public double getPrice(){
		return price;
	}
	
	public String getName(){
		return name;
	}
	
	public String getSymbol(){
		return symbol;
	}

	public int getUnclaimedQuantity() {
		return unclaimedQuantity;
	}

	public void setUnclaimedQuantity(int unclaimedQuantity) {
		this.unclaimedQuantity = unclaimedQuantity;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getDailyLow() {
		return dailyLow;
	}

	public void setDailyLow(int dailyLow) {
		this.dailyLow = dailyLow;
	}

	public int getDailyHigh() {
		return dailyHigh;
	}

	public void setDailyHigh(int dailyHigh) {
		this.dailyHigh = dailyHigh;
	}
}
