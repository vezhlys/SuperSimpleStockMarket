package com.github.vezhlys.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.github.vezhlys.exceptions.NoTickersProvidedException;
import com.github.vezhlys.exceptions.StockMarketNotInitializedException;

/**
 * Stock market model.
 */
public class StockMarket {
	public final static String INFO_HEADER = "Ticker\tType\tLastD\tFixedD\tParV\tPrice\tDYield\tP\\E";
	private final Set<Stock> tickers;
	private final List<Trade> trades = new ArrayList<>();
	private static StockMarket instance;

	private StockMarket(final Set<Stock> tickers) throws NoTickersProvidedException {
		if (CollectionUtils.isEmpty(tickers)) {
			throw new NoTickersProvidedException();
		}
		this.tickers = tickers;
	}

	public static StockMarket initStockMarket(final Set<Stock> tickers) throws NoTickersProvidedException {
		instance = new StockMarket(tickers);
		return instance;
	}

	public static StockMarket getInstance() {
		if (instance == null) {
			new StockMarketNotInitializedException();
		}
		return instance;
	}

	public void addTrade(final Trade trade) {
		trades.add(trade);
		updateTickerPrice(trade);
		removeTradesOlderThanFifteenMinutes();
	}

	public Stock getTicker(final String tickerId) {
		return tickers.stream().filter(ticker -> ticker.getId().equals(tickerId)).findFirst().orElse(null);
	}

	public BigDecimal stockIndex() {
		if (CollectionUtils.isEmpty(tickers)) {
			return BigDecimal.ZERO;
		}
		BigDecimal multipliedPrices = BigDecimal.ONE;
		for (Stock ticker : tickers) {
			multipliedPrices = multipliedPrices
					.multiply(Optional.ofNullable(ticker.getPrice()).orElse(BigDecimal.ZERO));
		}
		return nthRoot(tickers.size(), multipliedPrices);
	}

	public String showStockData() {
		final StringBuilder stockDataBuilder = new StringBuilder(INFO_HEADER + "\n");
		stockDataBuilder.append(tickers.stream().map(Stock::toString).collect(Collectors.joining("\n")));
		return stockDataBuilder.toString();
	}

	private void updateTickerPrice(final Trade trade) {
		getTicker(trade.getTickerId()).setPrice(getNewTickerPrice(trade));
	}

	private BigDecimal getNewTickerPrice(final Trade trade) {
		BigDecimal totalCost = BigDecimal.ZERO;
		int sumOfQuantities = 0;
		for (Trade trd : tradesByTickerInLastFifteenMinutes(trade)) {
			totalCost = totalCost.add(trd.getCost());
			sumOfQuantities += trd.getQuantity();
		}
		return sumOfQuantities > 0
				? totalCost.divide(BigDecimal.valueOf(sumOfQuantities), Stock.SCALE, BigDecimal.ROUND_HALF_UP)
				: BigDecimal.ZERO;
	}

	private List<Trade> tradesByTickerInLastFifteenMinutes(final Trade trade) {
		return trades.stream().filter(tradesByIdInLastFifteenMinutes(trade.getTickerId())).collect(Collectors.toList());
	}

	private Predicate<Trade> tradesByIdInLastFifteenMinutes(final String tickerId) {
		return trd -> trd.getTickerId().equals(tickerId)
				&& trd.getTimestamp().isAfter(LocalDateTime.now().minusMinutes(15));
	}

	private void removeTradesOlderThanFifteenMinutes() {
		trades.removeIf(trd -> trd.getTimestamp().isBefore(LocalDateTime.now().minusMinutes(15)));
	}

	private BigDecimal nthRoot(final int nthValue, final BigDecimal multipliedPrices) {
		final BigDecimal scale = BigDecimal.valueOf(0.001);
		if (multipliedPrices.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO;
		}
		BigDecimal tempValue = multipliedPrices;
		BigDecimal nthRoot = multipliedPrices.divide(new BigDecimal(nthValue), Stock.SCALE, BigDecimal.ROUND_HALF_UP);
		while (nthRoot.subtract(tempValue).abs().compareTo(scale) > 0) {
			tempValue = nthRoot;
			nthRoot = BigDecimal.valueOf(nthValue - 1).multiply(nthRoot)
					.add(multipliedPrices.divide(nthRoot.pow(nthValue - 1), Stock.SCALE, BigDecimal.ROUND_HALF_UP))
					.divide(new BigDecimal(nthValue), Stock.SCALE, BigDecimal.ROUND_HALF_UP);
		}
		return nthRoot;
	}

}
