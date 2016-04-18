package com.github.vezhlys.actions.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import com.github.vezhlys.exceptions.InvalidTradeArgumentsException;
import com.github.vezhlys.exceptions.NoTickersProvidedException;
import com.github.vezhlys.model.StockMarket;
import com.github.vezhlys.model.Stock;
import com.github.vezhlys.model.enums.StockType;

public class RecordTradeActionHandlerTest {

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	private StockMarket market;

	@Before
	public void initMarket() throws NoTickersProvidedException {
		market = StockMarket.initStockMarket(getStockTickers());
	}

	@Test
	public void removeFirstTrade() {
		Assert.assertEquals("typo".replaceAll("^(trade|t)", ""), "ypo");
	}

	@Test
	public void compareTickerInfoAfterTrade() throws InvalidTradeArgumentsException {
		new RecordTradeActionHandler("trade AAA buy 3 1").action();
		new RecordTradeActionHandler("trade AAA buy 6 2").action();
		Assert.assertTrue(market.getTicker("AAA").getPrice().compareTo(BigDecimal.valueOf(1.667)) == 0);
	}

	private Set<Stock> getStockTickers() {
		Stock ticker = new Stock();
		ticker.setId("AAA");
		ticker.setLastDividend(BigDecimal.valueOf(0.150));
		ticker.setType(StockType.COMMON);
		ticker.setParValue(BigDecimal.valueOf(0.100));
		ticker.setPrice(BigDecimal.valueOf(5));
		return new HashSet<Stock>(Arrays.asList(ticker));
	}
}
