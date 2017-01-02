package com.stockexchange.traders;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import org.glassfish.jersey.message.internal.Token;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.stockexchange.server.brokerages.Brokerage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockexchange.server.StockExchange;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.Register;
import com.stockexchange.transport.enums.State;

public class Trader implements Comparable<Trader>, Serializable{

	private transient final static long timeout = (long) 3e11;
	
	@JsonProperty private String username;
	@JsonProperty private String name;
	@JsonProperty private HashMap<String,Account> accounts;
	@JsonProperty private HashMap<UUID, Order> pendingOrders;
	@JsonProperty private UUID token;
	@JsonProperty private String brokerageName;
	
	
	private transient String pwHash;
	private transient Brokerage brokerage;
	private transient long tokenTimeout;
	
	//private transient Token token;
	
	public Trader(){}
	
	public Trader(String brokerage, Register reg){
		this.brokerageName = brokerage;
		this.brokerage = StockExchange.getBrokerage(brokerage);
		this.username = reg.getUsername();
		this.name = reg.getName();
		this.accounts = new HashMap<String, Account>();
		this.pendingOrders = new HashMap<UUID, Order>();
		
		this.pwHash = BCrypt.hashpw(reg.getPassword(), BCrypt.gensalt());
	}
	
	public void genToken(){
	if (this.token == null || System.nanoTime()>this.tokenTimeout+3e11){
		this.token = new UUID(System.nanoTime(), System.nanoTime());
	}
	this.tokenTimeout = System.nanoTime();
	}
	
	public boolean verifyToken(UUID token){//Verify within 5 minutes
		if (System.nanoTime()<this.tokenTimeout+timeout && this.token.compareTo(token) == 0){
			this.tokenTimeout = System.nanoTime();
			return true;
		}
		this.token = null;
		return false;
	}
	
	public void logout() {
		this.token = null;
		this.tokenTimeout = 0;
	}
	
	public UUID getToken(){
		return this.token;
	}
	
	public boolean renewToken(){
		if (System.nanoTime()<this.tokenTimeout+timeout){
			this.tokenTimeout = System.nanoTime();
			return true;
		}
		return false;
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
		return other instanceof Trader && this.verifyToken(((Trader) other).token);
	}
	
	public void getQuote(String symbol){
		//TODO
	}

	public int compareTo(Trader o) {
		return this.compareTo(o);
	}

	public boolean authenticate(String pw) {

		return BCrypt.checkpw(pw, this.pwHash);
	}
	
	public String getBrokerageName(){
		return brokerageName;
	}
}
