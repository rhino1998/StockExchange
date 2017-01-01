package com.stockexchange.server.orders;

import com.stockexchange.client.orders.RemoteOrder;
import com.stockexchange.server.StockExchange;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.orders.Order;
import com.stockexchange.traders.Trader;

public class ExecutableOrder extends Order{

	private Trader trader;
	private Stock stock;
	
	public ExecutableOrder(RemoteOrder order){
		this.trader = StockExchange.getBrokerage(order.getBrokerageName())
				.getTrader(order.getTraderUsername());
		this.stock = StockExchange.getStockExchange(order.getExchangeName())
				.getStock(order.getSymbol());
		this.shares = order.getShares();
	}
	
	public void subtractShares(int shares){
		this.shares -= shares;
	}
	
	
	
	
}
