package com.stockexchange.server.data;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GoogleFinanceAPI {

	
	public static String getChartURL(String symbol){
		Document doc;
		try {
			doc = Jsoup
			.connect("https://www.google.com/finance?q=goog")
			.userAgent("Mozilla")
			.get();
		} catch (IOException e) {
			return "";
		}
		return String.format(
			"https://www.google.com%s",
			doc.select("[alt=Chart.]").first().attr("src")
		);
	}
}
