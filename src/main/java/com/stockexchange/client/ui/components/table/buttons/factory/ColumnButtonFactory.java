package com.stockexchange.client.ui.components.table.buttons.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

import com.stockexchange.client.ui.ViewStage;
import com.stockexchange.client.ui.components.table.buttons.ColumnButton;

public class ColumnButtonFactory<S> {

    private final Class< ? extends ColumnButton< S>> btnType;
    private final Class< S> itemType;
    private Constructor< ? extends ColumnButton< S>> ctor;

    /**
     * Makes a factory to make the class described by btnType
     * 
     * @param btnType
     *            Describes the button to make.
     */
    public ColumnButtonFactory(Class< ? extends ColumnButton< S>> btnType) {
        this.btnType = btnType;
        ParameterizedType type = (ParameterizedType) btnType.getGenericSuperclass();
        this.itemType = (Class< S>) type.getActualTypeArguments()[0];
        try {
            this.ctor = btnType.getConstructor(ViewStage.class, itemType);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    };

    public ColumnButton< S> create(ViewStage win, S item) {
        try {
            return ctor.newInstance(win, item);
        } catch (Exception e) {
            return null;
        }
    }
}
