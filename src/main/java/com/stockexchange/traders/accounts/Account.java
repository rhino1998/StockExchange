package com.stockexchange.traders.accounts;

import java.util.HashMap;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockexchange.traders.Trader;

public class Account {
	
	
	@JsonProperty private final String name;
	@JsonProperty private final Trader owner;
	@JsonProperty private double balance;
	@JsonProperty private final HashMap<String, Long> portfolio  = new HashMap<String, Long>();
	
	public Account(String name, Trader owner){
		this.name = name;
		this.owner = owner;
	}

	public Account(String name, Trader owner, double money){
		this.name = name;
		this.owner = owner;
		this.balance = money;
	}
	
	
	public double deposit(double money){
		balance += money;
		return balance;
	}
	
	public double withdraw(double money){
		balance -= money;
		return balance;
	}
}
