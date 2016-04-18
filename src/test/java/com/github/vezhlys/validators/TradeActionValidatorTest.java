package com.github.vezhlys.validators;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.vezhlys.exceptions.InvalidTradeArgumentsException;
import com.github.vezhlys.exceptions.NoTickersProvidedException;
import com.github.vezhlys.model.StockMarket;
import com.github.vezhlys.model.StockMarketTest;

public class TradeActionValidatorTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	StockMarket market;

	@Before
	public void initMarket() throws NoTickersProvidedException {
		StockMarketTest test = new StockMarketTest();
		market = test.initStockMarket();
	}

	@Test
	public void validateEmptyArgument() throws InvalidTradeArgumentsException {
		checkExceptionMessage("No arguments.");
		TradeActionValidator.validateArguments("trade", market);
	}

	@Test
	public void validateTooSmallNumberOfArguments() throws InvalidTradeArgumentsException {
		checkExceptionMessage("Incorrect number of arguments.");
		TradeActionValidator.validateArguments("trade AAA sell 2", market);
	}

	@Test
	public void validateToBigNumberOfArguments() throws InvalidTradeArgumentsException {
		checkExceptionMessage("Incorrect number of arguments.");
		TradeActionValidator.validateArguments("trade AAA sell 2 2 now", market);
	}

	@Test
	public void validateEmptyString() throws InvalidTradeArgumentsException {
		checkExceptionMessage("No arguments.");
		TradeActionValidator.validateArguments(null, market);
	}

	@Test
	public void validateUnknownTicker() throws InvalidTradeArgumentsException {
		checkExceptionMessage("Unknown stock ticker.");
		TradeActionValidator.validateArguments("trade NOTICKER sell 2 2.5", market);
	}

	@Test
	public void validateUnknownTradeType() throws InvalidTradeArgumentsException {
		checkExceptionMessage("Invalid operation. Possible values: buy or sell.");
		TradeActionValidator.validateArguments("trade AAA donate 2 2.5", market);
	}

	@Test
	public void validateQuantityIsNotANumber() throws InvalidTradeArgumentsException {
		checkExceptionMessage("Quantity must be a number.");
		TradeActionValidator.validateArguments("trade AAA buy s 2.5", market);
	}

	@Test
	public void validateQuantityIsBelowOne() throws InvalidTradeArgumentsException {
		checkExceptionMessage("Quantity can not be less than 1.");
		TradeActionValidator.validateArguments("trade AAA sell 0 2.5", market);
	}

	@Test
	public void validatePriceIsNumber() throws InvalidTradeArgumentsException {
		checkExceptionMessage("Price must be a number.");
		TradeActionValidator.validateArguments("trade AAA sell 2 a", market);
	}

	@Test
	public void validatePriceIsZero() throws InvalidTradeArgumentsException {
		checkExceptionMessage("Price can't be 0 or less.");
		TradeActionValidator.validateArguments("trade AAA sell 2 0", market);
	}

	@Test
	public void validateArgumentsAreCorrect() throws InvalidTradeArgumentsException {
		ExpectedException.none();
		TradeActionValidator.validateArguments("trade AAA sell 2 1.5", market);
		TradeActionValidator.validateArguments("trade BBB buy 10 0.235", market);
	}

	private void checkExceptionMessage(String message) {
		expectedException.expect(InvalidTradeArgumentsException.class);
		expectedException.expectMessage(message);
	}

}
