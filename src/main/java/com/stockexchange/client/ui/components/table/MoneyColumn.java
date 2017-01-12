package com.stockexchange.client.ui.components.table;

import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.stocks.quotes.Quote;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import javafx.stage.Stage;

import javafx.util.Callback;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class MoneyColumn extends TableColumn<QuoteModel, Double> {
    /**
     * Creates a new MoneyColumn object.
     *
     * @param text DOCUMENT ME!
     */
    public MoneyColumn(String text) {
        super(text);

        Callback<TableColumn<QuoteModel, Double>, TableCell<QuoteModel, Double>>
        cellFactory =
        new Callback<TableColumn<QuoteModel, Double>, TableCell<QuoteModel, Double>>() {
            public TableCell<QuoteModel, Double> call(
                TableColumn<QuoteModel, Double> param) {
                final TableCell<QuoteModel, Double> cell =
                new TableCell<QuoteModel, Double>() {
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setGraphic(null);
                            setText(String.format("$%.2f", item));
                        }
                    }
                };

                return cell;
            }
        };

        this.setCellFactory(cellFactory);
    }
}
