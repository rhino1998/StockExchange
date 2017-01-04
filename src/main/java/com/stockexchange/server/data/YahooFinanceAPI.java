package com.stockexchange.server.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import com.opencsv.CSVReader;
import com.stockexchange.stocks.quotes.Quote;

public class YahooFinanceAPI {

    private static final String apiString = "http://finance.yahoo.com/d/quotes.csv?s=";

    public static Quote getQuote(String symbol) throws IOException {
        URL stockURL = new URL(apiString + symbol + "&f=snabghvopj1x");

        BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));
        CSVReader reader = new CSVReader(in);
        return new Quote(reader.readNext());
    }

    public static Quote[] getQuotes(String... symbols) throws IOException {
        String syms = "";
        for (String symbol : symbols) {
            syms += symbol + "+";
        }
        syms = syms.substring(0, syms.length() - 1);
        URL stockURL = new URL(apiString + syms + "&f=snabghvopj1x");
        BufferedReader in = new BufferedReader(new InputStreamReader(stockURL.openStream()));
        CSVReader reader = new CSVReader(in);
        Quote[] quotes = new Quote[symbols.length];
        int total = 0;
        for (int i = 0; i < quotes.length; i++) {
            String[] strs = reader.readNext();
            for (int j = 0; j < strs.length; j++) {
                if (strs[j].equals("N/A")) {
                    strs[j] = "0";
                }
            }
            // System.out.println(strs[0]);
            if (!Arrays.asList(strs).contains("N/A")) {
                quotes[total] = new Quote(strs);
                total++;
            }
        }
        return Arrays.copyOf(quotes, total);
    }
}
