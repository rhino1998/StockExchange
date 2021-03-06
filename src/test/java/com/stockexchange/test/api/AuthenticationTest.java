package com.stockexchange.test.api;

import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.connection.Connection;
import com.stockexchange.server.StockMarket;
import com.stockexchange.server.api.AuthenticationEndpoint;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTestNg;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

import javax.ws.rs.core.Application;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class AuthenticationTest extends JerseyTestNg.ContainerPerClassTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(AuthenticationEndpoint.class);
    }

    /**
     * DOCUMENT ME!
     */
    @Test(priority = 1)
    public void testRegister() {
        Connection.website = target("/");
        StockMarket.addBrokerage("rhino");

        Register reg = new Register("Test", "admin", "admin");
        Trader trader = AuthenticationAPI.registerTrader("rhino", reg);
        assertEquals(reg.getUsername(), trader.getUsername());
    }

    /**
     * DOCUMENT ME!
     */
    @Test(priority = 2)
    public void testAuthenticate() {
        Connection.website = target("/");
        StockMarket.addBrokerage("rhino");

        Register reg = new Register("Test", "admin", "admin");
        Credentials cred = new Credentials("admin", "admin");
        Trader a = AuthenticationAPI.registerTrader("rhino", reg);
        Trader b = AuthenticationAPI.authenticateTrader("rhino", cred);
        assertEquals(a.getToken(), b.getToken());
    }

    /**
     * DOCUMENT ME!
     */
    @Test(priority = 1)
    public void testRefresh() {
        Connection.website = target("/");
        StockMarket.addBrokerage("rhino");

        Register reg = new Register("Test", "admin", "admin");
        Credentials cred = new Credentials("admin", "admin");
        Trader a = AuthenticationAPI.registerTrader("rhino", reg);
        Trader b = AuthenticationAPI.authenticateTrader("rhino", cred);
        Trader c = AuthenticationAPI.refresh(b);

        assertEquals(a.getToken(), b.getToken());
        assertEquals(a.getToken(), c.getToken());
    }

    /**
     * DOCUMENT ME!
     */
    @Test(priority = 1)
    public void testLogout() {
        Connection.website = target("/");
        StockMarket.addBrokerage("rhino");

        Register reg = new Register("Test", "admin", "admin");
        Credentials cred = new Credentials("admin", "admin");
        Trader a = AuthenticationAPI.registerTrader("rhino", reg);
        Trader b = AuthenticationAPI.authenticateTrader("rhino", cred);
        Trader c = AuthenticationAPI.refresh(b);
        AuthenticationAPI.logoutTrader(c);

        Trader d = AuthenticationAPI.refresh(c);

        assertEquals(a.getToken(), b.getToken());
        assertEquals(a.getToken(), c.getToken());
        assertEquals(null, d);
    }

    /**
     * DOCUMENT ME!
     */
    @Test(priority = 1)
    public void testLogInOutInOut() {
        Connection.website = target("/");
        StockMarket.addBrokerage("rhino");

        Register reg = new Register("Test", "admin", "admin");
        Credentials cred = new Credentials("admin", "admin");
        Trader a = AuthenticationAPI.registerTrader("rhino", reg);
        Trader b = AuthenticationAPI.authenticateTrader("rhino", cred);
        Trader c = AuthenticationAPI.refresh(b);
        AuthenticationAPI.logoutTrader(c);

        Trader d = AuthenticationAPI.refresh(c);

        assertEquals(a.getToken(), b.getToken());
        assertEquals(a.getToken(), c.getToken());
        assertEquals(null, d);

        Trader e = AuthenticationAPI.authenticateTrader("rhino", cred);
        Trader f = AuthenticationAPI.refresh(e);
        assertEquals(e.getToken(), f.getToken());

        AuthenticationAPI.logoutTrader(f);

        Trader g = AuthenticationAPI.refresh(c);
        assertEquals(null, g);
    }
}
