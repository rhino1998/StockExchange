package com.stockexchange.traders;

import java.io.Serializable;

public class Password implements Serializable{

	private final String pwHash;
	
	public Password(String pw){
		pwHash = hash(pw);//TODO do actual hashing
	}
	
	public String getHash(){
		return this.pwHash;
	}
	
	public boolean equals(String pw){
		return this.pwHash.equals(hash(pw));
	}
	
	public boolean equals(Password pw){
		return this.pwHash.equals(pw.getHash());
	}
	
	public static String hash(String pw){
		return pw;
	}
}
