package com.stockexchange.server.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class GoogleFinanceAPI {
    /**
     * DOCUMENT ME!
     *
     * @param symbol DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static String getChartURL(String symbol) {
        Document doc;

        try {
            doc = Jsoup.connect("https://www.google.com/finance?q=goog")
                  .userAgent("Mozilla").get();
        } catch (IOException e) {
            return "";
        }

        return String.format("https://www.google.com%s",
                             doc.select("[alt=Chart.]").first().attr("src"));
    }
}
