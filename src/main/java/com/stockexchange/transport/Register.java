package com.stockexchange.transport;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Register extends Credentials implements Serializable{

	@JsonProperty private String name;
	
	public Register(){}
	
	public Register(String name, String username, String pw){
		super(username, pw);
		this.name=name;
		
	}
	
	public String getName() {
		return name;
	}
	
}
