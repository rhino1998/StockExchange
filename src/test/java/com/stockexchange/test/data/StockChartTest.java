package com.stockexchange.test.data;

import static org.testng.Assert.*;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import com.stockexchange.server.data.GoogleFinanceAPI;

public class StockChartTest {

    @Test(priority = 1)
    public void getStockChart() throws IOException {
        assertTrue(GoogleFinanceAPI.getChartURL("GOOG") != null);
    }
}
