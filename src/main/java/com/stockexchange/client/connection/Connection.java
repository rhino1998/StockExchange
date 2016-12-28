package com.stockexchange.client.connection;

import javax.ws.rs.client.WebTarget;

import com.stockexchange.traders.Trader;

public class Connection {

	
	public static WebTarget website; 
	
	public static Trader trader;

	public static final long refreshRate = (long) 5000;
}
