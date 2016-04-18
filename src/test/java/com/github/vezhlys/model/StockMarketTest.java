package com.github.vezhlys.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.vezhlys.exceptions.NoTickersProvidedException;
import com.github.vezhlys.model.enums.StockType;
import com.github.vezhlys.model.enums.TradeType;

public class StockMarketTest {

	private StockMarket market;

	@Before
	public void initMarket() throws NoTickersProvidedException {
		market = initStockMarket();
	}

	@Test
	public void stockIndexIsCorrect() {
		BigDecimal number = market.stockIndex();
		Assert.assertTrue(BigDecimal.valueOf(3.310).compareTo(number) == 0);
	}

	@Test(expected = NoTickersProvidedException.class)
	public void initStockMarketThrowsExceptionOnNull() throws NoTickersProvidedException {
		StockMarket.initStockMarket(null);
	}

	@Test(expected = NoTickersProvidedException.class)
	public void initStockMarketThrowsExceptionOnOnEmptyList() throws NoTickersProvidedException {
		StockMarket.initStockMarket(Collections.emptySet());
	}

	@Test
	public void tickerIsAAA() {
		Assert.assertTrue(market.getTicker("AAA").getId().equals("AAA"));
	}

	@Test
	public void tickerIsNull() {
		Assert.assertNull(market.getTicker("NO"));
	}

	@Test
	public void showDataOutputIsCorrect() throws NoTickersProvidedException {
		StockMarket market = StockMarket
				.initStockMarket(new HashSet<Stock>(Arrays.asList(getStockTickerForShowData())));
		Assert.assertEquals(market.showStockData(),
				StockMarket.INFO_HEADER + "\n" + "AAAA\tPrefer\t0.150\t2.00%\t0.100\t5.000\t0.00%\t33.333");
	}

	@Test
	public void tickerPriceIsCorrectAfterTrades() {
		market.addTrade(getTrade("AAA", BigDecimal.valueOf(2), 10, TradeType.BUY, LocalDateTime.now()));
		market.addTrade(
				getTrade("AAA", BigDecimal.valueOf(6), 15, TradeType.SELL, LocalDateTime.of(2010, 10, 10, 10, 10, 10)));
		market.addTrade(getTrade("BBB", BigDecimal.valueOf(3), 30, TradeType.BUY, LocalDateTime.now()));
		market.addTrade(getTrade("BBB", BigDecimal.valueOf(6), 15, TradeType.SELL, LocalDateTime.now()));
		Assert.assertTrue(market.getTicker("AAA").getPrice().compareTo(new BigDecimal(2)) == 0);
		Assert.assertTrue(market.getTicker("BBB").getPrice().compareTo(new BigDecimal(4)) == 0);
	}

	public StockMarket initStockMarket() throws NoTickersProvidedException {
		return StockMarket.initStockMarket(getValidSetOfStockTickers());
	}

	private Set<Stock> getValidSetOfStockTickers() {
		Set<Stock> tickers = new HashSet<>();
		tickers.add(getStockTicker("AAA", BigDecimal.valueOf(2)));
		tickers.add(getStockTicker("BBB", BigDecimal.valueOf(3)));
		tickers.add(getStockTicker("CCC", BigDecimal.valueOf(4)));
		tickers.add(getStockTicker("DDD", BigDecimal.valueOf(5)));
		return tickers;
	}

	private Stock getStockTicker(final String id, final BigDecimal price) {
		Stock ticker = new Stock();
		ticker.setId(id);
		ticker.setPrice(price);
		return ticker;
	}

	private Stock getStockTickerForShowData() {
		Stock ticker = new Stock();
		ticker.setId("AAAA");
		ticker.setLastDividend(BigDecimal.valueOf(0.150));
		ticker.setFixedDividend(BigDecimal.valueOf(0.02));
		ticker.setType(StockType.PREFER);
		ticker.setParValue(BigDecimal.valueOf(0.100));
		ticker.setPrice(BigDecimal.valueOf(5));
		return ticker;
	}

	private Trade getTrade(final String tickerId, final BigDecimal price, final int quantity, final TradeType type,
			final LocalDateTime timestamp) {
		Trade trade = new Trade();
		trade.setTickerId(tickerId);
		trade.setPrice(price);
		trade.setQuantity(quantity);
		trade.setType(type);
		trade.setTimestamp(timestamp);
		return trade;
	}

}
