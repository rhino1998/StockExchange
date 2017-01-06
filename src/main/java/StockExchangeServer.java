import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.ConsoleHandler;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.stockexchange.StockNames;
import com.stockexchange.server.StockMarket;
import com.stockexchange.server.data.YahooFinanceAPI;
import com.stockexchange.stocks.quotes.Quote;

public class StockExchangeServer {

    public static final long frequency = 5000;
    public static final String BASE_URI = "http://localhost:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * 
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.underdog.jersey.grizzly package
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.stockexchange.server.api");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI),
                rc);
    }

    public static void main(String[] args) throws IOException {
        Logger l = Logger
                .getLogger("org.glassfish.grizzly.http.server.HttpHandler");
        l.setLevel(Level.FINE);
        l.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        l.addHandler(ch);
        try {
            StockMarket.listStocks(StockNames.stocks);
        } catch (Exception e) {
            System.out.println("MAJOR FAILURE");
            System.out.println(e);
            e.printStackTrace();
            System.exit(2);
        }
        System.out.println(StockMarket.getStockExchange("NMS"));
        StockMarket.addBrokerage("rhino");
        final HttpServer server = startServer();
        System.out.println(String.format(
                "Jersey app started with WADL available at "
                        + "%sapplication.wadl\nHit enter to stop it...",
                BASE_URI));
        System.in.read();
        server.shutdownNow();
        System.exit(0);

    }
}
