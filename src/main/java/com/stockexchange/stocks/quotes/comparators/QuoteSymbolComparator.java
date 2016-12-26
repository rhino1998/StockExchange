package com.stockexchange.stocks.quotes.comparators;

import java.util.Comparator;

import com.stockexchange.stocks.quotes.Quote;

public class QuoteSymbolComparator implements Comparator<Quote>{

	public int compare(Quote o1, Quote o2) {
		return o1.getSymbol().compareTo(o2.getSymbol());
	}
	
}
