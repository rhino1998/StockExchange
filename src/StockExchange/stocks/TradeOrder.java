package StockExchange.stocks;
import Trader;


public class TradeOrder {
	private double myPrice;
	private int myShares;
	private String mySymbol;
	private Trader myTrader;
	private boolean myBuyOrder;
	private boolean myMarketOrder;

	public TradeOrder(Trader trader, String symbol, boolean buyOrder, boolean marketOrder, int numShares, double price){
		myTrader = trader;
		mySymbol = symbol;
		myBuyOrder = buyOrder;
		myMarketOrder = marketOrder;
		myShares = numShares;
		myPrice = price;
	}
	

}
