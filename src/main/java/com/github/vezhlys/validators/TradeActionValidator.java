package com.github.vezhlys.validators;

import org.apache.commons.lang3.StringUtils;

import com.github.vezhlys.exceptions.InvalidTradeArgumentsException;
import com.github.vezhlys.model.StockMarket;
import com.github.vezhlys.model.enums.TradeType;

/**
 * Trade action arguments validator.
 */
public class TradeActionValidator {

	public static void validateArguments(final String argumentLine, final StockMarket market)
			throws InvalidTradeArgumentsException {
		validateNotEmpty(argumentLine);
		String[] arguments = argumentLine.split("\\s");
		validateNumberOfArguments(arguments);
		validateTicker(arguments, market);
		validateTradeType(arguments);
		validateQuantity(arguments);
		validatePrice(arguments);
	}

	private static void validateNotEmpty(final String argumentLine) throws InvalidTradeArgumentsException {
		if (argumentLine == null || StringUtils.isBlank(argumentLine.replaceAll("^(trade|t)", ""))) {
			throw new InvalidTradeArgumentsException("No arguments.");
		}
	}

	private static void validateNumberOfArguments(final String[] arguments) throws InvalidTradeArgumentsException {
		if (arguments.length != 5) {
			throw new InvalidTradeArgumentsException("Incorrect number of arguments.");
		}
	}

	private static void validateTicker(final String[] arguments, final StockMarket market)
			throws InvalidTradeArgumentsException {
		if (market.getTicker(arguments[1].toUpperCase()) == null) {
			throw new InvalidTradeArgumentsException("Unknown stock ticker.");
		}
	}

	private static void validateTradeType(final String[] arguments) throws InvalidTradeArgumentsException {
		try {
			TradeType.valueOf(arguments[2].toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new InvalidTradeArgumentsException("Invalid operation. Possible values: buy or sell.");
		}
	}

	private static void validateQuantity(final String[] arguments) throws InvalidTradeArgumentsException {
		try {
			Integer quantity = Integer.valueOf(arguments[3]);
			if (quantity < 1) {
				throw new InvalidTradeArgumentsException("Quantity can not be less than 1.");
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new InvalidTradeArgumentsException("Quantity must be a number.");
		}

	}

	private static void validatePrice(final String[] arguments) throws InvalidTradeArgumentsException {
		try {
			Double price = Double.valueOf(arguments[4]);
			if (price <= 0) {
				throw new InvalidTradeArgumentsException("Price can't be 0 or less.");
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new InvalidTradeArgumentsException("Price must be a number.");
		}

	}
}
