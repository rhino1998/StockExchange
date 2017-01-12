package com.stockexchange.client.ui.components.tables;

import com.stockexchange.client.ClientApp;
import com.stockexchange.client.api.StockExchangeAPI;
import com.stockexchange.client.data.QuoteFilterChangeListener;
import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.components.table.ButtonColumn;
import com.stockexchange.client.ui.components.table.buttons.QuoteDetailsButton;
import com.stockexchange.client.ui.components.table.buttons.factory.ColumnButtonFactory;
import com.stockexchange.stocks.quotes.Quote;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Inject;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class StockQuotesController implements Initializable {
    private final ObservableList<QuoteModel> data =
        FXCollections.observableArrayList();
    @Inject
    private ClientApp app;
    @FXML
    private TextField filter;
    @FXML
    private TableView<QuoteModel> table;
    @FXML
    private TableColumn<QuoteModel, String> symbolColumn;
    @FXML
    private TableColumn<QuoteModel, String> nameColumn;
    @FXML
    private TableColumn<QuoteModel, Double> askColumn;
    @FXML
    private TableColumn<QuoteModel, Double> bidColumn;
    @FXML
    private TableColumn<QuoteModel, Double> lowColumn;
    @FXML
    private TableColumn<QuoteModel, Double> highColumn;
    @FXML
    private TableColumn<QuoteModel, Double> openColumn;
    @FXML
    private TableColumn<QuoteModel, Double> closeColumn;
    @FXML
    private TableColumn<QuoteModel, Double> volumeColumn;
    @FXML
    private TableColumn<QuoteModel, Double> marketCapColumn;
    private TableColumn<QuoteModel, String> detailsColumn;

    /**
     * DOCUMENT ME!
     *
     * @param fxmlLocation DOCUMENT ME!
     * @param rb DOCUMENT ME!
     */
    @Override
    public void initialize(URL fxmlLocation, ResourceBundle rb) {
        symbolColumn.setCellValueFactory(new PropertyValueFactory<QuoteModel, String>(
                                             "symbol"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<QuoteModel, String>(
                                           "name"));

        askColumn.setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                                          "ask"));
        bidColumn.setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                                          "bid"));

        lowColumn.setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                                          "low"));
        highColumn.setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                                           "high"));

        openColumn.setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                                           "open"));
        closeColumn.setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                                            "close"));

        volumeColumn.setCellValueFactory(new PropertyValueFactory<QuoteModel, Double>(
                                             "volume"));
        marketCapColumn.setCellValueFactory(new
                                            PropertyValueFactory<QuoteModel, Double>(
                                                "marketCap"));

        detailsColumn = new ButtonColumn<QuoteModel, String>(app,
                new ColumnButtonFactory<QuoteModel>(QuoteDetailsButton.class),
                "DETAILS");
        table.getColumns().add(detailsColumn);
        table.setEditable(false);

        FilteredList<QuoteModel> filterData =
            new FilteredList<QuoteModel>(data);

        // Enable searching
        filter.textProperty()
        .addListener(new QuoteFilterChangeListener(filterData));



        // Enable sorting
        SortedList<QuoteModel> sorted =
            new SortedList<QuoteModel>(filterData);
        sorted.comparatorProperty().bind(table.comparatorProperty());

        // Populate table
        table.setItems(sorted);
    }

    /**
     * DOCUMENT ME!
     *
     * @param quotes DOCUMENT ME!
     */
    public void update(List<Quote> quotes) {
        this.data.clear();
        // Wrap quotes as queryable objects in a list
        this.data.addAll(QuoteModel.marshallQuotes(quotes));
    }
}
