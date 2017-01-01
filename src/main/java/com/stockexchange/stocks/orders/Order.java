package com.stockexchange.stocks.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;

public class Order {
	@JsonProperty protected TransactionType transactionType;
	@JsonProperty protected OrderType orderType;
	
	@JsonProperty protected double shares;
	
	public Order(){}
	
	public double getShares(){
		return this.shares;
	}

	public boolean isMarket() {
		// TODO Auto-generated method stub
		return orderType == OrderType.MARKET;
	}
}
