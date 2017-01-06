package com.stockexchange.test.order;

import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import com.stockexchange.StockNames;
import com.stockexchange.client.orders.RemoteOrder;
import com.stockexchange.server.StockMarket;
import com.stockexchange.server.data.ReutersAPI;
import com.stockexchange.server.orders.ExecutableOrder;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.Register;

public class OrderTest {

    @Test(priority = 1)
    public void orderConvertTest() throws IOException {
        StockMarket.listStocks(StockNames.stocks);

        StockMarket.addBrokerage("rhino");
        Account acct = new Account("BASE");
        StockMarket.getBrokerage("rhino").addAccount(acct);

        Trader trader = StockMarket.getBrokerage("rhino").registerTrader(
                new Register("Riley Wilburn", "TestA", "pw"));
        trader.addAccount(acct);
        RemoteOrder r = new RemoteOrder(StockMarket.getStockExchange("Q")
                .getQuote("GOOG"), "BASE", trader, TransactionType.BUY, 100);

        ExecutableOrder e = new ExecutableOrder(r);

        assertEquals(r.getSymbol(), e.getStock().getSymbol());
        assertEquals(r.getQuantity(), e.getQuantity());

    }

    @Test(priority = 2)
    public void orderProcessTest() throws IOException {
        StockMarket.listStocks(StockNames.stocks);

        StockMarket.addBrokerage("rhino");
        Account acct = new Account("BASE");
        StockMarket.getBrokerage("rhino").addAccount(acct);

        Trader trader = StockMarket.getBrokerage("rhino").registerTrader(
                new Register("Riley Wilburn", "TestA", "pw"));
        trader.addAccount(acct);
        RemoteOrder rs = new RemoteOrder(StockMarket.getStockExchange("Q")
                .getQuote("GOOG"), "BASE", trader, TransactionType.SELL, 200);

        ExecutableOrder es = new ExecutableOrder(rs);

        RemoteOrder rb = new RemoteOrder(StockMarket.getStockExchange("Q")
                .getQuote("GOOG"), "BASE", trader, TransactionType.BUY, 110);

        ExecutableOrder eb = new ExecutableOrder(rb);
        ExecutableOrder eb1 = new ExecutableOrder(rb);

        eb.submit();
        eb1.submit();
        es.submit();

        Stock stock = eb.getStock();

        assertTrue(es.getQuantity() == 0);
        assertTrue(eb.getQuantity() == 0);
        assertTrue(eb1.getQuantity() != 0);
    }
}
