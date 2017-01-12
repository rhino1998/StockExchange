package com.stockexchange.client.connection;

import com.stockexchange.traders.Trader;

import javax.ws.rs.client.WebTarget;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class Connection {
    public static WebTarget website;
    public static Trader trader;
    public static final long refreshRate = (long)5000;
}
