package com.stockexchange.util;

import java.util.ArrayList;
import java.util.List;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  *
 * @param <E> DOCUMENT ME!
 */
public class History<E> {
    private int index;
    private int length;
    private Object[] history;

    /**
     * Create ring buffer that lets the entire collection of data be read in order of addition without popping
     *
     * @param length
     *            the duration of the history
     */
    public History(int length) {
        this.length = length;
        this.index = 0;
        history = new Object[length];
    }

    /**
     * Add an element to the history, potentially overwriting an old one
     *
     * @param e
     *            an element
     */
    public void add(E e) {
        history[index] = e;
        index = (index + 1) % length;
    }

    /**
     * Gets the entire History
     *
     * @return
     *
     */
    public List<E> getAll() {
        List<E> list = new ArrayList<E>(length);

        for (int i = 0; i < length; i++) {
            int j = (index + i) % length;

            if (history[j] == null) {
                continue;
            }

            list.add((E)history[j]);
        }

        return list;
    }
}
