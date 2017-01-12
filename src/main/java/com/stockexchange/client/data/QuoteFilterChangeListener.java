package com.stockexchange.client.data;

import java.util.function.Predicate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.transformation.FilteredList;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  */
public class QuoteFilterChangeListener implements ChangeListener<String> {
    private FilteredList<QuoteModel> data;

    /**
     * Creates a new QuoteFilterChangeListener object.
     *
     * @param data DOCUMENT ME!
     */
    public QuoteFilterChangeListener(FilteredList<QuoteModel> data) {
        this.data = data;
    }

    /**
     * DOCUMENT ME!
     *
     * @param observable DOCUMENT ME!
     * @param oldValue DOCUMENT ME!
     * @param newValue DOCUMENT ME!
     */
    public void changed(ObservableValue<?extends String> observable,
                        final String oldValue, final String newValue) {
        data.setPredicate(new Predicate<QuoteModel>() {
            public boolean test(QuoteModel quote) {
                if ((newValue == null) || newValue.isEmpty()) {
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
