package com.stockexchange.stocks;

public class Stock {
	
	private final String name;
	private final String symbol;
	private int price;
	private int volume;
	private int unclaimedQuantity;
	private int totalQuantity;
	private int dailyHigh;
	private int dailyLow;
	
	public int getPrice(){
		return price;
	}
	
	public String getName(){
		return name;
	}
	
	public String getSymbol(){
		return symbol;
	}
}
