package com.stockexchange.test.data;

import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import com.stockexchange.StockNames;
import com.stockexchange.server.data.ReutersAPI;
import com.stockexchange.server.data.TradierAPI;
import com.stockexchange.stocks.quotes.Quote;

public class TradierTest {

    @Test(priority = 1)
    public void getQuoteTest() throws IOException {
        List<Quote> quotes = TradierAPI.getQuotes("SPY", "AAPL");
        System.out.println(quotes);
    }

}
