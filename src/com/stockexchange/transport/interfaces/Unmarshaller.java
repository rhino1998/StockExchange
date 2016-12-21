package com.stockexchange.transport.interfaces;

public interface Unmarshaller<E> {

	public E unmarshall(String json);
}
