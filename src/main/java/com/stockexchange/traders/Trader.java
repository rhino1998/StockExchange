package com.stockexchange.traders;

import java.util.HashMap;
import java.util.UUID;

import com.stockexchange.stocks.orders.Order;
import com.stockexchange.stocks.orders.OrderView;
import com.stockexchange.traders.accounts.Account;

public class Trader {

	
	private String uuid;
	private String name;
	private HashMap<UUID,Account> accounts;
	private HashMap<UUID, Order> pendingOrders;
	
	
	
	public OrderView getPendingOrderView();
	public void addOrder(Order order);
}
