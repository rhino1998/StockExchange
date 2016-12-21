package com.stockexchange.server.traders;

import java.io.Serializable;

import com.stockexchange.traders.Password;

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
}
