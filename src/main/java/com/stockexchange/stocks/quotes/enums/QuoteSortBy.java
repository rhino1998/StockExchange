package com.stockexchange.stocks.quotes.enums;

import java.io.Serializable;
import java.util.Comparator;

import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.stocks.quotes.comparators.QuoteAskComparator;
import com.stockexchange.stocks.quotes.comparators.QuoteBidComparator;
import com.stockexchange.stocks.quotes.comparators.QuoteDailyHighComparator;
import com.stockexchange.stocks.quotes.comparators.QuoteDailyLowComparator;
import com.stockexchange.stocks.quotes.comparators.QuoteNameComparator;
import com.stockexchange.stocks.quotes.comparators.QuoteSymbolComparator;
import com.stockexchange.stocks.quotes.comparators.QuoteVolumeComparator;

public enum QuoteSortBy implements Serializable, Comparator<Quote>{
	ASK (new QuoteAskComparator()),
	BID (new QuoteBidComparator()),
	DAILYLOW (new QuoteDailyLowComparator()),
	DAILYHIGH (new QuoteDailyHighComparator()),
	NAME (new QuoteNameComparator()),
	SYMBOL (new QuoteSymbolComparator()),
	VOLUME (new QuoteVolumeComparator());
	
	private final Comparator<Quote> comp;
	QuoteSortBy(Comparator<Quote> comp) {
        this.comp = comp;
    }
	public int compare(Quote o1, Quote o2) {
		// TODO Auto-generated method stub
		return comp.compare(o1, o2);
	}
}
