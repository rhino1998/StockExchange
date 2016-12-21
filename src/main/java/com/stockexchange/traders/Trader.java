package com.stockexchange.traders;

import java.util.HashMap;
import java.util.UUID;

import org.glassfish.jersey.message.internal.Token;

import StockExchange.traders.Brokerage;
import StockExchange.traders.Trader;

import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.OrderView;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.enums.State;

public class Trader implements Comparable{

	
	private final String username;
	private String name;
	private String pswd;
	private HashMap<String,Account> accounts;
	private HashMap<UUID, Order> pendingOrders;
	private Brokerage brokerage;
	
	
	private transient State state;
	private transient Token token;
	
	public Trader(Brokerage b, String n, String p){
		brokerage = b;
		name = n.toUpperCase();
		pswd = p;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getName(){
		return this.name;
	}
	
	public OrderView getPendingOrderView(){
		return null;
	}
	public void submitOrder(Order order){
		return;
	}
	
	public boolean equals(Object other){
		try{
			if(this.compareTo((Trader) other)==0){
				return true;
			}else{
				return false;
			}
		}catch(ClassCastException e){
			System.out.println("ERROR");
			return false;
		}
	}

	public Password getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public int compareTo(Object arg0) {
		try{
			int ans = this.compareTo((Trader)arg0);
			return ans;
		}catch(ClassCastException e){
			System.out.println("ERROR");
			//TODO
			return 123456789;
		}
	}
	
	public void getQuote(String symbol){
		//TODO
	}
}
