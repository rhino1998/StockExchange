package com.stockexchange.server.api;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

@Path("/trader")
public class TraderEndpoint {

	
	
	
	//TODO Login (username, pword hash -> uuid)
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	public String login(){	
	}
	
	
	@Path("{uuid}")
	
	
	
	
}
