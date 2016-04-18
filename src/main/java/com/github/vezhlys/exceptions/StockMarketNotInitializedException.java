package com.github.vezhlys.exceptions;

import com.github.vezhlys.model.StockMarket;

/**
 * Stock market is not initialized exception on getting the stock market
 * instance.
 * 
 * @see StockMarket
 */
public class StockMarketNotInitializedException extends RuntimeException {
	private static final long serialVersionUID = 2692408413304454630L;
}
