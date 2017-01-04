package com.stockexchange.test.data;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import com.stockexchange.server.data.GoogleFinanceAPI;

public class StockChartTest {

    @Test
    public void getStockChart() throws IOException {
        assertTrue(GoogleFinanceAPI.getChartURL("GOOG") != null);
    }
}
