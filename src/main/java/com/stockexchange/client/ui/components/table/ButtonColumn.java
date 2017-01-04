package com.stockexchange.client.ui.components.table;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import com.stockexchange.client.data.QuoteModel;
import com.stockexchange.client.ui.ViewStage;
import com.stockexchange.client.ui.components.table.buttons.ColumnButton;
import com.stockexchange.client.ui.components.table.buttons.factory.ColumnButtonFactory;
import com.stockexchange.stocks.quotes.Quote;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ButtonColumn<S, T> extends TableColumn<S, T> {

    private ViewStage window;

    public ButtonColumn(ViewStage win, final ColumnButtonFactory<S> factory,
            String text) {
        super(text);
        window = win;

        Callback<TableColumn<S, T>, TableCell<S, T>> cellFactory = new Callback<TableColumn<S, T>, TableCell<S, T>>() {
            public TableCell<S, T> call(TableColumn<S, T> param) {
                TableCell<S, T> cell;
                cell = new TableCell<S, T>() {
                    public void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            try {
                                ColumnButton<S> btn = factory.create(
                                        window,
                                        getTableView().getItems().get(
                                                getIndex()));
                                setGraphic(btn);
                                setText(null);
                            } catch (Exception e) {
                                setGraphic(null);
                                setText(null);
                                System.out.println("FAIL");
                                e.printStackTrace();
                                System.exit(1);
                                return;
                            }
                        }
                    }
                };
                cell.setPadding(new Insets(1, 0, 1, 5));
                return cell;
            }

        };
        this.setCellFactory(cellFactory);
    }
}
