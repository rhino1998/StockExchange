package com.stockexchange.test.api;

import static org.testng.Assert.*;

import java.io.IOException;

import javax.ws.rs.core.Application;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTestNg;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.filter.LoggingFilter;
import org.testng.annotations.Test;

import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.api.TraderAPI;
import com.stockexchange.client.connection.Connection;
import com.stockexchange.server.StockMarket;
import com.stockexchange.StockNames;
import com.stockexchange.server.api.AuthenticationEndpoint;
import com.stockexchange.server.api.TraderEndpoint;
import com.stockexchange.client.orders.RemoteOrder;
import com.stockexchange.server.data.ReutersAPI;
import com.stockexchange.server.orders.ExecutableOrder;
import com.stockexchange.stocks.Stock;
import com.stockexchange.stocks.orders.enums.OrderType;
import com.stockexchange.stocks.orders.enums.TransactionType;
import com.stockexchange.traders.Trader;
import com.stockexchange.traders.accounts.Account;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

public class TraderTest extends JerseyTestNg.ContainerPerClassTest {

    @Override
    protected Application configure() {
        ResourceConfig conf = new ResourceConfig(AuthenticationEndpoint.class,
                TraderEndpoint.class);
        return conf;
    }

    @Test(priority = 1)
    public void testSubmitOrder() throws IOException {
        StockMarket.listStocks(StockNames.stocks);
        Connection.website = target("/");
        StockMarket.addBrokerage("rhino");

        Register reg = new Register("Test", "admin", "admin");
        Trader trader = AuthenticationAPI.registerTrader("rhino", reg);

        System.out.println(trader.getAccounts());

        assertEquals(reg.getUsername(), trader.getUsername());

        RemoteOrder r = new RemoteOrder(StockMarket.getStockExchange("NMS")
                .getQuote("GOOG"), trader.getAccounts().get(0), trader,
                TransactionType.BUY, 100);

        boolean success = TraderAPI.submitOrder(trader, r);

        assertTrue(success);

    }

}
