package com.stockexchange.server.traders;

import java.util.HashMap;

import com.stockexchange.traders.Trader;

public class TraderRegistry {

	private static final HashMap<String, Trader> traders = new HashMap<String, Trader>();
	
	
	public static void addTrader(Trader trader){
		traders.put(trader.getUsername(), trader)
	};
	
	public static Trader authenticate(Credentials cred) {
		Trader trader = traders.get(cred.getUsername());
		if (trader != null && trader.getPassword()){
			
		}
		return null;
	}
}
