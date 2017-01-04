package com.stockexchange.util;

import java.util.ArrayList;
import java.util.List;

public class History<E> {

    private int index;
    private int length;
    private Object[] history;

    public History(int length) {
        this.length = length;
        this.index = 0;
        history = new Object[length];
    }

    public void add(E e) {
        history[index] = e;
        index = (index + 1) % length;
    }

    public List< E> getAll() {
        List< E> list = new ArrayList< E>(length);
        for (int i = 0; i < length; i++) {
            int j = (index + i) % length;
            if (history[j] == null) {
                continue;
            }
            list.add((E) history[j]);
        }
        return list;

    }
}
