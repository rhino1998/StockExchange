package com.stockexchange.test.data;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.stockexchange.util.History;

public class HistoryTest {

	@Test
	public void historyAdd(){
		History<Integer> history = new History<Integer>(80);
		
		for (int i = 0; i<1000; i++){
			history.add(i);
		}
		
		int j = 920;
		for (Integer i : history.getAll()){
			assertTrue(j==i);
			j++;
		}
	}
}
