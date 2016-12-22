package com.stockexchange.traders;

import java.io.Serializable;

public class Credentials implements Serializable{

	private final String username;
	private final Password password;
	
	public Credentials(String username, String pw){
		this.password = new Password(pw);
		this.username = username;
		
	}
	
	public String getUsername() {
		return username;
	}
	
	public Password getPassword() {
		return password;
	}

	noContent().entity(Entity.json(trader)).build()
}
