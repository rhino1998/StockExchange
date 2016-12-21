package com.stockexchange.server.traders;

import java.io.Serializable;

public class Credentials implements Serializable{

	private final String username;
	private final Password password;
	
	public String getUsername() {
		return username;
	}
	
	public Password getPassword() {
		return password;
	}
}
