package com.stockexchange.server.brokerages;

import java.util.HashMap;

import com.stockexchange.brokerages.Brokerage;
import com.stockexchange.traders.Trader;

public class BrokerageRegistry {

	
	private static final HashMap<String, Brokerage> brokerages = new HashMap<String, Brokerage>();
	
	
	public static void add(Brokerage brokerage){
		brokerages.put(brokerage.getName(), brokerage);
	};
	
	public static Brokerage get(String name){
		return brokerages.get(name);
	};
}
