package com.stockexchange.server.brokerages;

import java.util.HashMap;

import com.stockexchange.traders.Credentials;
import com.stockexchange.traders.Register;
import com.stockexchange.traders.Trader;

public class Brokerage {

	
	private final HashMap<String, Trader> traders = new HashMap<String, Trader>();
	
	
	public Trader authenticate(Credentials cred) {
		Trader trader = traders.get(cred.getUsername());
		if (trader != null && trader.getPassword().equals(cred.getPassword())){
			return trader;
		}
		return null;
	}
	
	public void registerTrader(Register reg){
		if (traders.containsKey(reg.getUsername())){
			return;
		}
		traders.put(reg.getUsername(), new Trader(reg));
	}
}
