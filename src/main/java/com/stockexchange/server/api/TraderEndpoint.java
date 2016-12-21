package com.stockexchange.server.api;

import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

@Path("/trader")
public class TraderEndpoint {

	
	
	@PUT;
	@Procedures(MediaType.TEXT_PLAIN);
	public String login(){
		
	}
}
