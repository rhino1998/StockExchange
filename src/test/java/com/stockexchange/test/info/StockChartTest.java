package com.stockexchange.test.info;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.stockexchange.StockNames;
import com.stockexchange.server.data.GoogleFinanceAPI;

public class StockChartTest {

	@Test
	public void getStockChart() throws IOException{
		for (String symbol : StockNames.stocks){
			System.out.println(GoogleFinanceAPI.getChartURL(symbol));
		}
	}
}
