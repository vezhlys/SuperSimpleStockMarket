package com.github.vezhlys.model;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.github.vezhlys.model.enums.StockType;

public class StockTickerTest {

	@Test
	public void dividendYieldEqualsToLastDividendDivisionToPrice() {
		Stock ticker = getCommonStock();
		Assert.assertTrue(ticker.dividendYield().compareTo(BigDecimal.valueOf(0.5)) == 0);
	}

	@Test
	public void dividendPreferredYieldEqualsToFixedDividendDivisionToPrice() {
		Stock ticker = getPreferredStock();
		Assert.assertTrue(ticker.dividendYield().compareTo(BigDecimal.valueOf(0.002)) == 0);
	}

	@Test
	public void peRationIsTickerPriceDividedByDividend() {
		Stock ticker = getCommonStock();
		Assert.assertTrue(ticker.peRatio().compareTo(BigDecimal.valueOf(1.333)) == 0);
	}


	private Stock getCommonStock() {
		Stock ticker = new Stock();
		ticker.setLastDividend(new BigDecimal(0.25));
		ticker.setPrice(new BigDecimal(0.50));
		ticker.setType(StockType.COMMON);
		return ticker;
	}

	private Stock getPreferredStock() {
		Stock ticker = new Stock();
		ticker.setLastDividend(new BigDecimal(0.08));
		ticker.setPrice(new BigDecimal(1));
		ticker.setParValue(new BigDecimal(0.1));
		ticker.setFixedDividend(new BigDecimal(0.02));
		ticker.setType(StockType.PREFER);
		return ticker;
	}
}
