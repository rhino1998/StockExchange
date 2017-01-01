package com.stockexchange.test.info;

import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.stockexchange.StockNames;
import com.stockexchange.server.data.ReutersAPI;

public class StockDescriptionTest {
	
	
	@Test
	public void getDescriptionTest() throws IOException{
		for (String symbol : StockNames.stocks){
			String desc = ReutersAPI.getDescription(symbol);
			System.out.println(desc);
			assertNotEquals(desc, "");
		}
		
	}

}
