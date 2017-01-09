package com.stockexchange.util;

import java.util.HashMap;

public class DefaultConcurrentHashMap<K, V> extends HashMap<K, V> {
    protected V defaultValue;

    public DefaultConcurrentHashMap(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public V get(Object k) {
        return containsKey(k) ? super.get(k) : defaultValue;
    }
}