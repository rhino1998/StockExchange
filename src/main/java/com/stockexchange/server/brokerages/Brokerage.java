package com.stockexchange.server.brokerages;

import java.util.HashMap;

import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

public class Brokerage {

	private final String name;
	private final HashMap<String, Trader> traders = new HashMap<String, Trader>();
	
	
	public Brokerage(String name){
		this.name = name;
	}
	
	public Trader authenticate(Credentials cred) {
		Trader trader = traders.get(cred.getUsername());
		if (trader != null && trader.authenticate(cred.getPassword())){
			return trader;
		}
		return null;
	}
	
	public Trader refresh(String username){
		Trader trader =  traders.get(username);
		if (trader.renewToken()){
			return trader;
		}
		return null;
	}
	
	public Trader registerTrader(Register reg){
		if (traders.containsKey(reg.getUsername())){
			return traders.get(reg.getUsername());
		}
		Trader trader = new Trader(this.name, reg);
		traders.put(reg.getUsername(), trader);
		return trader;
	}
}
