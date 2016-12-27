package com.stockexchange.test.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	AuthenticationTest.class,
	StockExchangeTest.class,
})

public class TestSuiteAPI {}
