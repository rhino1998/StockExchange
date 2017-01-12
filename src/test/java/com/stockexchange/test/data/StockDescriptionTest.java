package com.stockexchange.test.data;

import com.stockexchange.StockNames;
import com.stockexchange.server.data.ReutersAPI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class StockDescriptionTest {
    /**
     * DOCUMENT ME!
     *
     * @throws IOException DOCUMENT ME!
     */
    @Test(priority = 1)
    public void getDescriptionTest() throws IOException {
        int i = 0;

        for (String symbol : StockNames.stocks) {
            String desc = ReutersAPI.getDescription(symbol);
            // System.out.println(desc);
            assertNotEquals(desc, "");
            i++;

            if (i > 10) {
                break;
            }
        }
    }
}
