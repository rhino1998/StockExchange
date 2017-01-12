package com.stockexchange.util;

import java.util.HashMap;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
  *
 * @param <K> DOCUMENT ME!
 * @param <V> DOCUMENT ME!
 */
public class DefaultConcurrentHashMap<K, V> extends HashMap<K, V> {
    protected V defaultValue;

    /**
     * Creates a new DefaultConcurrentHashMap object.
     *
     * @param defaultValue DOCUMENT ME!
     */
    public DefaultConcurrentHashMap(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param k DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    @Override
    public V get(Object k) {
        return containsKey(k) ? super.get(k) : defaultValue;
    }
}
