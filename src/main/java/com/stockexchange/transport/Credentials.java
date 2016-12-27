package com.stockexchange.transport;

import java.io.Serializable;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credentials implements Serializable{

	/**
	 * 
	 */
	//@JsonProperty private static final long serialVersionUID = -7752851460576934357L;
	@JsonProperty private String username;
	@JsonProperty private String password;
	
	public Credentials(){
		
	}
	
	public Credentials(String username, String pw){
		this.password = pw;
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}
