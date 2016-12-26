package com.stockexchange.transport;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockexchange.traders.Password;

public class Credentials implements Serializable{

	@JsonProperty private String username;
	@JsonProperty private Password password;
	
	public Credentials(){
		
	}
	
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
