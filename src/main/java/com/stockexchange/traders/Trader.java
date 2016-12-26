package com.stockexchange.traders;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import org.glassfish.jersey.message.internal.Token;

import com.stockexchange.server.brokerages.Brokerage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockexchange.server.StockExchangeRegistry;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.OrderView;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.Register;
import com.stockexchange.transport.enums.State;

public class Trader implements Comparable<Trader>, Serializable{

	@JsonProperty private String username;
	@JsonProperty private String name;
	@JsonProperty private Password password;
	@JsonProperty private HashMap<String,Account> accounts;
	@JsonProperty private HashMap<UUID, Order> pendingOrders;
	
	private transient Brokerage brokerage;
	
	//private transient Token token;
	
	public Trader(String brokerage, String name, String username, Password pw){
		this.brokerage = StockExchangeRegistry.getBrokerage(brokerage);
		this.username = username;
		this.name = name.toUpperCase();
		this.password = pw;
		this.accounts = new HashMap<String, Account>();
		this.pendingOrders = new HashMap<UUID, Order>();
	}
	
	public Trader(String brokerage, Register reg){
		this.brokerage = StockExchangeRegistry.getBrokerage(brokerage);
		this.username = reg.getUsername();
		this.name = reg.getName();
		this.password = reg.getPassword();
		this.accounts = new HashMap<String, Account>();
		this.pendingOrders = new HashMap<UUID, Order>();
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getName(){
		return this.name;
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

	/*public int compareTo(Object arg0) {
		try{
			int ans = this.compareTo((Trader)arg0);
			return ans;
		}catch(ClassCastException e){
			System.out.println("ERROR");
			//TODO
			return 123456789;
		}
	}
	*/
	
	public void getQuote(String symbol){
		//TODO
	}

	public int compareTo(Trader o) {
		return this.compareTo(o);
	}

	public boolean authenticate(Password pw) {
		return this.password.equals(pw);
	}
}
