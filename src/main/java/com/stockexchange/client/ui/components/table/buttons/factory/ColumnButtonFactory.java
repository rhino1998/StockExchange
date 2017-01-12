package com.stockexchange.client.ui.components.table.buttons.factory;

import com.stockexchange.client.ClientApp;
import com.stockexchange.client.ui.components.table.buttons.ColumnButton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import javafx.stage.Stage;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  *
 * @param <S> DOCUMENT ME!
 */
public class ColumnButtonFactory<S> {
    private final Class<?extends ColumnButton<S>> btnType;
    private final Class<S> itemType;
    private Constructor<?extends ColumnButton<S>> ctor;

    /**
     * Makes a factory to make the class described by btnType
     *
     * @param btnType
     *            Describes the button to make.
     */
    public ColumnButtonFactory(Class<?extends ColumnButton<S>> btnType) {
        this.btnType = btnType;

        ParameterizedType type =
            (ParameterizedType)btnType.getGenericSuperclass();
        this.itemType = (Class<S>)type.getActualTypeArguments()[0];

        try {
            this.ctor = btnType.getConstructor(ClientApp.class, itemType);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param app DOCUMENT ME!
     * @param item DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public ColumnButton<S> create(ClientApp app, S item) {
        try {
            return ctor.newInstance(app, item);
        } catch (Exception e) {
            return null;
        }
    }
}
