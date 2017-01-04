package com.stockexchange.client.data;

import java.util.function.Predicate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;

public class QuoteFilterChangeListener implements ChangeListener< String> {

    private FilteredList< QuoteModel> data;

    public QuoteFilterChangeListener(FilteredList< QuoteModel> data) {
        this.data = data;
    }

    public void changed(ObservableValue< ? extends String> observable, final String oldValue, final String newValue) {
        data.setPredicate(new Predicate< QuoteModel>() {

            public boolean test(QuoteModel quote) {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lower = newValue.toLowerCase();

                if (quote.getSymbol().toLowerCase().contains(lower)) {
                    return true;
                }
                if (quote.getName().toLowerCase().contains(lower)) {
                    return true;
                }
                return false;
            }

        });

    }
}
