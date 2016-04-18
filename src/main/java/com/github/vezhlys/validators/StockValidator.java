package com.github.vezhlys.validators;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.github.vezhlys.exceptions.InvalidStockException;
import com.github.vezhlys.model.Stock;

/**
 * Validator for the stock before adding it to stock market on init.
 */
public class StockValidator {

	public static void validateStock(final Stock stock) throws InvalidStockException {
		validateTicker(stock);
		validateLastDividend(stock);
		validateFixedDividend(stock);
		validateType(stock);
		validateParValue(stock);
		validatePrice(stock);
	}

	private static void validateTicker(final Stock stock) throws InvalidStockException {
		if (StringUtils.isBlank(stock.getId())) {
			throw new InvalidStockException("Invalid ticker. Can't be empty.");
		}
	}

	private static void validateLastDividend(final Stock stock) throws InvalidStockException {
		if (stock.getLastDividend() == null || stock.getLastDividend().compareTo(BigDecimal.ZERO) < 0) {
			throw new InvalidStockException("Invalid last dividend value (empty or less than zero).");
		}
	}

	private static void validateFixedDividend(final Stock stock) throws InvalidStockException {
		if (stock.getFixedDividend() != null && stock.getFixedDividend().compareTo(BigDecimal.ZERO) < 0) {
			throw new InvalidStockException("Invalid fixed dividend value (less than zero).");
		}
	}

	private static void validateType(final Stock stock) throws InvalidStockException {
		if (stock.getType() == null) {
			throw new InvalidStockException("Invalid type. Can't be null value.");
		}
	}

	private static void validateParValue(final Stock stock) throws InvalidStockException {
		if (stock.getParValue() == null || stock.getParValue().compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidStockException("Invalid par value (empty, zero or less than zero).");
		}
	}

	private static void validatePrice(Stock stock) throws InvalidStockException {
		if (stock.getPrice() == null || stock.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidStockException("Invalid price (empty, zero or less thgn zero).");
		}
	}

}
