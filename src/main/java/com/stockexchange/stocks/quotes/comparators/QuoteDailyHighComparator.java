package com.stockexchange.stocks.quotes.comparators;

import java.util.Comparator;

import com.stockexchange.stocks.quotes.Quote;

public class QuoteDailyHighComparator implements Comparator<Quote>{

	public int compare(Quote o1, Quote o2) {
		return Double.compare(o1.getDailyHigh(), o2.getDailyHigh());
	}
	
}
