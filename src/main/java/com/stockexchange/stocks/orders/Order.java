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
		return orderType == OrderType.MARKET;
	}

	public boolean isBuy() {
		return transactionType == TransactionType.BUY;
	}
}
