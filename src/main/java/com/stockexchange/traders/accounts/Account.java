package com.stockexchange.traders.accounts;

import java.util.HashMap;
import java.util.UUID;

import com.stockexchange.traders.Trader;

public class Account {
	
	
	private final String name;
	private final Trader owner;
	private double balance;
	private final HashMap<String, Long> portfolio  = new HashMap<String, Long>;
	
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
