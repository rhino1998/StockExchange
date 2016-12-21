package com.stockexchange.server.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.stockexchange.server.traders.Credentials;
import com.stockexchange.traders.Trader;

@Path("/auth")
public class AuthenticationEndpoint {

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response authenticateTrader(Credentials cred){
		//TODO write user authentication logic
		return null;
		
	}
	
}
 