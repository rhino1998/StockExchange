package com.stockexchange.stocks.orders;

import java.io.Serializable;
import java.util.UUID;

import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;

public class Order implements Serializable{

	
	private final UUID uuid;
	
	private final Trader trader;
	private final Account account;
	private final Stock stock;
	private final TransactionType transactionType;
	private final OrderType orderType;
	private final int limitPrice;
	private final int volume;
	
}
