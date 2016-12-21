package com.stockexchange.stocks.comparators;

import java.util.Comparator;

public class StockSymbolComparator<Stock> implements Comparator<Stock>{

	public int compare(Stock a, Stock b) {
		return(a.getSymbol().compareTo(b.getSymbol()));
	}

}
