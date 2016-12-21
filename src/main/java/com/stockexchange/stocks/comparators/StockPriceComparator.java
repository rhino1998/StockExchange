package com.stockexchange.stocks.comparators;

import java.util.Comparator;

public class StockPriceComparator<Stock> implements Comparator<Stock>{

	public int compare(Stock a, Stock b) {
		if(a.getPrice() > b.getPrice())
			return 1;
		if(a.getPrice() < b.getPrice())
			return -1;
		else
			return 0;
	}

}
