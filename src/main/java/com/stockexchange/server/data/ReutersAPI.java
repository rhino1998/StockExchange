package com.stockexchange.server.data;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.stockexchange.StockNames;

public class ReutersAPI {

    public static String getDescription(String symbol) {
        try {
            File input = File.createTempFile("tmp", symbol);
            // System.out.println(symbol);
            Document doc = Jsoup
                    .connect(
                            String.format(
                                    "http://www.reuters.com/finance/stocks/lookup?search=%s&searchType=any&sortBy=&dateRange=&comSortBy=marketcap",
                                    symbol)).userAgent("Mozilla").get();

            for (Element elem : doc.select(String.format(
                    ".search-result-content a[href*=%s.]", symbol))) {
                String url = String.format("http://www.reuters.com%s", elem
                        .attr("href").replaceAll("overview", "companyProfile"));
                doc = Jsoup.connect(url).userAgent("Mozilla").get();
                String description = doc.select(
                        "div#companyNews div.moduleBody p").text();
                if (!description.equals("")) {
                    return description;
                }
            }
        } catch (Exception e) {
        }

        return "Description not available";
    }
}
