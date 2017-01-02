package com.stockexchange.client.api;

import com.stockexchange.stocks.orders.Order;
import com.stockexchange.traders.Trader;

public class TraderAPI {

	private static Trader trader;
	
	public static void setTrader(Trader trader){
		TraderAPI.trader = trader;
	}
	
	public static void getAccounts(){
		
	}
	
	public static void submitOrder(Order order){
		//TODO write api handling
	}
}
