package com.stockexchange.stocks.comparators;

import java.util.Comparator;

public class StockNameComparator<Stock> implements Comparator<Stock>{

	public int compare(Stock a, Stock b) {
		return(a.getName().compareTo(b.getName()));
	}

}
