package com.stockexchange.client;

import com.airhacks.afterburner.injection.Injector;

import com.stockexchange.client.connection.Connection;
import com.stockexchange.client.ui.TimerViewModel;
import com.stockexchange.client.ui.details.DetailsView;
import com.stockexchange.client.ui.login.LoginView;
import com.stockexchange.client.ui.portfolio.PortfolioView;
import com.stockexchange.client.ui.quotes.QuotesView;
import com.stockexchange.client.ui.register.RegisterView;
import com.stockexchange.client.ui.welcome.WelcomeView;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.traders.Trader;

import java.time.LocalDate;
import java.time.Month;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javafx.application.Application;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;

import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class ClientApp extends Application {
    private static final double CENTER_ON_SCREEN_X_FRACTION = 1.0f / 2;
    private static final double CENTER_ON_SCREEN_Y_FRACTION = 1.0f / 2.5;
    public static final String CSS =
        ClientApp.class.getClassLoader().getResource("styles/style.css")
        .toExternalForm();
    private final Map<Object, Object> customProperties =
        new HashMap<Object, Object>();
    private Stage stage;
    private TimerViewModel timerViewModel = new TimerViewModel();

    /**
     * DOCUMENT ME!
     *
     * @param s DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    @Override
    public void start(Stage s) throws Exception {
        this.stage = s;

        Client client = ClientBuilder.newClient();

        Connection.website = client.target("http://10.144.128.125:8080/");

        /*
         * Properties of any type can be easily injected.
         */
        LocalDate date = LocalDate.now();
        customProperties.put("date", date);
        customProperties.put("stage", stage);
        customProperties.put("app", this);
        customProperties.put("quote", null);
        customProperties.put("trader", null);
        customProperties.put("timerViewModel", timerViewModel);
        /*
         * any function which accepts an Object as key and returns
         * and return an Object as result can be used as source.
         */
        Injector.setConfigurationSource(new Function<Object, Object>() {
            @Override
            public Object apply(Object t) {
                return customProperties.get(t);
            }
        });

        System.setProperty("happyEnding", " Enjoy the flight!");

        stage.setTitle("SafeTrade");
        gotoWelcome();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Rectangle2D primScreenBounds = Screen.getPrimary().getBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) * CENTER_ON_SCREEN_X_FRACTION);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) * CENTER_ON_SCREEN_Y_FRACTION);
    }

    /**
     * DOCUMENT ME!
     *
     * @throws Exception DOCUMENT ME!
     */
    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }

    /**
     * DOCUMENT ME!
     */
    public void gotoWelcome() {
        timerViewModel.stopTimer();


        double oldWidth = stage.getWidth();
        double oldHeight = stage.getHeight();

        WelcomeView w = new WelcomeView();
        Scene scene =
            new Scene(w.getView(), WelcomeView.WIDTH, WelcomeView.HEIGHT);
        scene.getStylesheets()
        .add(CSS);
        stage.setScene(scene);

        stage.setX(stage.getX() - ((stage.getWidth() - oldWidth) / 2));
        stage.setY(stage.getY() - ((stage.getHeight() - oldHeight) / 2));
    }

    /**
     * DOCUMENT ME!
     */
    public void gotoLogin() {
        timerViewModel.stopTimer();

        double oldWidth = stage.getWidth();
        double oldHeight = stage.getHeight();

        LoginView view = new LoginView();
        Scene scene =
            new Scene(view.getView(), LoginView.WIDTH, LoginView.HEIGHT);
        scene.getStylesheets()
        .add(CSS);
        stage.setScene(scene);

        stage.setX(stage.getX() - ((stage.getWidth() - oldWidth) / 2));
        stage.setY(stage.getY() - ((stage.getHeight() - oldHeight) / 2));
    }

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * DOCUMENT ME!
     *
     * @param trader DOCUMENT ME!
     */
    public void login(Trader trader) {
        customProperties.put("trader", trader);
    }

    public void logout(Trader trader) {
        customProperties.put("trader", null);
    }

    /**
     * DOCUMENT ME!
     */
    public void gotoRegister() {
        timerViewModel.stopTimer();

        double oldWidth = stage.getWidth();
        double oldHeight = stage.getHeight();

        RegisterView view = new RegisterView();
        Scene scene =
            new Scene(view.getView(), RegisterView.WIDTH, RegisterView.HEIGHT);
        scene.getStylesheets()
        .add(CSS);
        stage.setScene(scene);

        stage.setX(stage.getX() - ((stage.getWidth() - oldWidth) / 2));
        stage.setY(stage.getY() - ((stage.getHeight() - oldHeight) / 2));
    }

    /**
     * DOCUMENT ME!
     */
    public void gotoQuotes() {
        timerViewModel.stopTimer();

        double oldWidth = stage.getWidth();
        double oldHeight = stage.getHeight();

        QuotesView view = new QuotesView();
        Scene scene =
            new Scene(view.getView(), QuotesView.WIDTH, QuotesView.HEIGHT);
        scene.getStylesheets()
        .add(CSS);
        stage.setScene(scene);

        stage.setX(stage.getX() - ((stage.getWidth() - oldWidth) / 2));
        stage.setY(stage.getY() - ((stage.getHeight() - oldHeight) / 2));
    }

    /**
     * DOCUMENT ME!
     *
     * @param quote DOCUMENT ME!
     */
    public void gotoStockDetails(Quote quote) {
        timerViewModel.stopTimer();

        double oldWidth = stage.getWidth();
        double oldHeight = stage.getHeight();

        customProperties.put("quote", quote);

        DetailsView view = new DetailsView();
        Scene scene =
            new Scene(view.getView(), DetailsView.WIDTH, DetailsView.HEIGHT);
        scene.getStylesheets()
        .add(CSS);
        stage.setScene(scene);

        stage.setX(stage.getX() - ((stage.getWidth() - oldWidth) / 2));
        stage.setY(stage.getY() - ((stage.getHeight() - oldHeight) / 2));
    }

    /**
     * DOCUMENT ME!
     */
    public void gotoProfile() {
        timerViewModel.stopTimer();

        double oldWidth = stage.getWidth();
        double oldHeight = stage.getHeight();

        PortfolioView view = new PortfolioView();
        Scene scene =
            new Scene(view.getView(), PortfolioView.WIDTH,
                      PortfolioView.HEIGHT);
        scene.getStylesheets()
        .add(CSS);
        stage.setScene(scene);

        stage.setX(stage.getX() - ((stage.getWidth() - oldWidth) / 2));
        stage.setY(stage.getY() - ((stage.getHeight() - oldHeight) / 2));
    }
}
