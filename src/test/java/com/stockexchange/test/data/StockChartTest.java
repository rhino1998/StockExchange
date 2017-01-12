package com.stockexchange.test.data;

import com.stockexchange.server.data.GoogleFinanceAPI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class StockChartTest {
    /**
     * DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     */
    @Test(priority = 1)
    public void getStockChart() throws IOException {
        assertTrue(GoogleFinanceAPI.getChartURL("GOOG") != null);
    }
}
