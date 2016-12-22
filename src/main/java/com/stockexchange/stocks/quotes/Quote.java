package com.stockexchange.stocks.quotes;

import java.io.Serializable;

import com.stockexchange.stocks.Stock;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;

public class Quote implements Serializable{

	private final String name;
	private final String symbol;
	private final int dailyHigh;
	private final int dailyLow;
	private final int highBid;
	private final int lowAsk;
	private final int volume;
	private final int marketCap;
	
	
	public Quote(Stock stock){
		this.name = stock.getName();
		this.symbol = stock.getSymbol();
		this.dailyHigh = stock.getDailyHigh();
		this.dailyLow = stock.getDailyLow();
		this.highBid = stock.getHighBid();
		this.lowAsk = stock.getLowAsk();
		this.volume = stock.getVolume();
		this.marketCap = stock.getMarketCap();
	}
	
	public int getHighBid(){
		return highBid;
	}
	
	public int getlowAsk(){
		return lowAsk;
	}
	
	public String getName(){
		return name;
	}
	
	public String getVolume(){
		
	}
}
