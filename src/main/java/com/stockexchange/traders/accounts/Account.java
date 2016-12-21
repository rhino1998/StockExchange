package com.stockexchange.traders.accounts;

import java.util.UUID;

import com.stockexchange.traders.Trader;

public class Account {
	
	
	private final String name;
	private final Trader owner;
	private int balance;
	
	
	public Account(String name, Trader owner){
		this.name = name;
		this.owner = owner;
	}

	public Account(String name, Trader owner, int money){
		this.name = name;
		this.owner = owner;
		this.balance = money;
	}
	
	
	public int deposit(int money){
		balance += money;
		return balance;
	}
	
	public int withdraw(int money){
		balance -= money;
		return balance;
	}

}
