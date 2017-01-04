package com.stockexchange.client.ui.components.table;

import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.stocks.quotes.Quote;
import com.stockexchange.util.MoneyFormat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MoneyShortColumn extends TableColumn< QuoteModel, Double> {

    public MoneyShortColumn(String text) {
        super(text);

        Callback< TableColumn< QuoteModel, Double>, TableCell< QuoteModel, Double>> cellFactory = new Callback< TableColumn< QuoteModel, Double>, TableCell< QuoteModel, Double>>() {
            public TableCell< QuoteModel, Double> call(TableColumn< QuoteModel, Double> param) {
                final TableCell< QuoteModel, Double> cell = new TableCell< QuoteModel, Double>() {
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setGraphic(null);
                            setText(MoneyFormat.shortened(item));

                        }
                    }
                };

                return cell;
            }

        };
        this.setCellFactory(cellFactory);
    }
}
