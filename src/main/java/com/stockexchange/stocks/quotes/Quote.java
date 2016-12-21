package com.stockexchange.stocks.quotes;

import java.io.Serializable;

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
}
