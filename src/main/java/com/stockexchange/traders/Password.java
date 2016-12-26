package com.stockexchange.traders;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class Password implements Serializable{
	
	@JsonProperty private String hash;
	
	public Password(String pw){
		hash = hash(pw);//TODO do actual hashing
	}


	
	public boolean equals(String pw){
		return this.hash.equals(hash(pw));
	}
	
	public static String hash(String pw){
		return pw;
	}
}
