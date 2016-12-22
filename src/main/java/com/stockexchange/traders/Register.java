package com.stockexchange.traders;

import java.io.Serializable;

public class Register extends Credentials implements Serializable{

	private final String name;
	
	public Register(String name, String username, String pw){
		super(username, pw);
		this.name=name;
		
	}
	
	public String getName() {
		return name;
	}
}
