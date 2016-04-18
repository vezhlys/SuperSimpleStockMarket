package com.github.vezhlys;

import static com.github.vezhlys.model.Stock.stockBuilder;

import java.io.Console;
import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.github.vezhlys.actions.impl.ShowDataActionHandler;
import com.github.vezhlys.exceptions.InvalidStockException;
import com.github.vezhlys.exceptions.NoConsoleFoundException;
import com.github.vezhlys.exceptions.NoTickersProvidedException;
import com.github.vezhlys.exceptions.StockMarketNotInitializedException;
import com.github.vezhlys.model.Stock;
import com.github.vezhlys.model.StockMarket;
import com.github.vezhlys.model.enums.StockType;

/**
 * Simple Stock Market application.
 */
public class SimpleStockMarketApp {

	public static void main(String[] args) throws NoTickersProvidedException {
		try {
			final Console console = System.console();
			StockMarket.initStockMarket(loadInitialData());
			ShowDataActionHandler.getInstance().action();
			ActionProcessor.getInstance().process(console);
		} catch (NoConsoleFoundException e) {
			closeOnEmptyConsole();
		} catch (StockMarketNotInitializedException | InvalidStockException e) {
			closeOnFaultyMarket(e);
		}
	}

	private static Set<Stock> loadInitialData() throws InvalidStockException {
		Set<Stock> initialData = new TreeSet<>();
		initialData.add(stockBuilder("TEA", StockType.COMMON, BigDecimal.ZERO, null, BigDecimal.valueOf(0.100),
				BigDecimal.valueOf(0.500)));
		initialData.add(stockBuilder("POP", StockType.COMMON, BigDecimal.valueOf(0.08), null, BigDecimal.valueOf(0.060),
				BigDecimal.valueOf(0.500)));
		initialData.add(stockBuilder("ALE", StockType.COMMON, BigDecimal.valueOf(0.23), null, BigDecimal.valueOf(0.100),
				BigDecimal.valueOf(2.000)));
		initialData.add(stockBuilder("GIN", StockType.PREFER, BigDecimal.valueOf(0.08), new BigDecimal(0.02),
				BigDecimal.valueOf(0.100), BigDecimal.valueOf(0.100)));
		initialData.add(stockBuilder("JOE", StockType.COMMON, BigDecimal.valueOf(0.13), null, BigDecimal.valueOf(0.250),
				BigDecimal.valueOf(0.900)));
		return initialData;
	}

	private static void closeOnEmptyConsole() {
		System.err.println("Cannot get system console object. Maybe you are running application from IDE?");
		System.exit(1);
	}

	private static void closeOnFaultyMarket(Exception e) {
		if (StringUtils.isNotEmpty(e.getMessage())) {
			System.err.println(e.getMessage());
		}
		System.err.println(
				"Unexpected error: stock market is not initialized properly. Sorry for inconvienence. Please report it!");
		System.exit(1);
	}

}