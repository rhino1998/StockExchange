package com.stockexchange.client.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.server.brokerages.Brokerage;
import com.stockexchange.traders.Password;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

public class AuthenticationAPI {

	
	public static Trader authenticateTrader(String username, String password){
		WebTarget target = Connection.website.path("/auth");
		Entity<Credentials> entity = Entity.entity(new Credentials(username, password), MediaType.APPLICATION_JSON);
		Response response = target.request(MediaType.APPLICATION_JSON).post(entity);
		return response.readEntity(Trader.class);
	}
	
	
	public static Trader registerTrader(String brokerage,Register reg){
		WebTarget target = Connection.website.path(String.format("/auth/register/%s", brokerage));
		Entity<Register> entity = Entity.entity(reg, MediaType.APPLICATION_JSON);
		System.out.println(Entity.json(entity));

		Response response = target.request(MediaType.APPLICATION_JSON).post(entity);
		//System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		return response.readEntity(Trader.class);
	}
}
