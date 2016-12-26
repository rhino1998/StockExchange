package com.stockexchange.transport.dummies;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stockexchange.stocks.quotes.Quote;


@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDummy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1018855070247498616L;
	public String symbol;
	public String name;
	public String exchange;
	public double ask;
	public double bid;
	public double dailyLow;
	public double dailyHigh;
	public double open;
	public double previousClose;
	public double marketCap;
	public int volume;
	
	public QuoteDummy(){}
	
	public QuoteDummy(Quote quote){
		this.setSymbol(quote.getSymbol());
		this.setName(quote.getName());
		this.setExchange(quote.getExchange());
		this.setAsk(quote.getAsk());
		this.setBid(quote.getBid());
		this.setAsk(quote.getAsk());
		this.setBid(quote.getBid());
		this.setAsk(quote.getAsk());
		this.setBid(quote.getBid());
		this.setOpen(quote.getOpen());
		this.setPreviousClose(quote.getPreviousClose());
		this.setVolume(quote.getVolume());
		this.setMarketCap(quote.getMarketCap());
		
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getExchange() {
		return exchange;
	}


	public void setExchange(String exchange) {
		this.exchange = exchange;
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


	public double getMarketCap() {
		return marketCap;
	}


	public void setMarketCap(double marketCap) {
		this.marketCap = marketCap;
	}


	public int getVolume() {
		return volume;
	}


	public void setVolume(int volume) {
		this.volume = volume;
	}


	public double getPreviousClose() {
		return previousClose;
	}


	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}


	public double getOpen() {
		return open;
	}


	public void setOpen(double open) {
		this.open = open;
	}


	public double getDailyHigh() {
		return dailyHigh;
	}


	public void setDailyHigh(double dailyHigh) {
		this.dailyHigh = dailyHigh;
	}


	public double getDailyLow() {
		return dailyLow;
	}


	public void setDailyLow(double dailyLow) {
		this.dailyLow = dailyLow;
	}
}
