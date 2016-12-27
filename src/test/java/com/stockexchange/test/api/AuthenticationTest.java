package com.stockexchange.test.api;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.stockexchange.client.api.AuthenticationAPI;
import com.stockexchange.client.connection.Connection;
import com.stockexchange.server.StockExchangeRegistry;
import com.stockexchange.server.api.AuthenticationEndpoint;
import com.stockexchange.traders.Trader;
import com.stockexchange.transport.Credentials;
import com.stockexchange.transport.Register;

public class AuthenticationTest extends JerseyTest{

	
	@Override
	protected Application configure(){
		return new ResourceConfig(AuthenticationEndpoint.class);
	}
	
	@Test
	public void testRegister(){
		Connection.website = target("/");
		StockExchangeRegistry.addBrokerage("rhino");
		Register reg = new Register("Test", "admin", "admin");
		Trader trader = AuthenticationAPI.registerTrader("rhino", reg);
		assertEquals(reg.getUsername(), trader.getUsername());
	}
	
	@Test
	public void testAuthenticate(){
		Connection.website = target("/");
		StockExchangeRegistry.addBrokerage("rhino");
		Register reg = new Register("Test", "admin", "admin");
		Credentials cred = new Credentials("admin", "admin");
		Trader a = AuthenticationAPI.registerTrader("rhino", reg);
		Trader b = AuthenticationAPI.authenticateTrader("rhino", cred);
		assertEquals(a.getToken(), b.getToken());
	}
	
	@Test
	public void testRefresh(){
		Connection.website = target("/");
		StockExchangeRegistry.addBrokerage("rhino");
		Register reg = new Register("Test", "admin", "admin");
		Credentials cred = new Credentials("admin", "admin");
		Trader a = AuthenticationAPI.registerTrader("rhino", reg);
		Trader b = AuthenticationAPI.authenticateTrader("rhino", cred);
		Trader c = AuthenticationAPI.refresh(b);
		
		
		assertEquals(a.getToken(), b.getToken());
		assertEquals(a.getToken(), c.getToken());
	}
	
	@Test
	public void testLogout(){
		Connection.website = target("/");
		StockExchangeRegistry.addBrokerage("rhino");
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
	
	@Test
	public void testLogInOutInOut(){
		Connection.website = target("/");
		StockExchangeRegistry.addBrokerage("rhino");
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
		Trader f = AuthenticationAPI.refresh(b);
		assertEquals(e.getToken(), f.getToken());
		
		
		AuthenticationAPI.logoutTrader(f);
		Trader g = AuthenticationAPI.refresh(c);
		assertEquals(null, g);
	}
	
}
