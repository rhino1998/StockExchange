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
	private final String symbol;
	private final TransactionType transactionType;
	private final OrderType orderType;
	private final int limitPrice;
	private final int volume;
	
	public Order(Trader t, Account a, String s, TransactionType tt, OrderType o, int l, int v){
		trader = t;
		account = a;
		symbol = s;
		transactionType = tt;
		orderType = o;
		limitPrice = l;
		volume = v;
		uuid = new UUID(System.nanoTime(), System.nanoTime()+1);
	}
	
	public Trader getTrader(){
		return trader;
	}
	
	public String getSymbol(){
		return symbol;
	}
	
	public boolean isBuy(){
		return (transactionType==TransactionType.BUY);
	}
	
	public boolean isSell(){
		return (transactionType==TransactionType.SELL);
	}
	
	public boolean isMarket(){
		return (orderType==OrderType.MARKET);
	}
	
	public boolean isLimit(){
		return (orderType==OrderType.LIMIT);
	}
	
	public int getShares(){
		//TODO
		return 123456789;
	}
	
	public double getPrice(){
		//TODO
		return 123456789;
	}
	
	public void subtractShares(){
		//TODO
		try{
			
		}catch(IllegalArgumentException e){
			
		}
		return;
	}
	
	
	
}
