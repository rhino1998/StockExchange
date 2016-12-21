package com.stockexchange.traders;

import java.util.HashMap;
import java.util.UUID;

import org.glassfish.jersey.message.internal.Token;

import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.OrderView;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.enums.State;

public class Trader {

	
	private final String username;
	private String name;
	private HashMap<UUID,Account> accounts;
	private HashMap<UUID, Order> pendingOrders;
	
	
	private transient State state;
	private transient Token token;
	
	
	
	public String getUsername(){
		//TODO write this;
	}
	
	public String getName(){
		//TODO write this;
	}
	
	public OrderView getPendingOrderView();
	public void submitOrder(Order order);
}
