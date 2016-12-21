package com.stockexchange.client.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.server.traders.Credentials;
import com.stockexchange.traders.Trader;

public class AuthenticationAPI {

	
	public static Trader authenticateTrader(String username, String password){
		WebTarget target = Connection.website.path("/auth");
		Response response = target.request().put(Entity.json(new Credentials(username, password)));
		return response.readEntity(Trader.class);
	}
	
	
	public static Trader registerTrader(Brokerage brokerage, String username, String name, String password){
		WebTarget target = Connection.website.path("/auth");
		Response response = target.request().put(Entity.json(new Trader(brokerage, username, password)));
		return response.readEntity(Trader.class);
	}
}
